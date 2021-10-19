#
# spec file for package @obs.package.name@
#
# Copyright (c) 2021 SUSE LLC
#
# All modifications and additions to the file contributed by third parties
# remain the property of their copyright owners, unless otherwise agreed
# upon. The license for this file, and modifications and additions to the
# file, is the same license as for the pristine package itself (unless the
# license for the pristine package is not an Open Source License, in which
# case the license is the MIT License). An "Open Source License" is a
# license that conforms to the Open Source Definition (Version 1.9)
# published by the Open Source Initiative.

# Please submit bugfixes or comments via https://bugs.opensuse.org/
#


%global jline1 jline1
%global jline1_jar %{_jnidir}/jline1/jline-1.0.jar
Name:           @obs.package.name@
Version:		@obs.package.version@
Release:		0
Summary:		@obs.package.summary@
License:		Zlib
Group:			Development/Languages/Other
URL:			https://github.com/urbic/%{name}
Source:			%{name}-%{version}.tar.xz
BuildRequires:	ant
BuildRequires:	java-devel >= 11
BuildRequires:	java-javadoc >= 11
BuildRequires:	javacc
BuildRequires:	junit >= 4.0
BuildRequires:	mvn(jline:jline:1)
BuildRequires:	mvn(net.sf.docbook:docbook-xsl-saxon)
BuildRequires:	mvn(org.apache.ant:ant-junit)
Requires:		java-headless >= 11
Requires:		mvn(jline:jline:1)
Requires(post):	shared-mime-info
Requires(postun):	shared-mime-info
Provides:		config(ant-%{name})
Provides:		mimehandler(application/x-%{name})
BuildArch:		noarch

%description
@obs.package.description@

%package doc
Summary:		Documentation for %{name}
Group:			Documentation/HTML
BuildRequires:	ant-apache-resolver
BuildRequires:	graphviz
BuildRequires:	sassc
BuildRequires:	saxon6
BuildRequires:	xslthl
Requires:		paratype-pt-sans-fonts
BuildRequires:	docbook5-xsl-stylesheets

%description doc
@obs.package.description@
This package contains documentation for %{name}.

%package javadoc
Summary:		Javadocs for %{name}
Group:			Development/Languages/Other
BuildRequires:	fdupes
BuildRequires:	jline1-javadoc
BuildRequires:	junit-javadoc
Requires:		jpackage-utils

%description javadoc
This package contains the API documentation for %{name}.

%prep
%setup -q

%build
LC_ALL=C.UTF-8 %{ant} -v \
	build

%check
LC_ALL=C.UTF-8 \
	%{ant} -v \
	test

%install
LANG=C.UTF-8 %{ant} -v \
	-Ddestdir=%{buildroot} \
	install
%fdupes %{buildroot}%{_javadocdir}/%{name}

%post
%mime_database_post
:

%postun
%mime_database_postun
:

%files
%{_javadir}/*.jar
%{_bindir}/*
%{_datadir}/%{name}
%dir %{_datadir}/vim
%dir %{_datadir}/vim/site
%dir %{_datadir}/vim/site/ftdetect
%dir %{_datadir}/vim/site/syntax
%{_datadir}/vim/site/ftdetect/%{name}.vim
%{_datadir}/vim/site/syntax/%{name}.vim
%{_datadir}/mime/packages/%{name}.xml
%{_datadir}/ant/lib/ant-%{name}.jar
%config %{_sysconfdir}/ant.d/*
%{_mandir}/man1/*.1%{?ext_man}
%dir %{_docdir}/%{name}
%doc %{_docdir}/%{name}/README
%doc %{_docdir}/%{name}/AUTHORS
%dir %{_defaultlicensedir}/%{name}
%license %{_defaultlicensedir}/%{name}/LICENSE

%files doc
%dir %{_docdir}/%{name}
%{_docdir}/%{name}/html
%{_docdir}/%{name}/examples

%files javadoc
%defattr(0644,root,root,0755)
%{_javadocdir}/%{name}

#%%changelog
