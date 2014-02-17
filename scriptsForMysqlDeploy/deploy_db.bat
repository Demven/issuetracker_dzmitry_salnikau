@echo off

rem mysql home directory
set mysql_home=D:\Java\MySQL\Enstalled\MySQL Server 5.6

rem database root user for schema, user and tables creation
set db_user=root

set PATH=%mysql_home%\bin;

if exist "%mysql_home%" goto OK
echo Please set correct MySQL path into mysql_home variable.
pause
exit
:OK

echo Creating schema and tables. Filling with data...
mysql --user=%db_user% --force < script.sql

pause