@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT
if exist ".\data\" (
    del /Q ".\data\*.*"
)

REM compile all Java files recursively into the bin folder
@REM REM javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java

set SRC_DIR=..\src\main\java
for /R "%SRC_DIR%" %%f in (*.java) do (
    javac -cp "%SRC_DIR%" -Xlint:none -d ..\bin "%%f"
    IF ERRORLEVEL 1 (
        echo ********** BUILD FAILURE **********
        exit /b 1
    )
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin Bernard < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
