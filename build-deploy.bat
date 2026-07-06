@echo off
setlocal EnableExtensions EnableDelayedExpansion

cd /d "%~dp0"

set "VERSION_FILE=.deploy-version"

if not exist "%VERSION_FILE%" (
    >"%VERSION_FILE%" echo 0.0.0
)

set /p CURRENT_VERSION=<"%VERSION_FILE%"
for /f "tokens=1-3 delims=." %%A in ("%CURRENT_VERSION%") do (
    set "MAJOR=%%A"
    set "MINOR=%%B"
    set "PATCH=%%C"
)

set /a PATCH+=1
set "VERSION=%MAJOR%.%MINOR%.%PATCH%"

echo Building Java26Demo version %VERSION%...
call gradlew.bat clean bootJar -PappVersion=%VERSION%
if errorlevel 1 (
    echo Build failed. Version was not incremented.
    exit /b 1
)

set "JAR=build\libs\Java26Demo-%VERSION%.jar"
if not exist "%JAR%" (
    echo Expected JAR was not created: %JAR%
    exit /b 1
)

if exist "eb-deploy" rmdir /s /q "eb-deploy"
mkdir "eb-deploy"

copy /y "Dockerfile" "eb-deploy\Dockerfile" >nul
copy /y "%JAR%" "eb-deploy\Java26Demo-%VERSION%.jar" >nul

powershell.exe -NoProfile -Command "Compress-Archive -Path '.\eb-deploy\*' -DestinationPath '.\deploy.zip' -Force"
if errorlevel 1 (
    echo Deployment archive creation failed. Version was not incremented.
    exit /b 1
)

>"%VERSION_FILE%" echo %VERSION%

echo.
echo Build complete.
echo Version: %VERSION%
echo JAR: %JAR%
echo Deployment archive: deploy.zip

endlocal
