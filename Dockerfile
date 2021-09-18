FROM opensuse/leap:15.3

RUN zypper -n --gpg-auto-import-keys ar -p1 obs://home:urbic:java/openSUSE_Leap_15.3 home:urbic:java
RUN zypper -n --gpg-auto-import-keys ar -p1 obs://Java:packages/openSUSE_Leap_15.3 Java:packages
RUN zypper -n --gpg-auto-import-keys in -y \
	ant \
	ant-apache-resolver \
	ant-junit \
	javacc \
	'mvn(jline:jline:1)' \
	docbook5-xsl-stylesheets \
	'mvn(net.sf.docbook:docbook-xsl-saxon)' \
	graphviz \
	sassc \
	'mvn(saxon:saxon)' \
	glibc-locale \
	javapackages-tools \
	xslthl

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
