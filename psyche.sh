#!/bin/sh
exec java -cp target/lib/psyche.jar:/usr/share/java/jline.jar coneforest.psi.Psyche "$@"
