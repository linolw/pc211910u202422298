@ECHO OFF
SETLOCAL
REM Maven Wrapper (Windows): downloads the Maven distribution on first use.
REM This project uses the only-script approach; no wrapper JAR is needed.
SET "WRAPPER_SCRIPT=%~dp0.mvn\wrapper\run-maven.ps1"
IF NOT EXIST "%WRAPPER_SCRIPT%" (
  ECHO ERROR: Missing .mvn\wrapper\run-maven.ps1
  EXIT /B 1
)
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%WRAPPER_SCRIPT%" %*
EXIT /B %ERRORLEVEL%
