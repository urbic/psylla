#!/bin/sh

if [ -x /usr/bin/build-classpath ]; then
	CLASSPATH=$(build-classpath jline1)
	exec java -cp target/lib/psylla.jar:$CLASSPATH coneforest.psylla.Psylla -Isrc/main/psylla "$@"
fi

if [ -r /usr/lib/java-wrappers/java-wrappers.sh ]; then
	. /usr/lib/java-wrappers/java-wrappers.sh
	find_java_runtime openjdk
	JAVA_CLASSPATH=target/lib/psylla.jar
	find_jars jline ./target/lib/psylla.jar
	run_java coneforest.psylla.Psylla -Isrc/main/psylla "$@"
fi
