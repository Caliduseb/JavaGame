@echo off
del Game.jar
del %HOMEDRIVE%%HOMEPATH%\Desktop\Game.jar
jar cmf .\TheGame.mf Game.jar .\com\sebi
jar cmf .\TheGame.mf %HOMEDRIVE%%HOMEPATH%\Desktop\Game.jar .\com\sebi
