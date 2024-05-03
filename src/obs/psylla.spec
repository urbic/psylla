#
# spec file for package @obs.package.name@
#
# Copyright (c) 2024 SUSE LLC
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


Name:           @obs.package.name@
Version:        @obs.package.version@
Release:        0
Summary:        @obs.package.summary@
License:        Zlib
Group:          Development/Languages/Other
URL:            @project.url@
Source:         %{name}-%{version}.tar.xz
BuildRequires:  adobe-sourcecodepro-fonts
BuildRequires:  adobe-sourcesanspro-fonts
BuildRequires:  ivy-local
BuildRequires:  java-devel >= 17
BuildRequires:  java-javadoc >= 17
BuildRequires:  javacc
BuildRequires:  mvn(coneforest:clianthus)
BuildRequires:  mvn(jline:jline:1)
BuildRequires:  mvn(junit:junit) < 5
BuildRequires:  mvn(net.sf.docbook:docbook-xsl-saxon)
BuildRequires:  mvn(org.apache.ant:ant-junit)
Requires:       java-headless >= 17
Requires:       mvn(coneforest:clianthus)
Requires:       mvn(jline:jline:1)
Requires(post): shared-mime-info
Requires(postun): shared-mime-info
Provides:       config(ant-%{name})
Provides:       mimehandler(application/x-%{name})
BuildArch:      noarch

%description
@obs.package.description@

%package doc
Summary:        Documentation for %{name}
Group:          Documentation/HTML
BuildRequires:  ant-apache-resolver
BuildRequires:  docbook5-xsl-stylesheets
BuildRequires:  graphviz
BuildRequires:  mvn(net.sf.xslthl:xslthl)
BuildRequires:  sassc
Requires:       adobe-sourcecodepro-fonts
Requires:       adobe-sourcesanspro-fonts

%description doc
@obs.package.description@
This package contains documentation for %{name}.

%package javadoc
Summary:        Javadocs for %{name}
Group:          Documentation/HTML
BuildRequires:  fdupes
BuildRequires:  clianthus-javadoc
BuildRequires:  jline1-javadoc
BuildRequires:  junit-javadoc
Requires:       jpackage-utils

%description javadoc
This package contains the API documentation for %{name}.

%prep
%setup -q

%build
LC_ALL=C.UTF-8 %{ant} -v -Divy.mode=local build

%check
LC_ALL=C.UTF-8 %{ant} -v -Divy.mode=local test

%install
LC_ALL=C.UTF-8 %{ant} -v -Divy.mode=local -Ddestdir=%{buildroot} install
%fdupes %{buildroot}%{_javadocdir}/%{name}

%add_maven_depmap %{name}/coneforest.%{name}.pom %{name}/coneforest.%{name}.jar
%add_maven_depmap %{name}/coneforest.%{name}.tools.pom %{name}/coneforest.%{name}.tools.jar

%post
%mime_database_post
:

%postun
%mime_database_postun
:

%files -f .mfiles
%{_bindir}/%{name}
%{_datadir}/%{name}
%dir %{_datadir}/vim
%dir %{_datadir}/vim/site
%dir %{_datadir}/vim/site/ftdetect
%dir %{_datadir}/vim/site/syntax
%{_datadir}/vim/site/ftdetect/%{name}.vim
%{_datadir}/vim/site/syntax/%{name}.vim
%{_datadir}/mime/packages/%{name}.xml
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
%{_javadocdir}/%{name}

#%%changelog
