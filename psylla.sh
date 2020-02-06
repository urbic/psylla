#!/bin/sh
CLASSPATH=$(build-classpath jline1)
exec java -cp target/lib/psylla.jar:$CLASSPATH coneforest.psylla.Psylla -Isrc/main/psylla "$@"
