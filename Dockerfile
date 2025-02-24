FROM opensuse/tumbleweed:latest

RUN zypper -n --gpg-auto-import-keys ar -p1 obs://home:urbic:coneforest/openSUSE_Tumbleweed home:urbic:coneforest
RUN zypper -n --gpg-auto-import-keys ar -p2 obs://Java:packages/openSUSE_Tumbleweed Java:packages
RUN zypper -n --gpg-auto-import-keys in -y \
	ant{,-apache-resolver,-junit} \
	git \
	osc \
	ivy \
	ivy-local \
	jacoco \
	'java-devel>=21' \
	javacc \
	javapackages-tools \
	docbook5-xsl-stylesheets \
	'mvn(coneforest:clianthus)' \
	'mvn(jline:jline:1)' \
	'mvn(net.sf.docbook:docbook-xsl-saxon)' \
	'mvn(net.sf.xslthl:xslthl)' \
	'mvn(saxon:saxon)' \
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
#RUN rm ivysettings.xml
RUN git config tar.tar.xz.command 'xz -9'
RUN env -C ${HOME} LANG=C.UTF-8 CLASSPATH=$(build-classpath xerces-j2-xml-apis) JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 \
    ant -Divy.mode=local test coverage build

USER root
RUN env -C ${HOME} LANG=C.UTF-8 JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 ant -Divy.mode=local -Ddestdir=/ install

USER psylla
ENV LANG=en_US.UTF-8
ENTRYPOINT ["/usr/bin/psylla"]
CMD []
