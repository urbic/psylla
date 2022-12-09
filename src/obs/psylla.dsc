Format: 1.0
Version: @obs.package.version@-0
Source: @obs.package.name@
Binary: @obs.package.name@
Maintainer: Anton Shvetz <shvetz.anton@gmail.com>
Architecture: all
Build-Depends: debhelper (>=9),
	perl-base (>=5.18.2),
	openjdk-17-jdk-headless,
	openjdk-17-doc,
	javacc,
	junit4 (>=4.11),
	ant,
	ant-optional,
	libjline-java,
	libjline-java-doc,
	docbook-xsl-ns (>=1.79),
	docbook-mathml,
	docbook-xsl-saxon,
	libxslthl-java,
	sassc,
	libxerces2-java,
	graphviz
Debtransform-Tar: @obs.package.name@-@obs.package.version@.tar.xz
Files:
	00000000000000000000000000000000 0 @obs.package.name@_@obs.package.version@.orig.tar.xz
	00000000000000000000000000000000 0 @obs.package.name@_@obs.package.version@-0.diff.tar.xz
