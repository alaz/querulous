#!/bin/sh
exec java -XX:MaxPermSize=256m -Xmx1024m -jar sbt-launch-0.7.4.jar $@
