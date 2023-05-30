FROM opensuse/tumbleweed:latest

RUN zypper -n --gpg-auto-import-keys ar -p1 obs://home:urbic:coneforest/openSUSE_Tumbleweed home:urbic:coneforest
RUN zypper -n --gpg-auto-import-keys ar -p1 obs://Java:packages/openSUSE_Tumbleweed Java:packages
RUN zypper -n --gpg-auto-import-keys in -y \
	ant{,-apache-resolver,-junit} \
	ivy \
	javacc \
	javapackages-tools \
	docbook5-xsl-stylesheets \
	'mvn(jline:jline:1)' \
	'mvn(net.sf.docbook:docbook-xsl-saxon)' \
	'mvn(net.sf.xslthl:xslthl)' \
	glibc-locale \
	graphviz \
	shadow \
	sassc

USER root
RUN useradd -ms /bin/sh psylla

ENV HOME /home/psylla
RUN mkdir -p ${HOME}

WORKDIR ${HOME}
COPY . ${HOME}
USER root
RUN chown -R psylla:users ${HOME}

USER psylla
RUN env -C ${HOME} LANG=C.UTF-8 CLASSPATH=$(build-classpath xerces-j2-xml-apis) JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 \
    ant build

USER root
RUN env -C ${HOME} LANG=C.UTF-8 JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 ant install -Ddestdir=/

USER psylla
ENV LANG=en_US.UTF-8
ENTRYPOINT ["/usr/bin/psylla"]
CMD []
