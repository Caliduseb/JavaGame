echo off
del .\com\sebi\*.class
javac -d . ..\src\com\sebi\*.java
java com.sebi.Main
