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
Url:			https://github.com/urbic/%{name}
Group:			Development/Languages
Source:			%{name}-%{version}.tar.xz
BuildRequires:	ant
BuildRequires:	javacc
BuildRequires:	java-devel >= 1.8.0
BuildRequires:	java-javadoc >= 1.8.0
BuildRequires:	jline
BuildRequires:	shared-mime-info
Requires:		java >= 1.8.0
Requires:		jline
BuildRoot:		%{_tmppath}/%{name}-%{version}-build
BuildArch:		noarch

%description
Psylla is extensible and embeddable Psi implementation written in Java.
Psi is scriptable interpretive PostScript-like programming language.

%package doc
Summary: Documentation for Psylla
Requires:		paratype-pt-sans-fonts
BuildRequires:	ant-apache-resolver
BuildRequires:	docbook5-xsl-stylesheets
BuildRequires:	graphviz
BuildRequires:	rubygem(sass)
BuildRequires:	saxon6
BuildRequires:	xerces-j2-xml-apis
BuildRequires:	xslthl

%description doc
This package contains documentation for Psylla.
Psylla is extensible and embeddable Psi implementation written in Java.
Psi is scriptable interpretive PostScript-like programming language.

%package javadoc
Summary:		Javadocs for %{name}
Group:			Documentation
Requires:		jpackage-utils

%description javadoc
This package contains the API documentation for %{name}.

%prep
%setup -q

%build
LANG=ru_RU.UTF-8 \
CLASSPATH=%{_javadir}/xerces-j2-xml-apis.jar \
	%{ant} build

%install
%{__install} -d %{buildroot}%{_datadir}/%{name}/{%{version},site}
%{__install} -d %{buildroot}%{_javadir}
%{__install} -d %{buildroot}%{_bindir}
%{__install} -d %{buildroot}%{_docdir}/%{name}{,-doc}
%{__install} -d %{buildroot}%{_javadocdir}/%{name}
%{__install} -d %{buildroot}%{_datadir}/vim/site/{ftdetect,syntax}
%{__install} -m 644 target/lib/%{name}.jar %{buildroot}%{_javadir}
%{__install} -m 755 target/bin/* %{buildroot}%{_bindir}
%{__cp} -pr target/doc/{html,examples} %{buildroot}%{_docdir}/%{name}-doc
%{__install} -m 644 target/doc/{README,LICENSE,AUTHORS} %{buildroot}%{_docdir}/%{name}
%{__install} -m 644 target/vim/syntax/*.vim %{buildroot}%{_datadir}/vim/site/syntax
%{__install} -m 644 target/vim/ftdetect/*.vim %{buildroot}%{_datadir}/vim/site/ftdetect
%{__cp} -pr target/doc/javadoc/* %{buildroot}%{_javadocdir}/%{name}
%{__ln_s} %{version} %{buildroot}%{_datadir}/%{name}/current
%{__install} -d %{buildroot}%{_datadir}/mime/packages
%{__install} -m 644 target/mime/%{name}.xml %{buildroot}%{_datadir}/mime/packages
%{__install} -d %{buildroot}%{_mandir}
%{__cp} -pr target/man/en/* %{buildroot}%{_mandir}

%post
%mime_database_post

%postun
%mime_database_postun

%clean
%{__rm} -rf %{buildroot}

%files
%defattr(-,root,root)
%{_javadir}/*.jar
%{_bindir}/*
%dir %{_datadir}/%{name}
%dir %{_datadir}/%{name}/site
%dir %{_datadir}/%{name}/%{version}
%{_datadir}/%{name}/current
%dir %{_datadir}/vim
%dir %{_datadir}/vim/site
%dir %{_datadir}/vim/site/ftdetect
%dir %{_datadir}/vim/site/syntax
%{_datadir}/vim/site/ftdetect/psi.vim
%{_datadir}/vim/site/syntax/psi.vim
%{_datadir}/mime/packages/%{name}.xml
%{_mandir}/man1/*
%doc README LICENSE AUTHORS

%files doc
%defattr(-,root,root)
#%%doc target/doc/{examples,html}
%{_docdir}/%{name}-doc

%files javadoc
%defattr(0644,root,root,0755)
%{_javadocdir}/%{name}

#%%changelog
