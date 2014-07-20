<?xml version="1.0" encoding="UTF-8"?>
<!--
$URL: svn+ssh://concyclic@svn.code.sf.net/p/mathjax4svg/code/sources/site/xhtml.xsl $
$Id: xhtml.xsl 5 2014-06-05 01:35:35Z concyclic $
-->
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:d="http://docbook.org/ns/docbook"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:xslthl="http://xslthl.sf.net"
	xmlns:lxslt="http://xml.apache.org/xslt"
	exclude-result-prefixes="xlink lxslt d xslthl #default"
	version="1.1"
	>
	<xsl:import href="http://docbook.sourceforge.net/release/xsl-ns/current/manpages/profile-docbook.xsl"/>
	<xsl:import href="highlight.xsl"/>

	<xsl:param name="header.rule" select="0"/>
	<xsl:param name="footer.rule" select="0"/>
	<xsl:param name="blurb.on.titlepage.enabled" select="1"/>
	<xsl:param name="callout.graphics" select="0"/>
	<xsl:param name="callout.unicode" select="1"/>
	<xsl:param name="textinsert.extension" select="1"/>
	<xsl:param name="use.id.as.filename" select="1"/>
	<xsl:param name="index.method">kosek</xsl:param>
	<xsl:param name="use.svg" select="1"/>
	<xsl:param name="variablelist.term.break.after" select="1"/>
	<xsl:param name="index.on.type" select="1"/>
	<xsl:param name="use.extensions" select="1"/>
	<xsl:param name="make.graphic.viewport" select="1"/>
	<xsl:param name="highlight.source" select="1"/>
	<xsl:param name="variablelist.term.separator"/>
	<xsl:param name="graphicsize.extension" select="0"/>
	<xsl:param name="graphicsize.use.img.src.path" select="1"/>
	<xsl:param name="make.year.ranges" select="1"/>
	<xsl:param name="shortcut.icon"/>
	<xsl:param name="keep.relative.image.uris" select="1"/>
	<xsl:param name="make.clean.html" select="1"/>

	<xsl:output method="xml" omit-xml-declaration="yes"/>
	
	<xsl:template name="root.attributes">
		<xsl:attribute name="xml:lang">
			<xsl:value-of select="ancestor-or-self::d:*/@xml:lang"/>
		</xsl:attribute>
	</xsl:template>

	<xsl:template match="d:subscript">
		<xsl:text>_</xsl:text>
		<xsl:apply-imports/>
	</xsl:template>

	<xsl:template match="d:superscript">
		<xsl:text>^</xsl:text>
		<xsl:apply-imports/>
	</xsl:template>

</xsl:stylesheet>
