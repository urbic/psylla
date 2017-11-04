#!/bin/sh
exec java -cp target/lib/psylla.jar:/usr/share/java/jline.jar coneforest.psylla.Psylla -Isrc/main/psi "$@"
