Format: 1.0
Version: @obs.package.version@-0
Source: @obs.package.name@
Binary: @obs.package.name@
Maintainer: @project.developer.name@ <@project.developer.email@>
Architecture: all
Build-Depends:
	debhelper (>=10),
	openjdk-21-jdk-headless,
	openjdk-21-doc,
	javacc,
	junit4 (>=4.11),
	ant-optional,
	ivy-debian-helper,
	libclianthus-java,
	libclianthus-java-doc,
	libjline-java,
	libjline-java-doc,
	libxslthl-java,
	docbook-xsl-ns (>=1.79),
	docbook-mathml,
	docbook-xsl-saxon,
	sassc,
	libxerces2-java,
	graphviz
Vcs-Git: @project.scm.url@.git
Vcs-Browser: @project.scm.url@
Homepage: @project.url@
Debtransform-Release: 1
Debtransform-Tar: @obs.package.name@-@obs.package.version@.tar.xz
Files:
	00000000000000000000000000000000 0 @obs.package.name@_@obs.package.version@.orig.tar.xz
	00000000000000000000000000000000 0 @obs.package.name@_@obs.package.version@-0.diff.tar.xz
