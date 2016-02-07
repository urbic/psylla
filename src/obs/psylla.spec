#
# spec file for package @obs.package.name@
#
# Copyright (c) 2014 SUSE LINUX Products GmbH, Nuernberg, Germany.
#
# All modifications and additions to the file contributed by third parties
# remain the property of their copyright owners, unless otherwise agreed
# upon. The license for this file, and modifications and additions to the
# file, is the same license as for the pristine package itself (unless the
# license for the pristine package is not an Open Source License, in which
# case the license is the MIT License). An "Open Source License" is a
# license that conforms to the Open Source Definition (Version 1.9)
# published by the Open Source Initiative.

# Please submit bugfixes or comments via http://bugs.opensuse.org/
#

Name:           @obs.package.name@
Version:		@obs.package.version@
Release:		0
License:		Zlib
Summary:		Psi programming language
Url:			https://github.com/urbic/psylla
Group:			Development/Libraries/Java
Source:			%{name}-%{version}.tar.xz
BuildArch:		noarch
BuildRequires:	java-devel >= 1.8.0
BuildRequires:	jline
BuildRequires:	ant
BuildRequires:	javacc
Requires:		java >= 1.8.0
Requires:		jline
BuildRoot:      %{_tmppath}/%{name}-%{version}-build

%description
Psylla is extensible and embeddable Psi implementation written in Java.
Psi is scriptable interpretive PostScript-like programming language.

%package doc
Summary: Documentation for Psylla
Requires:		paratype-pt-sans-fonts
BuildRequires:	saxon6
BuildRequires:	docbook5-xsl-stylesheets
BuildRequires:	ant-apache-resolver
BuildRequires:	xerces-j2-xml-apis
BuildRequires:	xslthl
BuildRequires:	rubygem-sass
BuildRequires:	graphviz

%description doc
This package contains documentation for Psylla.
Psylla is extensible and embeddable Psi implementation written in Java.
Psi is scriptable interpretive PostScript-like programming language.

%package javadoc
Summary:	Javadocs for %{name}
Group:		Documentation
Requires:	jpackage-utils

%description javadoc
This package contains the API documentation for %{name}.

%prep
%setup -q

%build
LANG=ru_RU.UTF-8 \
CLASSPATH=/usr/share/java/xerces-j2-xml-apis.jar \
	ant build javadoc

%install
%{__install} -d %{buildroot}%{_datadir}
%{__install} -d %{buildroot}%{_javadir}
%{__install} -d %{buildroot}%{_bindir}
%{__install} -d %{buildroot}%{_defaultdocdir}/%{name}{,-doc}
%{__install} -d %{buildroot}%{_javadocdir}/%{name}
%{__install} -d %{buildroot}%{_datadir}/vim/site/{ftdetect,syntax}
%{__install} -m 644 target/lib/%{name}.jar %{buildroot}%{_datadir}/java
%{__install} -m 755 target/bin/* %{buildroot}%{_bindir}
%{__cp} -pr target/doc/{html,examples} %{buildroot}%{_defaultdocdir}/%{name}-doc
%{__install} -m 644 target/doc/{README,LICENSE,AUTHORS} %{buildroot}%{_defaultdocdir}/%{name}
%{__install} -m 644 target/vim/syntax/*.vim %{buildroot}%{_datadir}/vim/site/syntax
%{__install} -m 644 target/vim/ftdetect/*.vim %{buildroot}%{_datadir}/vim/site/ftdetect
%{__cp} -pr target/doc/javadoc/* %{buildroot}%{_javadocdir}/%{name}

%clean
%{__rm} -rf %{buildroot}

%files
%defattr(-,root,root)
%{_javadir}/*.jar
%{_bindir}/*
%dir %{_datadir}/vim
%dir %{_datadir}/vim/site
%dir %{_datadir}/vim/site/ftdetect
%dir %{_datadir}/vim/site/syntax
%{_datadir}/vim/site/ftdetect/psi.vim
%{_datadir}/vim/site/syntax/psi.vim
%doc README LICENSE AUTHORS

%files doc
%defattr(-,root,root)
%doc target/doc/{examples,html}

%files javadoc
%defattr(0644,root,root,0755)
%{_javadocdir}/%{name}

#%%changelog
