#!/bin/sh

if [ -x /usr/bin/build-classpath ]; then
	exec java \
		--add-reads coneforest.psylla=ALL-UNNAMED \
		-p target/classes:$(build-classpath junit junit5) \
		-cp $(build-classpath jline1) \
		coneforest.psylla.Psylla \
		-Isrc/main/psylla \
		"$@"
fi

if [ -r /usr/lib/java-wrappers/java-wrappers.sh ]; then
	. /usr/lib/java-wrappers/java-wrappers.sh
	find_java_runtime openjdk
	JAVA_CLASSPATH=target/lib/psylla.jar
	find_jars jline ./target/lib/psylla.jar
	run_java coneforest.psylla.Psylla -Isrc/main/psylla "$@"
fi
