SEARCH = "/rest/api/latest/search/?"
ZAPI_EXECUTION_ISSUEID = "/rest/zapi/latest/execution?issueId="
ZAPI_STEP_EXECUTION_DETAILS = "/rest/zapi/latest/stepResult?executionId="
AUTH_ROUTE = "/rest/auth/latest/session"
ZAPI_URLGETPROJLIST = "/rest/zapi/latest/util/project-list"
ZAPI_URLTESTCYCLE = "/rest/zapi/latest/cycle/"
ZAPI_URLTESTEXECUTIONS = "/rest/zapi/latest/execution/"
ZAPI_URLTESTSTEP = "/rest/zapi/latest/teststep/"
ZAPI_URLGETVERSIONS = "/rest/zapi/latest/util/versionBoard-list/?projectId="
ZAPI_URLGETEXECTESTSTEPRESULTS = "/rest/zapi/latest/stepResult/?executionId="
ZAPI_URLTESTSTEPRESULT = "/rest/zapi/latest/stepResult/"
URLGETAUTOMATEDTESTS = "/rest/api/latest/search/?jql=labels%20in%20(Automated)"
ZAPI_URLGETTESTID = "/rest/zapi/latest/zql/executeSearch?zqlQuery=Project%20=%20NGTRUNK"
PROJECTNAME = "NG Trunk"
PROJECTID = '10300'
USERNAME = "qametrics"
PASSWORD = "Q@m3tr1cs"
#USERNAME = "mbodoh"
#PASSWORD = "MGBP@$$w0rd"
TESTCYCLENAME = "Auto DevDeploy"
TESTCYCLEID = "13"
VERSION = "Unscheduled"
VERSIONID = "-1"
SPRINT = "S27 "
ALLOWED_TESTSTEPRESULTS = "pass, fail, blocked"
#ARTIFACTDIR = "C:\\NG7qa\\"
#ARTIFACTDIR = "C:\\ng7\\qa\\NG7qa\\"
ARTIFACTDIR = "C:\\"

#------------------ SAMPLE QUERIES ---------------------------------------------------------------------------------
#JQLFILTER = { 'jql' : 'project in (NGTRUNK) AND issuetype in (Story, Requirement) AND status in (Resolved, Closed) AND resolution not in (Duplicate, Cancelled)' }

##JQLFILTER = { 'jql' : 'project in (NGTRUNK) AND issuetype in (Story,Requirement) AND labels not in (nontestable)  AND (component = EMPTY OR component not in (zzDeprecatedGFS,GFS))'}

#JQLFILTER = { 'jql' : 'project in (NGTRUNK) AND issuetype in (Story,Requirement,"High Level Requirement", "Low Level Requirement")   AND labels not in (nontestable)  AND (component = EMPTY OR component not in (zzDeprecatedGFS,  GFS) )' }

#JQLFILTER = { 'jql' : 'issuetype = Test AND reporter in (raguirre)' }

#JQLFILTER = { 'jql' : 'project in( NGTRUNK) and issuetype = Test' }


#JQLFILTER = { 'jql' : 'issuetype = Story AND reporter in (raguirre)' }

#JQLFILTER = { 'jql' : 'project in (NGTRUNK) AND issuetype in (Story,Requirement) AND (component not in (zzDeprecatedGFS,GFS) OR component = EMPTY ) AND ( labels=empty or labels!=nontestable)' }

#JQLFILTER = { 'jql' : 'project in (NGTRUNK) AND issuetype in (Story,Requirement, "High Level Requirement", "Low Level Requirement") AND (component not in (zzDeprecatedGFS, GFS) OR component=EMPTY ) AND (labels=empty or labels !=nontestable)' }

#JQLFILTER =  { 'jql' : 'project in (NGTRUNK) AND issuetype in (Story,Requirement, "High Level Requirement", "Low Level Requirement") AND (component = EMPTY OR component not in (zzDeprecatedGFS, GFS) ) AND ( labels = empty or labels != nontestable ) AND "Associated Epic" !~ GFS' }

#JQLFILTER = { 'jql' : 'project  in (NGTRUNK) AND issuetype in (Story,Requirement,"High Level Requirement", "Low Level Requirement") and (labels=EMPTY or labels=nontestable)' }

#JQLFILTER = { 'jql' : 'project in (NGTRUNK) AND issuetype in (Story,Requirement,"High Level Requirement", "Low Level Requirement")' }

#JQLFILTER = { 'jql' : 'project in (NGTRUNK) AND issuetype in ("High Level Requirement")' }

#JQLFILTER = { 'jql' : 'issue=NGTRUNK-15000' }

JQLFILTER = { 'jql' : 'issue=NGTRUNK-13777' }

#JQLFILTER = { 'jql' : 'project=10300 and issuetype=11' }


## ----- current traceability JQL query: ----------------
#JQLFILTER = { 'jql' : 'project in (NGTRUNK) AND issuetype in (Story,Requirement,"High Level Requirement", "Low Level Requirement")' }
#JQLFILTER = { 'jql' : 'project in (NGTRUNK) AND issuetype in (Story,Requirement,"High Level Requirement", "Low Level Requirement") and "NG7 Team" not in (NG7Documentation,"Configuration Change Request",Architecture,GFS-Anand,DevOps,Clinical-Ed,Core-Nick)' }
####JQLFILTER = 'jql=project+in+(NGTRUNK)+AND+issuetype+in+(Story,Requirement,"High+Level+Requirement","Low+Level+Requirement")'

#-----------------------------------------------------------------------------------------------------

##---- Test Case Execution --------------
JQL_TEST_CASE_EXECUTION_FILTER = { 'jql' : 'project in( NGTRUNK) and issuetype = Test' }
##JQL_TEST_CASE_EXECUTION_FILTER = { 'jql' : 'project in( NGTRUNK) and issuetype = Test and issue=NGTRUNK-17496' }




OUTPUTFILE = "traceability.txt"
SEARCHMAXRESULTS = 250

