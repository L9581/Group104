@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "script_dir=%~dp0"
if "%script_dir:~-1%"=="\" set "script_dir=%script_dir:~0,-1%"gao

set "src_dir=%script_dir%\codes\src"
set "build_dir=%script_dir%\codes\build"
set "sources_file=%TEMP%\group104_java_sources_%RANDOM%%RANDOM%.txt"

set "javac_exe="
set "java_exe="
set "java_version="

if defined JAVA_HOME (
    if exist "%JAVA_HOME%\bin\javac.exe" (
        for /f "tokens=2 delims= " %%V in ('"%JAVA_HOME%\bin\javac.exe" -version 2^>^&1') do (
            set "java_version=%%V"
        )
        echo %java_version% | findstr /b "21" >nul
        if not errorlevel 1 (
            set "javac_exe=%JAVA_HOME%\bin\javac.exe"
            set "java_exe=%JAVA_HOME%\bin\java.exe"
        )
    )
)

if not defined javac_exe (
    for /d %%D in ("C:\Program Files\Microsoft\jdk-21*") do (
        if exist "%%~fD\bin\javac.exe" (
            set "javac_exe=%%~fD\bin\javac.exe"
            set "java_exe=%%~fD\bin\java.exe"
        )
    )
)

if not defined javac_exe (
    echo Java 21 not found. Set JAVA_HOME to JDK 21 or install Microsoft OpenJDK 21. 1>&2
    exit /b 1
)

for /f "tokens=2 delims= " %%V in ('"%javac_exe%" -version 2^>^&1') do (
    set "java_version=%%V"
)

echo Using javac: %javac_exe%
echo Detected version: %java_version%

echo %java_version% | findstr /b "21" >nul
if errorlevel 1 (
    echo This script requires Java 21. 1>&2
    exit /b 1
)

pushd "%script_dir%"

if exist "%build_dir%" rmdir /s /q "%build_dir%"
mkdir "%build_dir%" >nul 2>&1

if exist "%sources_file%" del /q "%sources_file%"
for /r "%src_dir%" %%F in (*.java) do (
    set "source_file=%%F"
    set "relative_source=!source_file:%script_dir%\=!"
    >>"%sources_file%" echo !relative_source!
)

for %%A in ("%sources_file%") do if %%~zA==0 (
    del /q "%sources_file%"
    popd
    echo No Java sources found in %src_dir% 1>&2
    exit /b 1
)

"%javac_exe%" -d "%build_dir%" @"%sources_file%"
if errorlevel 1 (
    del /q "%sources_file%"
    popd
    exit /b 1
)

del /q "%sources_file%"
"%java_exe%" -cp "%build_dir%" app.Main
set "exit_code=%ERRORLEVEL%"
popd
exit /b %exit_code%
