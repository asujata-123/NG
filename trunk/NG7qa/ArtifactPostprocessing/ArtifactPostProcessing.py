#!/usr/bin/env python

#################################################################################################################
# ArtifactPostProcessing.py
#  Python Tool that parses test results from the Selenium execution artifacts and updates the Zephyr automated test containers via ZAPI.
# that based on a JQL query extracts from JIRA the results of this query and processes each issue to gather the list of the following fields:
#
# Syntax: (python must be in your path)
#   
#   python ArtifactPostProcessing.py --jiraServer=<jira server url> --route=/jira --region=<region processed> --timestamp=<execution timestamp>  
#
#   Optional command line arguments: --update=[True | False] --testname=<test name>
#   
##################################################################################################################

import optparse
import json

import settings
import csv
import os
import urllib
import urllib2
import requests
import cookielib
import re
import base64

from requests.auth import HTTPBasicAuth
from restkit import Resource, Connection, BasicAuth, request
from socketpool import ConnectionPool
from json import dumps, load
from base64 import b64encode
from cookielib import CookieJar

#--- functions  -----

def login(authURL, username, password):
# Get the authentication cookie using the REST API

    creds = {'username' : username, 'password' : password}
    req = urllib2.Request(authURL)
    req.add_data(json.dumps(creds))
    req.add_header("Content-type", "application/json")
    req.add_header("Accept", "application/json")
    fp = opener.open(req)
    fp.close()

def getProjectNumber(projName):
    #print("url=" + url + settings.ZAPI_URLGETPROJLIST)
    #print("Getting the project number for project "+projName)
    item_fetcher = fetcher_factory(url + settings.ZAPI_URLGETPROJLIST)
    projects = item_fetcher("")
    projNumber = 0
   
    for x in projects['options'] :
      if projName in str(x['label']) :
         projNumber = int(x['value'])
         #print("project label = " +str(x['label']) +", project number = " +str(x['value']))
            
    return projNumber
   
def getVersionId(projId, versionName) :
    #print("Getting the version for project "+str(projId))
    #print("url = "+url + settings.ZAPI_URLGETVERSIONS + str(projId))
    item_fetcher = fetcher_factory(url + settings.ZAPI_URLGETVERSIONS + str(projId))
    versions = item_fetcher("")
    versionId = 0
   
    for x in versions["unreleasedVersions"] :
       if str(versionName) in str(x["label"]) :
         versionId = str(x["value"])
         
   # WIP handle traverse for releasedVersions list    
   
    #print("Version id = " + str(versionId))               
    return versionId   
   
def getTestCaseId(testContainerName) :
    #print("Getting the test case id for test container "+testContainerName)
    item_fetcher = fetcher_factory(url + settings.URLGETAUTOMATEDTESTS)
    testContainers = item_fetcher("")
    testContainerId = ""
   
    for x in testContainers["issues"] :
      #if testContainerName in str(x['fields']['summary']) :
      if testContainerName == str(x['fields']['summary']) :
         testContainerId = int(x['id'])
            
    #print("Test case id = " + str(testContainerId))               
    return testContainerId 

def getTestStepId(testCaseId, stepOrder) :
   #print("Getting the test step id for step " + str(stepOrder) + " in test container "+ str(testCaseId))
   getStepIdUrl = url + settings.ZAPI_URLTESTSTEP + str(testCaseId)
   #print ("getStepIdUrl = "+ str(getStepIdUrl))
   item_fetcher = fetcher_factory(getStepIdUrl)
   testSteps = item_fetcher("")
   testStepId = ""
   
   for x in testSteps :
      order = x['orderId']
      if order == stepOrder :
         testStepId = x['id']

   #print("Test step id = " + str(testStepId))
   return testStepId    
   
### WIP   
def getTestCyleId(testCycleNamePrefix, projectId, versionId) :
   #print("Getting the id of test case cycle with prefix "+testCycleNamePrefix)
   item_fetcher = fetcher_factory(url + settings.ZAPI_URLTESTCYCLE + "?projectId="+str(projectId)+"&versionId="+str(versionId))
   testCycles = item_fetcher("")
   testCycleId = 0
   name = ""
   
   for x,y in enumerate(testCycles) :
      try:
         name = testCycles[str(y)]['name']
         #print("cycle name=" +testCycles[str(y)]['name']+"/"+str(x)+"/"+str(y))
         if testCycleNamePrefix in str(name) :
            testCycleId = str(y)
      except:
         continue
      
   #print("testCycleId = " + str(testCycleId))
   return testCycleId
