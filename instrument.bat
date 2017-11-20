
set CLASSPATH=build/classes
set CLASSPATH=%CLASSPATH%;dist\lib\activejdbc-1.4.13.jar
set CLASSPATH=%CLASSPATH%;dist\lib\javalite-common-1.4.13.jar
set CLASSPATH=%CLASSPATH%;dist\lib\mysql-connector-java-5.0.4.jar
set CLASSPATH=%CLASSPATH%;dist\lib\slf4j-api-1.7.6.jar
set CLASSPATH=%CLASSPATH%;dist\lib\slf4j-simple-1.7.6.jar
set CLASSPATH=%CLASSPATH%;dist\lib\activejdbc-instrumentation-1.4.11.jar
set CLASSPATH=%CLASSPATH%;dist\lib\javassist-3.18.1-GA.jar

java -classpath %CLASSPATH% -DoutputDirectory=build/classes org.javalite.instrumentation.Main
