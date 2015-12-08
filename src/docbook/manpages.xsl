<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:d="http://docbook.org/ns/docbook"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:xslthl="http://xslthl.sf.net"
	xmlns:lxslt="http://xml.apache.org/xslt"
	xmlns:exsl="http://exslt.org/common"
	exclude-result-prefixes="xlink lxslt d xslthl exsl #default"
	version="1.1"
	>
	<xsl:import href="http://docbook.sourceforge.net/release/xsl-ns/current/manpages/profile-docbook.xsl"/>

	<xsl:param name="header.rule" select="0"/>
	<xsl:param name="footer.rule" select="0"/>
	<xsl:param name="blurb.on.titlepage.enabled" select="1"/>
	<xsl:param name="callout.graphics" select="0"/>
	<xsl:param name="callout.unicode" select="1"/>
	<xsl:param name="textinsert.extension" select="1"/>
	<xsl:param name="use.id.as.filename" select="1"/>
	<xsl:param name="index.method">kosek</xsl:param>
	<xsl:param name="variablelist.term.break.after" select="1"/>
	<xsl:param name="index.on.type" select="1"/>
	<xsl:param name="use.extensions" select="1"/>
	<xsl:param name="variablelist.term.separator"/>
	<xsl:param name="make.year.ranges" select="1"/>
	<xsl:param name="shortcut.icon"/>
	<xsl:param name="make.clean.html" select="1"/>


	<xsl:template match="/">
		<xsl:variable name="content" select="."/>
		<xsl:apply-imports select="$content"/>
	</xsl:template>



	<xsl:template match="d:subscript">
		<xsl:text>_</xsl:text>
		<xsl:variable name="content">
			<xsl:apply-templates/>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="string-length($content)=1">
				<xsl:value-of select="$content"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>[</xsl:text>
				<xsl:value-of select="$content"/>
				<xsl:text>]</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="d:superscript">
		<xsl:text>^</xsl:text>
		<xsl:apply-imports/>
	</xsl:template>

	<xsl:template match="d:literal">
		<xsl:text>"</xsl:text>
		<xsl:apply-imports/>
		<xsl:text>"</xsl:text>
	</xsl:template>

	<xsl:template match="d:token">
		<!--xsl:call-template name="bold">
			<xsl:with-param name="node" select="exsl:node-set(.)"/>
			<xsl:with-param name="context" select="."/>
		</xsl:call-template-->
		<xsl:value-of select="."/>
	</xsl:template>

	<xsl:template match="d:filename">
		<xsl:call-template name="bold">
			<xsl:with-param name="node" select="exsl:node-set(.)"/>
			<xsl:with-param name="context" select="."/>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="d:varlistentry|d:glossentry">
		<xsl:text>.PP&#xA;</xsl:text>
		<xsl:for-each select="d:term|d:glossterm">
			<xsl:variable name="content">
				<xsl:apply-templates/>
			</xsl:variable>
			<xsl:value-of select="normalize-space($content)"/>
			<xsl:choose>
				<xsl:when test="position()=last()"/>
				<xsl:otherwise>
					<xsl:value-of select="$variablelist.term.separator"/>
					<xsl:if test="not($variablelist.term.break.after='0')">
						<xsl:text>&#xA;.br&#xA;</xsl:text>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
		<xsl:text>&#xA;.RS</xsl:text>
		<xsl:if test="not($list-indent='')">
			<xsl:text> </xsl:text>
			<xsl:value-of select="$list-indent"/>
		</xsl:if>
		<!--xsl:text>&#xA;</xsl:text-->
		<xsl:apply-templates/>
		<xsl:text>.RE&#xA;</xsl:text>
	</xsl:template>

	<xsl:template match="d:productionset">
		<xsl:variable name="title.wrapper">
			<xsl:value-of select="normalize-space(d:title[1])"/>
		</xsl:variable>
		<xsl:text>.PP&#xA;</xsl:text>
		<xsl:call-template name="bold">
			<xsl:with-param name="node" select="exsl:node-set($title.wrapper)"/>
			<xsl:with-param name="context" select="."/>
		</xsl:call-template>
		<!--xsl:text>&#xA;.PP&#xA;</xsl:text-->
		<xsl:for-each select="d:production">
			<xsl:text>&#xA;.PP&#xA;</xsl:text>
			<xsl:text>.RS 4&#xA;</xsl:text>
			<!--xsl:text>[</xsl:text>
			<xsl:value-of select="position()"/>
			<xsl:text>]	</xsl:text-->
			<xsl:apply-templates select="."/>
			<xsl:text>&#xA;.RE&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="d:production">
		<xsl:variable name="content">
			<xsl:apply-templates select="d:lhs"/>
			<xsl:text> </xsl:text>
			<xsl:copy-of select="$ebnf.assignment"/>
			<xsl:text> </xsl:text>
			<xsl:apply-templates select="d:rhs"/>
		</xsl:variable>
		<xsl:value-of select="normalize-space($content)"/>
	</xsl:template>

	<xsl:template name="mixed-block">
		<xsl:for-each select="node()">
			<xsl:choose>
				<xsl:when test="self::d:address|self::d:literallayout|self::d:programlisting|self::d:screen|self::d:synopsis">
					<xsl:text>&#10;</xsl:text>
					<xsl:text>.sp&#10;</xsl:text>
					<xsl:call-template name="mark.up.block.start"/>
					<xsl:apply-templates select="."/>
				</xsl:when>
				<xsl:when test="(self::d:itemizedlist|self::d:orderedlist|
						self::d:variablelist|self::d:glosslist|
						self::d:simplelist[@type !='inline']|
						self::d:segmentedlist|
						self::d:caution|self::d:important|
						self::d:note|self::d:tip|self::d:warning|
						self::d:table|self::d:informaltable|
						self::d:productionset
						)">
					<xsl:call-template name="mark.up.block.start"/>
					<xsl:apply-templates select="."/>
				</xsl:when>
				<xsl:when test="self::text()">
					<xsl:variable name="content">
						<xsl:apply-templates select="."/>
					</xsl:variable>
					<xsl:if test="starts-with(translate(.,'&#9;&#10;&#13; ','    '), ' ')
							and preceding-sibling::node()[1][name(.)!='']
							and normalize-space($content) != ''
							and not(
								preceding-sibling::*[1][
								self::d:caution or
								self::d:important or
								self::d:note or
								self::d:tip or
								self::d:warning or
								self::d:variablelist or
								self::d:glosslist or
								self::d:itemizedlist or
								self::d:orderedlist or
								self::d:segmentedlist or
								self::d:procedure or
								self::d:address or
								self::d:literallayout or
								self::d:programlisting or
								self::d:synopsis or
								self::d:screen or
								self::d:table or
								self::d:informaltable or
								self::d:productionset
								])">
						<xsl:text>&#10;</xsl:text>
					</xsl:if>
					<xsl:value-of select="normalize-space($content)"/>
					<xsl:if test="(translate(substring(., string-length(.), 1),'&#9;&#10;&#13; ','    ')  = ' '
							and following-sibling::node()[1][name(.)!=''])
							or following-sibling::node()[1][self::comment()]
							or following-sibling::node()[1][self::processing-instruction()]
							">
						<xsl:if test="normalize-space($content) != ''
								or concat(normalize-space($content), ' ') != ' '">
							<xsl:text>&#10;</xsl:text>
						</xsl:if>
					</xsl:if>
				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="content">
						<xsl:apply-templates select="."/>
					</xsl:variable>
					<xsl:value-of select="normalize-space($content)"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
		<xsl:call-template name="mark.up.block.end"/>
	</xsl:template>

</xsl:stylesheet>