###      

def getTestExecutionId(testCycleId, testCaseId) :
   #print("Delete executions of testCycleId "+str(testCycleId))
   getExecutionsUrl = url + settings.ZAPI_URLTESTEXECUTIONS + '?cycleId=' + str(testCycleId)
   item_fetcher = fetcher_factory(getExecutionsUrl)
   testExecutions = item_fetcher("")
   executionId = ""
   
   for x in testExecutions['executions'] :
      executionId = x['id']
      issueId = x['issueId']
      #print("issueId/testCycleId ="+str(issueId)+"/"+str(testCycleId))
      if str(testCaseId) == str(issueId) :
         return executionId
   return ""

def cleanupExecutions(testCaseId) :
   #print("Delete executions of testCaseId "+str(testCaseId))
   getExecutionsUrl = url + settings.ZAPI_URLTESTEXECUTIONS + '?issueId=' + str(testCaseId)
   item_fetcher = fetcher_factory(getExecutionsUrl)
   testExecutions = item_fetcher("")
   
   for x in testExecutions['executions'] :
      testExecId = x['id']
      execDeleteUrl = url + settings.ZAPI_URLTESTEXECUTIONS + str(testExecId)
      #print("execDeleteUrl = "+execDeleteUrl)
      deleteRequest(execDeleteUrl)
      item_fetcher = fetcher_factory(getExecutionsUrl)
      projects = item_fetcher("")   

def performBulkQuickExecute(testCycleId, executionValue) :
   if (options.debug is True):
      print("performBulkQuickExecute:  set all execution containers to "+executionValue)
   getExecutionsUrl = url + settings.ZAPI_URLTESTEXECUTIONS + '?cycleId=' + str(testCycleId)
   item_fetcher = fetcher_factory(getExecutionsUrl)
   testExecutions = item_fetcher("")
   
   for x in testExecutions['executions'] :
      testExecId = x['id']
      performQuickExecute(testExecId, executionValue)
      
	  
def createExecution(testCaseId, versionId, testCycleId, projectId) :
   #print ("Create a new execution for testcase "+ str(testCaseId))
   createExecutionUrl = url + settings.ZAPI_URLTESTEXECUTIONS + '?issueId=' + str(testCaseId)
   
   data = {
   "issueId": testCaseId,
   "versionId": versionId,
   "cycleId": testCycleId,
   "projectId": projectId
   }
   
   response = postRequest(createExecutionUrl, data)
   dataBack = json.loads(response.read())
   #print("\n\nRESPONSE DATA:"+str(dataBack)+"\n\n")
   for x in dataBack :
      createdExecutionId = x
   #print("\n\ncreateExecution id:"+str(createdExecutionId)+"\n\n")
   return createdExecutionId

def createTestStep(testCaseId) :
   #print ("Add a test step to testcase "+ str(testCaseId))
   createTestStepUrl = url + settings.ZAPI_URLTESTSTEP + str(testCaseId)
   createdTestStepId = ""
   data = {
   "step": "new test step"
   }
   
   #print("createTestStepUrl = "+createTestStepUrl)
   response = postRequest(createTestStepUrl, data)
   dataBack = json.loads(response.read())
   #print("\nRESPONSE DATA:"+str(dataBack)+"\n")
   createdTestStepId = dataBack['id']
   #print("CreatedTestStepId ="+str(createdTestStepId))
   
   if createdTestStepId != "" :
      response = updateTestStep(testCaseId, createdTestStepId, "new test step", " ", " ")
      #moveTestStepUrl = createTestStepUrl +"/"+str(createdTestStepId) +'/move'
      #print("moveTestStepUrl = "+moveTestStepUrl)
      #data = {"position": "First"}
      #response = postRequest(moveTestStepUrl, data)
      
   return createdTestStepId
   
def getExecutionStatusValue(rowSummResult) :
   rowSummResult = rowSummResult.lower()
   if rowSummResult == "pass" or rowSummResult == "passed" :
      status = 1
   elif rowSummResult == "fail" or rowSummResult == "failed" :
      status = 2
   elif rowSummResult == "warning" :
      status = 2   
   elif rowSummResult == "wip" :
      status = 3
   elif rowSummResult == "blocked" :
      status = 4
   else :
      status = -1
      
   return status
   
