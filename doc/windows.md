start/stop java application script

```cmd
@ECHO OFF
IF "%1"=="start" (
    ECHO start your app name
    start "yourappname" java -jar -Dspring.profiles.active=prod yourappname-0.0.1.jar
) ELSE IF "%1"=="stop" (
    ECHO stop your app name
    TASKKILL /FI "WINDOWTITLE eq yourappname"
) ELSE (
    ECHO please, use "run.bat start" or "run.bat stop"
)
pause
```

