#
# This script is an example of running instrumentation without Maven or Ant, just a simple command line.
#

export CLASSPATH=build/classes

for file in `ls dist/lib` ; do export  CLASSPATH=$CLASSPATH:dist/lib/$file; done


java -cp $CLASSPATH -DoutputDirectory=build/classes org.javalite.instrumentation.Main