def performQuickExecute(testExecutionId, rowSummResult) :
   #print ("Perform quickExecute of "+str(testExecutionId))
   
   data = {
   "status" : getExecutionStatusValue(rowSummResult)
   }
   quickExecuteUrl = url + settings.ZAPI_URLTESTEXECUTIONS + str(testExecutionId) +"/quickExecute"
   #print("url = "+quickExecuteUrl)
   response = postRequest(quickExecuteUrl, data)
   #dataBack = json.loads(response.read())
   #print("preformQuickExecute response : "+str(response))
   return response
      
def performTestStepExecute(testExecutionId, resultsList, commentsList, testSteps) : 
   #print ("Perform test step execution of "+str(testExecutionId))
   testStepExecuteUrl = url + settings.ZAPI_URLTESTSTEPRESULT
   
   #print("resultsList = "+str(resultsList))
   #print("\n\nexecution testSteps = "+str(testSteps) +"\n\n")
   
   for x, y in enumerate(testSteps) :
       #print ("x/y = "+str(x)+"/"+str(y))
      stepId = str(y['stepId'])
      urlPutRequest = testStepExecuteUrl + str(y['id'])
      #print ("urlPutRequest = "+urlPutRequest)
      status = getExecutionStatusValue(resultsList['\''+str(stepId)+'\''])
      comment = commentsList['\''+str(stepId)+'\'']
      data = { 
      "status" : status,
      "comment" : comment
      }
      #print("Result for step "+str(y['id'])+" = "+str(status))
      response = putRequest(urlPutRequest, data)
      
      
def updateTestStep(testCaseId, stepId, stepDescription, stepData, stepResult) :
    #print("Update test")
    
    formdata = {"id":stepId,"step":stepDescription,"data":stepData,"result":stepResult}
    #data_encoded = urllib.urlencode(formdata)
    #print("data = "+str(formdata))
    
    testStepUpdateUrl = url + settings.ZAPI_URLTESTSTEP + str(testCaseId)+'/'+str(stepId)
    #print("testStepUpdateUrl = " +testStepUpdateUrl)
   
    putRequest(testStepUpdateUrl, formdata)
   
def updateTestCycle(testCycleId, field, data) :
    if (options.debug is True) : 
	   print("Update test cycle "+field)
    
    formdata = {
      "id" : testCycleId,
      field:data
   }
    #print("data = "+str(formdata))
    
    testCycleUpdateUrl = url + settings.ZAPI_URLTESTCYCLE
    #print("testCycleUpdateUrl = " +testCycleUpdateUrl)
   
    putRequest(testCycleUpdateUrl, formdata)

def deleteTestSteps(testCaseId, list) :
    #print("Delete test steps from test case "+str(testCaseId))
    getStepsUrl = url + settings.ZAPI_URLTESTSTEP + str(testCaseId)
    item_fetcher = fetcher_factory(getStepsUrl)
    steps = item_fetcher("")
    teststepId = 0
   
    for x in steps :
      teststepId = x['id']
      if " "+str(teststepId)+" " not in list :
         stepDeleteUrl = getStepsUrl + '/'+str(teststepId)
         deleteRequest(stepDeleteUrl)
         item_fetcher = fetcher_factory(getStepsUrl)
         steps = item_fetcher("")   
   
def putRequest(url, data) :   
    #base64string = base64.encodestring('%s:%s' % ("mbodoh", "MGBP@$$w0rd")).replace('\n', '')
    base64string = base64.encodestring('%s:%s' % (settings.USERNAME, settings.PASSWORD)).replace('\n', '')
    request = urllib2.Request(url, data=json.dumps(data))
    request.add_header("Authorization", "Basic %s" % base64string)
    request.add_header("Content-Type", "application/json")
    request.get_method = lambda: 'PUT'
    response = urllib2.urlopen(request)
   
    #print("Request headers = ")
    #print request.headers
    #print('\n')
    #print("Response = "+str(response))   

