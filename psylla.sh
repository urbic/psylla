#!/bin/sh

if [ -x /usr/bin/build-classpath ]; then
	exec java \
		--add-reads coneforest.psylla=ALL-UNNAMED \
		-cp $(build-classpath jline1) \
		-p target/artifacts/jars:$(build-classpath coneforest.clianthus) \
		coneforest.psylla.runtime.Psylla \
		-Isrc/main/psylla \
		"$@"
fi

if [ -r /usr/lib/java-wrappers/java-wrappers.sh ]; then
	. /usr/lib/java-wrappers/java-wrappers.sh
	find_java_runtime openjdk
	JAVA_CLASSPATH=target/lib/psylla.jar
	find_jars jline ./target/artifacts/jars
	run_java coneforest.psylla.runtime.Psylla -Isrc/main/psylla "$@"
fi
