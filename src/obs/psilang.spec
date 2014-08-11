#
# spec file for package 
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
Url:			http://mech.math.msu.su/~shvetz/projects/psi
Group:			Development/Libraries/Java
Source:			%{name}-%{version}.tar.xz
BuildRequires:	java
BuildRequires:	ant
BuildRequires:	javacc
BuildRequires:	saxon6
BuildRequires:	docbook5-xsl-stylesheets
BuildRequires:	ant-apache-resolver
#BuildRequires:	xml-commons-resolver12
#BuildRequires:	xmlgraphics-batik
BuildRequires:	xerces-j2-xml-apis
#BuildRequires:	xml-commons-jaxp-1.3-apis
#BuildRequires:	xalan-j2
BuildRequires:	xslthl
BuildRequires:	rubygem-sass
#BuildRequires:	xml-commons
#BuildRequires:	xml-commons-resolver-bootstrap
#PreReq:
#Provides:
BuildRoot:      %{_tmppath}/%{name}-%{version}-build

%description

%prep
%setup -q

%build
CLASSPATH=/usr/share/java/xerces-j2-xml-apis.jar ant build

%install
ant \
	-Ddest.dir=%{buildroot}\
	-Djava.dir=%{_javadir}\
	-Dbin.dir=%{_bindir}\
	-Ddefault.doc.dir=%{_defaultdocdir}\
	install

%post

%postun

%files
%defattr(-,root,root)
%{_javadir}/*.jar
%{_bindir}/*
%{_defaultdocdir}/*

#%%changelog

