set server=%1
set type=%2

java -jar AutoSmokeTest.jar %server% %type%

echo *** Collect execution timestamp from file c:\ts_%server%.txt ***  

set /p timeStamp=<c:\ts_%server%.txt

echo timeStamp=%timeStamp%

echo *** Update JIRA containers via ZAPI on region %server% ***

C:\Python27\python.exe ArtifactPostprocessing\ArtifactPostProcessing.py --jiraServer=https://devtools.mirthcorp.com --route=/jira --region=%server% --timestamp=%timeStamp%