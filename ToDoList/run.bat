@echo off
echo Running ToDoList Application...
echo ----------------------------------
call mvn -version
echo.
call mvn clean compile exec:java
echo.
pause