def postRequest(url, data) :
   base64string = base64.encodestring('%s:%s' % (settings.USERNAME, settings.PASSWORD)).replace('\n', '')
   request = urllib2.Request(url, data=json.dumps(data))
   request.add_header("Authorization", "Basic %s" % base64string)
   request.add_header("Content-Type", "application/json")
   request.get_method = lambda: 'POST'
   response = urllib2.urlopen(request)
   return response
   
    #print("Request headers = ")
    #print request.headers
    #print('\n')
    #return response
   
def deleteRequest(url) :   
   base64string = base64.encodestring('%s:%s' % (settings.USERNAME, settings.PASSWORD)).replace('\n', '')
   request = urllib2.Request(url)
   request.add_header("Authorization", "Basic %s" % base64string)
   request.add_header("Content-Type", "application/json")   
   request.get_method = lambda: 'DELETE'
   response = urllib2.urlopen(request)
   return response
   
   #print("Request headers = ")
   #print request.headers
   #print('\n')
   #print("Response = "+str(response))   




   
def fetcher_factory(url):
    """ This factory will create the actual method used to fetch issues or search results from JIRA."""
        
    def get_issue(key):
        """ Given an issue key (i.e. JRA-9) return the JSON representation of it. This is the only place where we deal
            with JIRA's REST API. """

        #print('Fetching.... ' + key)
        req = urllib2.Request(url)
      
        try:
            response = opener.open(req)
                                    
            if response.getcode() == 200:
                issue = json.loads(response.read())
                return issue
            else:                
                print("not http 200 response: " + response.getcode())        
                return None
            response.close()    
         
        except urllib2.HTTPError, e:
            print('Error: get_issue() failed with errorcode %d' % e.code)            
            raise e
            return None
                    
    return get_issue
           
def dquote(str):
    return '\"' + str + '\"'
    
    
    
def build_issue_data(issue):
    
        
    def get_key(issue):       
        return issue['key']

    def get_id(issue):        
        return issue['id']
        
    def get_link_key(link):        
        return link['key']
                
        
    def getDictName(fields, dictName):
        value = ""
        if fields.has_key(dictName):
            if fields.get(dictName) is not None:
                value = fields.get(dictName)['name']

        return value
                        
    def handleIssueStr(linkedIssue, key):
        if len(linkedIssue):
            linkedIssue += "," + key                          
        else:
            linkedIssue = key
        return linkedIssue

    def getArrayValues(fields, fname):
        arrayVals = ""
        if fields.has_key(fname) and fields[fname]:        
            for val in fields[fname]:
                arrayVals = handleIssueStr(arrayVals, val)                
               
        return arrayVals                

        
    def getDictionariesValues(fields, dictName):
        values = ""
        if fields.has_key(dictName) and fields[dictName]:        
            for dict in fields[dictName]: 
                values = handleIssueStr(values, dict['name'])
        return values
        
    # there is a known bug in JIRA that is causing the fallowing function to fail...leaving it here -hopefully a fix is coming soon
    def getCustomFieldNameNumber(issue, fieldName):
        value = ""
        if 'names' in issue:
            names = issue['names']
            for  cfield, name in names.iteritems():
                print cfield + "....................................... " + name
                if name == fieldName:                
                    value = cfield                                        
                    break
        else: 
            print "NO NAMES ---------------------"
                    
        return value        
                
                
    def getCustomFieldValue(fields, fieldNameNumber):
        val = ""
        if fields.has_key(fieldNameNumber) and fields[fieldNameNumber] is not None:    
            val = fields[fieldNameNumber]
        
        return val
        
        
        
    def walk(issue):
        """ issue is the JSON representation of the issue """
                
        fields = issue['fields']

        print( ( issue['key'] + "--->" + fields['summary'] ).encode('ascii', 'ignore') )

        row = issue['key'] + '|' +  fields['summary'] + '|' + getTestCaseStatus(get_id(issue)) + '|'
            
        row = row +  fields["created"] + '|' + fields["updated"] + '|'  + fields['creator']['displayName'] + '|' + GetAssignee(fields) + '|' + GetVersion(fields) + '|' + getTestCaseCycle(get_id(issue)) + '|'
      
        executiondefects = getStepDefects(get_id(issue))
        
        # get linked tests and defects if any
        test = ""
        defect = ""
        story = ""  
        requirement = ""        
        HLrequirement=""
        LLrequirement = ""        
        techTask = ""
        if fields.has_key('issuelinks') and len(fields['issuelinks']):
            for other_issue in issue['fields']['issuelinks']:

                if other_issue.has_key('outwardIssue'):
                    aIssue = other_issue['outwardIssue']
                if other_issue.has_key('inwardIssue'): 
                    aIssue = other_issue['inwardIssue']
                    
                if len(aIssue):                                               
                    linkIssueType = getDictName(aIssue['fields'], 'issuetype')
                    key = get_link_key(aIssue)                   
                    
                    if linkIssueType  == 'Test':
                            test = handleIssueStr(test, key)                         
                                                    
                    if linkIssueType  == 'Bug':
                        defect = handleIssueStr(defect, key)                    
                                           
                    if linkIssueType  == 'Story':
                        story = handleIssueStr(story, key)                    
                        
                    if linkIssueType  == 'Requirement':                        
                        requirement = handleIssueStr(requirement, key)   
                        
                    if linkIssueType  == 'High Level Requirement':                        
                        HLrequirement = handleIssueStr(HLrequirement, key)                           
                        
                    if linkIssueType  == 'Low Level Requirement':                        
                        LLrequirement = handleIssueStr(LLrequirement, key)                           
                    
                    if linkIssueType  == 'Technical task':                        
                        techTask = handleIssueStr(techTask, key)                    

        row = row + test + '|' + defect + '|' + executiondefects + '|' + story + '|' + requirement + '|' + HLrequirement + '|' + LLrequirement + '|' + techTask + '|' + dquote(getArrayValues(fields, "labels")) + '|' + dquote(getDictionariesValues(fields, "components")) + '|' + dquote(getCustomFieldValue(fields, ASSOCIATED_EPIC_FIELD))  + '|' +  dquote(getDictionariesValues(fields, NG7_TEAM_FIELD)) # + '|' + dquote(NG7QAMetrixLib.GetSprintName(fields, SPRINT_FIELD))
        
        return row

    row = walk(issue)
    return row
    
    
