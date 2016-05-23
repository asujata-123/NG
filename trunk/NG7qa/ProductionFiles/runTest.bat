echo *** Clear Files ***
set server=%1
set type=%2

rmdir /S /Q \NG7\qa

echo *** Get Tests ***

svn checkout --no-auth-cache --username ng7service@ip001.nextgen.com --password Abc12345 --force http://ng7svn/svn/qa/trunk/UITests/trunk/ \ng7\qa

echo *** Hack for Artifacts ***

cp \ng7\qa\NG7qa\SmokeTestRunTranscript.csv \

echo *** Execute Tests ***

cd C:\ng7\qa\NG7qa\
java -D"webdriver.chrome.driver"=c:\chromedriver\chromedriver.exe -jar AutoSmokeTest.jar %server% %type%
SET LEVEL=%ERRORLEVEL%

echo *** Move artifact to checkin location and commit/push ***

cp \SmokeTestRunTranscript_%server%.csv \ng7\qa\NG7qa\ProductionFiles\SmokeTestRunTranscript_%server%.csv
cd \ng7\qa\NG7qa
svn commit -m "Build Server check-in" ProductionFiles\SmokeTestRunTranscript_%server%.csv --no-auth-cache --username ng7service@ip001.nextgen.com --password Abc12345

echo *** Cleanup Chrome Web Driver isntances ***

taskkill /f /im chromedriver* || true

echo *** Collect execution timestamp from file c:\ts_%server%.txt ***  

set /p timeStamp=<c:\ts_%server%.txt

echo *** Update JIRA containers via ZAPI on region %server% for timestamp %timeStamp% ***
 
C:\Python279\python.exe ArtifactPostprocessing\ArtifactPostProcessing.py --jiraServer=https://devtools.mirthcorp.com --route=/jira --region=%server% --timestamp=%timeStamp%

exit /b %LEVEL%