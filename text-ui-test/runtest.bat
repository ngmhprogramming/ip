@ECHO OFF

REM create bin directory if it doesn't exist
if exist ..\bin rmdir /S /Q ..\bin
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT
if exist ".\data\" (
    del /Q ".\data\*.*"
)

REM compile all Java files recursively into the bin folder
@REM REM javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java

@REM set SRC_DIR=..\src\main\java
@REM for /R "%SRC_DIR%" %%f in (*.java) do (
@REM     javac -cp "%SRC_DIR%" -Xl   int:none -d ..\bin "%%f"
@REM     IF ERRORLEVEL 1 (
@REM         echo ********** BUILD FAILURE **********
@REM         exit /b 1
@REM     )
@REM )
REM no error here, errorlevel == 0

REM gather all .java files recursively into a temp file
set SRC_DIR=..\src\main\java
set FILE_LIST=%TEMP%\java_files.txt

if exist "%FILE_LIST%" del "%FILE_LIST%"
for /R "%SRC_DIR%" %%f in (*.java) do echo %%f >> "%FILE_LIST%"

REM compile all files at once
javac -cp "%SRC_DIR%" -Xlint:none -d ..\bin @"%FILE_LIST%"
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin bernard.core.Bernard < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT

REM cleanup temp file
if exist "!FILE_LIST!" del "!FILE_LIST!"