def getTestCaseStatus(id):    
    item_fetcher = fetcher_factory(url + settings.ZAPI_EXECUTION_ISSUEID+id)
    executions = item_fetcher("")
    recCount = executions["recordsCount"]
    status = ""
    if recCount > 0:
        stat = executions["executions"][0]["executionStatus"]    # latest test execution
        status = executions["status"][stat]["name"]
 
    
    return status
        
def getTestCaseCycle(id):    
    item_fetcher = fetcher_factory(url + settings.ZAPI_EXECUTION_ISSUEID+id)
    executions = item_fetcher("")
    recCount = executions["recordsCount"]
    cycle = ""
    if recCount > 0:
        cycle = executions["executions"][0]["cycleName"]

    return cycle

def getTestExecutionId_(id):
    """Gets the execution ID when you pass the test case ID"""
    item_fetcher = fetcher_factory(url + settings.ZAPI_EXECUTION_ISSUEID+id)
    executions = item_fetcher("")
    recCount = executions["recordsCount"]
    ExecId = ""
    if recCount > 0:
        ExecId = executions["executions"][0]["id"]
    return str(ExecId)
    
def getStepDefects(id):
    """If there is an execution Gets the details of each step execution and returns a string of all the defects if any"""
    defects = ""
    executiondef = ""
    ExecId = getTestExecutionId(id)
    if len(ExecId):
        defectsurl = url + settings.ZAPI_STEP_EXECUTION_DETAILS+ExecId

        item_fetcher = fetcher_factory(defectsurl)
        defects = item_fetcher("")
    
        #print 'Defects call is '
        #print json.dumps(defects)
        #print 'End defects call with length'
    
        if len(defects):
            for steparr in defects:
                if len(steparr['defects']):

                    for stepdefect in steparr['defects']:
                        if len(executiondef):
                            executiondef += "," + stepdefect['key'] + "(" + stepdefect['status'] + ")"
                        else:
                            executiondef = stepdefect['key'] + "(" + stepdefect['status'] + ")"

    return executiondef

