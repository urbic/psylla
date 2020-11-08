<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:d="http://docbook.org/ns/docbook"
	xmlns:xslthl="http://xslthl.sf.net"
	xmlns="http://www.w3.org/1999/xhtml"
	exclude-result-prefixes="xslthl d"
	version="1.0"
	>

	<xsl:import href="http://docbook.sourceforge.net/release/xsl-ns/current/highlighting/common.xsl"/>

	<xsl:template match="xslthl:*" mode="xslthl">
		<span>
			<xsl:attribute name="class"><xsl:value-of select="concat('hl-',local-name())"/></xsl:attribute>
			<xsl:apply-templates mode="xslthl"/>
		</span>
	</xsl:template>

</xsl:stylesheet>
