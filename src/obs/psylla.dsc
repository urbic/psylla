Format: 1.0
Version: @obs.package.version@-0
Source: @obs.package.name@
Binary: @obs.package.name@
Maintainer: Anton Shvetz <tz@sectorb.msk.ru>
Architecture: all
Build-Depends: debhelper (>=9),
	perl-base (>=5.18.2),
	openjdk-11-jdk-headless,
	openjdk-11-doc,
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