def parse_args():
    parser = optparse.OptionParser()
    parser.add_option('-j', '--jiraServer', dest='jira_url', default='https://test-devtools.mirthcorp.com', help='REQUIRED: JIRA Base URL')
    parser.add_option('-r', '--route', dest='jira_extra_route', default="", help='REQUIRED: JIRA Extra Route (Default is blank)')
    parser.add_option('-z', '--region', dest='region', default='DEV', help='REQUIRED: region tested')
    parser.add_option('-t', '--timestamp', dest='timestamp', default='noStamp', help='REQUIRED: execution timestamp to process')
    parser.add_option('-u', '--update', dest='update', default=False, action='store_true', help='OPTIONAL: decides whether to update the test steps or not')
    parser.add_option('-n', '--testname', dest='testname', default='', help='OPTIONAL: process specified test name only')
    parser.add_option('-d', '--debug', dest='debug', default=False, action='store_true', help='OPTIONAL: shows debug print messages')
    parser.add_option('-a', '--datadebug', dest='datadebug', default=False, action='store_true', help='OPTIONAL: shows data debug print messages')
    parser.add_option('-c', '--suppresscycleprep', dest='suppresscycleprep', default=False, action='store_true', help='OPTIONAL: suppress test cycle prep')
    return parser.parse_args()

    
def GetVersion(fields):
    if len(fields['versions']):
        version = fields['versions'][0]['name']
    else:
        version = ""       
    return version

def GetAssignee(fields):

    assignee = ""
    
    if fields['assignee']:
        assignee = fields['assignee']['displayName']
        #print 'Assignee ', fields['assignee']['displayName']
        
    return assignee   
  
  
def writeIssues(file, issues):
    for issue in issues:
        row = build_issue_data(issue)              
        if len(row):
            file.write(row.encode('ascii', 'ignore') + '\n')  # to avoid the python unicode encode error

    
# ------ main section -------------------      
global url    
global opener

global ASSOCIATED_EPIC_FIELD
ASSOCIATED_EPIC_FIELD = 'customfield_10414'
global NG7_TEAM_FIELD
NG7_TEAM_FIELD = 'customfield_10601'
global SPRINT_FIELD
SPRINT_FIELD = 'customfield_10200'

