@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "script_dir=%~dp0"
if "%script_dir:~-1%"=="\" set "script_dir=%script_dir:~0,-1%"

set "src_dir=%script_dir%\codes\src"
set "build_dir=%script_dir%\codes\build"
set "sources_file=%TEMP%\group104_java_sources_%RANDOM%%RANDOM%.txt"

if exist "%build_dir%" rmdir /s /q "%build_dir%"
mkdir "%build_dir%" >nul 2>&1

if exist "%sources_file%" del /q "%sources_file%"

for /r "%src_dir%" %%F in (*.java) do (
    >>"%sources_file%" echo %%F
)

if not exist "%sources_file%" (
    echo No Java sources found in %src_dir% 1>&2
    exit /b 1
)

for %%A in ("%sources_file%") do if %%~zA==0 (
    del /q "%sources_file%"
    echo No Java sources found in %src_dir% 1>&2
    exit /b 1
)

javac -d "%build_dir%" @"%sources_file%"
if errorlevel 1 (
    del /q "%sources_file%"
    exit /b 1
)

del /q "%sources_file%"
java -cp "%build_dir%" app.RecruitmentApp
