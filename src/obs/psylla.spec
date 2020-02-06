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
Summary:		@obs.package.summary@
Url:			https://github.com/urbic/%{name}
Group:			Development/Languages/Other
Source:			%{name}-%{version}.tar.xz
Source1:        https://cdn.docbook.org/release/xsl-nons/1.79.2/extensions/saxon65.jar
BuildRequires:	ant
BuildRequires:	javacc
BuildRequires:	java-devel >= 1.8.0
BuildRequires:	java-javadoc >= 1.8.0
%if 0%{?mageia}
BuildRequires:	locales-ru
BuildRequires:	jline1
Requires:		jline1
%else
BuildRequires:	jline
Requires:		jline
%endif
BuildRequires:	junit >= 4.0
BuildRequires:	ant-junit
Provides:		config(ant-%{name})
Provides:		mimehandler(application/x-%{name})
Requires:		java-headless >= 1.8.0
Requires(post):	shared-mime-info
Requires(postun):	shared-mime-info
BuildRoot:		%{_tmppath}/%{name}-%{version}-build
BuildArch:		noarch

%description
@obs.package.description@

%package doc
Summary:		Documentation for %{name}
Group:			Documentation/HTML
Requires:		paratype-pt-sans-fonts
BuildRequires:	ant-apache-resolver
%if 0%{?mageia}
BuildRequires:	docbook5-style-xsl
BuildRequires:	java-1.8.0-openjdk-javadoc
%else
BuildRequires:	docbook5-xsl-stylesheets
%endif
BuildRequires:	graphviz
BuildRequires:	sassc
BuildRequires:	saxon6
%if 0%{?fedora}||0%{?mageia}
BuildRequires:	xerces-j2
%else
#BuildRequires:	xerces-j2-xml-apis
%endif
BuildRequires:	xslthl

%if 0%{?fedora}
BuildRequires:	ghostscript-core
%endif

%description doc
@obs.package.description@
This package contains documentation for %{name}.

%package javadoc
Summary:		Javadocs for %{name}
Group:			Development/Languages/Other
BuildRequires:	junit-javadoc
BuildRequires:	fdupes
Requires:		jpackage-utils

%description javadoc
This package contains the API documentation for %{name}.

%prep
%setup -q
%{__mkdir} -p target/lib
%{__cp} %{S:1} target/lib

%build
LANG=ru_RU.UTF-8 \
CLASSPATH=%{_javadir}/xerces-j2-xml-apis.jar \
	%{ant} build

%check
LANG=ru_RU.UTF-8 \
CLASSPATH=%{_javadir}/xerces-j2-xml-apis.jar \
	%{ant} test

%install
LANG=ru_RU.UTF-8 \
CLASSPATH=%{_javadir}/xerces-j2-xml-apis.jar \
	%{ant} install -Ddestdir=%{buildroot} -Dconfig.docdir=%{_docdir} -Dconfig.licensedir=%{_defaultlicensedir}
%fdupes %{buildroot}%{_javadocdir}/%{name}/jquery

%post
%mime_database_post
:

%postun
%mime_database_postun
:

%clean
%{__rm} -rf %{buildroot}

%files
%defattr(-,root,root)
%{_javadir}/*.jar
%{_bindir}/*
%dir %{_datadir}/%{name}
%dir %{_datadir}/%{name}/site
%dir %{_datadir}/%{name}/%{version}
%{_datadir}/%{name}/%{version}/*
%{_datadir}/%{name}/current
%dir %{_datadir}/vim
%dir %{_datadir}/vim/site
%dir %{_datadir}/vim/site/ftdetect
%dir %{_datadir}/vim/site/syntax
%{_datadir}/vim/site/ftdetect/%{name}.vim
%{_datadir}/vim/site/syntax/%{name}.vim
%{_datadir}/mime/packages/%{name}.xml
%{_datadir}/ant/lib/ant-%{name}.jar
%config %{_sysconfdir}/ant.d/*
%{_mandir}/man1/*
%dir %{_docdir}/%{name}
%doc %{_docdir}/%{name}/README
%doc %{_docdir}/%{name}/AUTHORS
%dir %{_defaultlicensedir}/%{name}
%license %{_defaultlicensedir}/%{name}/LICENSE

%files doc
%defattr(-,root,root)
%dir %{_docdir}/%{name}
%{_docdir}/%{name}/html
%{_docdir}/%{name}/examples

%files javadoc
%defattr(0644,root,root,0755)
%{_javadocdir}/%{name}

#%%changelog