if __name__ == '__main__' :

   (options, args) = parse_args()
    
   print ("Execution time stamp to process = "+options.timestamp)
   url = options.jira_url + options.jira_extra_route

    # log in and store the cookie in jar
   cj = cookielib.CookieJar()
   opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
   login(url + settings.AUTH_ROUTE, settings.USERNAME, settings.PASSWORD)

    # query for project list and get project id
   projectId = getProjectNumber(settings.PROJECTNAME)
   versionId = getVersionId(projectId, settings.VERSION)
   
   # query for test cycles and get test cycle id
   #testCycleId = getTestCyleId(settings.TESTCYCLENAME, settings.PROJECTID, settings.VERSIONID)
   #print ("project/version/cycle = "+settings.PROJECTID +"/"+settings.VERSIONID +"/"+settings.TESTCYCLEID)
   
   # Delete all executions from the cycle
   #cleanupExecutions(settings.TESTCYCLEID)
   
   # Determine the test cycle name prefix and the artifact, based on the selected execution region
   testCycleName_prefix = ""
   artifact_filename = ""
   if options.region == "integration" :
      testCycleName_prefix = 'Auto Integration'
      artifact_filename = settings.ARTIFACTDIR + "SmokeTestRunTranscript_integration.csv"
   elif options.region == "perf" :
      testCycleName_prefix = 'Auto Perf'
      artifact_filename = settings.ARTIFACTDIR + "SmokeTestRunTranscript_perf.csv"
   elif options.region == "release" :
      testCycleName_prefix = 'Auto Release'
      artifact_filename = settings.ARTIFACTDIR + "SmokeTestRunTranscript_release.csv"
   
   print ("testCycleName_prefix / artifact_filename= "+testCycleName_prefix+" / "+artifact_filename)
   #artifact_filename = options.artifact # WIP
   
   # Update the execution test cycle name to reflect the most recent execution timeStamp
   testCycleName = testCycleName_prefix+" "+options.timestamp
   testCycleId = getTestCyleId(testCycleName_prefix, settings.PROJECTID, settings.VERSIONID)
   
   # MGB 3/2/2015: moving this update to after the artifact is opened and factoring in options.testname
   #print ("testCycleID / new testCycleName= " +str(testCycleId)+" / "+testCycleName)
   #updateTestCycle(testCycleId, "name", testCycleName)
      
   # Initialize runtime variables
   #print ("Initialize runtime variables")
   missingTestContainers = "";
   testName = ""
   testStepPointer = 0
   testExecutionId = ""
   testCaseId = ""
   testCaseBugData = ""
   
   testStepResultDict = {} 
   testStepOrderDict = {} 
   commentDict = {}
   testStepIdList = ""
   testStepDataDict = {}
      
   # Open artifact
   print("Attempting to reference artifact "+artifact_filename)
   
   if os.path.isfile(artifact_filename) is False :
      print ("Could not find artifact " +artifact_filename)
      
   else :
      f = open(artifact_filename)
      csv_f = csv.reader(f)

      # Initialize runtime variables
      missingTestContainers = "";
      testName = ""
      testStepPointer = 0
      testExecutionId = ""
      testCaseId = ""
      testCaseBugData = ""
      testStepResultDict = {}
      testStepOrderDict = {}
      commentDict = {}
      testStepIdList = ""

	  # If running in batch mode (aka empty options.testname), update the test cycle container with the timestamped testCycleName 
	  # and set all execution containers in the test cycle to "UNEXECUTED" ; if testname != "", only that test's container is
	  # updated and the test cycle retains the previous batch execution timestamp.
      if (options.debug is True) :
	     print ("options.testname / options.suppresscycleprep = "+options.testname+" / "+str(options.suppresscycleprep))
		 
      if (not options.testname) and (not options.suppresscycleprep):
         print ("Prep test cycle "+testCycleName+" for batch test execution : timestamp test cycle container name and set all test execution containers in the cycle to status 'UNEXECUTED'")
         updateTestCycle(testCycleId, "name", testCycleName)
         performBulkQuickExecute(testCycleId, "unexecuted")
         

      # Parse artifact and get the test name
      for row in csv_f :
            #print("Row="+str(row))
            testStepId = ""
            comment = ""
            rowSummary = ""
            catastrophicFlag = ""
            testData = ""
            trace = ""
			
            try:
             rowTimeStamp = row[0]
             rowTestName = row[1]
             rowStepDesc = row[2]
             rowSummResult = row[2]
             rowTestStepData = row[3]
             rowTestStepResult = row[4]
            except IndexError:
			 #print ("IndexError exception encountered on row "+str(row))
			 continue

            #if 'SUMMARY' in row :
            try:
             if row[9] == "SUMMARY" :
              #print("SUMMARY found   "+str(row))
              rowSummary = "SUMMARY"
            except IndexError:
			 rowSummary = ""
             #else :
              #rowSummary = ""

            #if ('catastrophic check' in row) or ('doNotDeliver check' in row):
            try:
             if (row[5]=='catastrophic check') or (row[5] == 'doNotDeliver check'):
              #print("catastrophicCheck found   "+str(row))
              catastrophicFlag = row[5] + '\n'
            except IndexError:
             catastrophicFlag = ""
             #else :
              #catastrophicFlag = ""

            #if  (str(row).find('failureTrace') != -1) :
            try:
             if  (str(row[9]).find('failureTrace') != -1) :
              #print("Failure found   "+str(row))
              trace = row[9]
            except IndexError:
              trace = ""
             #else :
              #trace = ""

            if rowTestStepData != '' :
             #print("testData found   "+str(row))
             testData = "Test data: "+rowTestStepData +'\n'
            else :
             testData = ""

            # Skip any artifact rows that are not related to the execution timeStamp
            if rowTimeStamp != options.timestamp :
             continue

            # MGB 12/26/2014: added code to process specific test name only
            if options.testname != "" :
             if rowTestName != options.testname :
              #print("Row testname "+rowTestName+" does not match "+options.testname+"... skipping...")
              continue
            ####
 
            #print("Row="+str(row))
            
            # Skip to the next test if the rowTestName is found in the missingTestContainers
            if rowTestName in missingTestContainers :
              print("Row testName "+rowTestName+" in missingTestContainers "+str(missingTestContainers))
              continue
 
            if (options.datadebug is True) :
             print("ROW: "+str(row))
            if (options.datadebug is True) :
             print("catastrophic="+catastrophicFlag)
            if (options.datadebug is True) :
             print("data="+testData)
            if (options.datadebug is True):
             print("trace="+trace)
            if (options.datadebug is True):
             print("summary="+rowSummary+'\n\n')
				
            # Check if test name.  If a new test name is detected, find the new test container and delete previous executions
            if rowTestName != testName :
                print ("\nProcessing test "+rowTestName+"...")
                testName = rowTestName
                testStepPointer = 0
         
                # Find test container for test name.  If the container is not found, add to the list and iterate to the next test
                testCaseId = ""
                testCaseId = getTestCaseId(testName)
                if testCaseId == "" :
                 missingTestContainers = missingTestContainers + str(testName) + "\n"
                 continue

                # Find test execution container for test case.  If the container is not found, create one.   
                print ("... find/create test execution container for "+testName)
                testExecutionId = getTestExecutionId(testCycleId, testCaseId)
                if testExecutionId == "" : 
                 testExecutionId = createExecution(testCaseId, settings.VERSIONID, testCycleId, settings.PROJECTID)
   
                # Set the executions status of the container to "UNEXECUTED" 
                performQuickExecute(testExecutionId, "unexecuted")
				
            # Check rowSummary flag.  If row is flagged as SUMMARY, populate test-case level execution value.  
            if rowSummary == "SUMMARY" : 
              #print ("Test Step Id list: "+testStepIdList)
              print ("... clean up test execution container for "+testName)
              deleteTestSteps(testCaseId, testStepIdList) # delete test steps that are not listed in the list of executed test steps, testStepIdList
              getTestStepsUrl = url + settings.ZAPI_URLGETEXECTESTSTEPRESULTS + str(testExecutionId)
              item_fetcher = fetcher_factory(getTestStepsUrl)
              testSteps = item_fetcher("")
              #print ("Test Executions = "+str(testSteps))
            
              #getTestStepsUrl = url + settings.ZAPI_URLGETEXECTESTSTEPRESULTS + str(testExecutionId)
              #item_fetcher = fetcher_factory(getTestStepsUrl)
              #testSteps = item_fetcher("")
              #performTestStepExecute(testExecutionId, testStepResultDict, commentDict, testStepDataDict, testSteps)  # 1/12/2014 MGB : added test step data 
            
              print ("... perform test result execution for "+testName)
              performTestStepExecute(testExecutionId, testStepResultDict, commentDict, testSteps)
              #print("QuickExecute for execution "+str(testExecutionId))
              performQuickExecute(testExecutionId, rowSummResult)
            
              # Before processing the next test step, clear the runtime dictionaries
              testStepResultDict = {} 
              testStepOrderDict = {} # clear the test step order dictionary
              testStepIdList = "" # clear the test step Id list
              commentDict = {} # clear the catastrophic flag list
            
            # If the row is not flagged as SUMMARY, add execution information to the hash for execution at the end of the test case processing
            else :
              if (options.debug is True):
               print ("... build test step data for "+testName+" step "+str(testStepPointer))
              comment = catastrophicFlag + testData
              #print ("comment = "+str(comment))              
              #print ("rowTestStepResult = "+str(rowTestStepResult).lower())
              if ((str(rowTestStepResult).lower() == "warning") and (str(testName).find("CatastrophicChecks_") == -1)) :  # process warnings if the test case being processed is CatastrophicChecks_*
               continue
              testStepPointer = testStepPointer + 1
              testStepId = getTestStepId(testCaseId, testStepPointer)
              if testStepId == "" :
                testStepId = createTestStep(testCaseId)
                updateTestStep(testCaseId, testStepId, rowStepDesc, "", "")
              #print ("Adding step to list "+str(testStepId)+","+rowStepDesc)   
              testStepIdList = testStepIdList +" "+str(testStepId)+" "
              testStepOrderDict['\''+str(testStepId)+'\''] = str(testStepPointer)         
              testStepResultDict['\''+str(testStepId)+'\''] = str(rowTestStepResult)
              commentDict['\''+str(testStepId)+'\''] = str(comment)
              testStepDataDict['\''+str(testStepId)+'\''] = str(rowTestStepData)

              #if options.update == "true":
              if (options.update is True) or (str(testName).find("CatastrophicChecks_") != -1) :
               print("... update test case container for "+testName)
               updateTestStep(testCaseId, testStepId, rowStepDesc, "", "")


      # Close artifact reader
      f.close()

   if missingTestContainers != "" : 
      print ("Could not find test containers for the following test cases: " +missingTestContainers)
   
   
   
   
   
   
   
   
