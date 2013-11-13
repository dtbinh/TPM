::@ECHO OFF

set CLASSPATH=%~dp0

set libPath=%CLASSPATH%lib
set binPath=%CLASSPATH%bin

set boot=%CLASSPATH%lib\boot.jar
set idw-gpl=%CLASSPATH%lib\idw-gpl.jar
set j3dcore=%CLASSPATH%lib\j3dcore.jar
set j3dutils=%CLASSPATH%lib\j3dutils.jar
set jcommon-1012=%CLASSPATH%lib\jcommon-1.0.12.jar
set jfreechart-109=%CLASSPATH%lib\jfreechart-1.0.9.jar
set jlfgr-1_0=%CLASSPATH%lib\jlfgr-1_0.jar
set jython=%CLASSPATH%lib\jython.jar
set madkitapplet=%CLASSPATH%lib\madkitapplet.jar
set madkitkernel=%CLASSPATH%lib\madkitkernel.jar
set madkitutils=%CLASSPATH%lib\madkitutils.jar
set messages=%CLASSPATH%lib\messages.jar
set pythonlib=%CLASSPATH%lib\pythonlib.jar
set simulation=%CLASSPATH%lib\simulation.jar
set system=%CLASSPATH%lib\system.jar
set vecmath=%CLASSPATH%lib\vecmath.jar
set xercesImpl=%CLASSPATH%lib\xercesImpl.jar
set xml-apis=%CLASSPATH%lib\xml-apis.jar
set jdom=%CLASSPATH%lib\jdom.jar

IF EXIST "C:\Program Files (x86)\Java\jre7\bin\java.exe" (
    "C:\Program Files (x86)\Java\jre7\bin\java.exe" -Djava.library.path=%libPath% -Dfile.encoding=Cp1252 -classpath %binPath%;%boot%;%idw-gpl%;%j3dcore%;%j3dutils%;%jcommon-1012%;%jfreechart-109%;%jlfgr-1_0%;%jython%;%madkitapplet%;%madkitkernel%;%madkitutils%;%messages%;%pythonlib%;%simulation%;%system%;%vecmath%;%xercesImpl%;%xml-apis%;%jdom% madkit.boot.Madkit madkit.kernel.Booter --graphics --config turtlekit2.cfg
) ELSE (
    "C:\Program Files\Java\jre7\bin\java.exe" -Djava.library.path=%libPath% -Dfile.encoding=Cp1252 -classpath %binPath%;%boot%;%idw-gpl%;%j3dcore%;%j3dutils%;%jcommon-1012%;%jfreechart-109%;%jlfgr-1_0%;%jython%;%madkitapplet%;%madkitkernel%;%madkitutils%;%messages%;%pythonlib%;%simulation%;%system%;%vecmath%;%xercesImpl%;%xml-apis%;%jdom% madkit.boot.Madkit madkit.kernel.Booter --graphics --config turtlekit2.cfg
)