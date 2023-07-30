<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:d="http://docbook.org/ns/docbook"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:xslthl="http://xslthl.sf.net"
	xmlns:lxslt="http://xml.apache.org/xslt"
	exclude-result-prefixes="xlink lxslt d xslthl #default"
	version="1.0"
	>
	<xsl:import href="http://docbook.sourceforge.net/release/xsl-ns/current/xhtml5/profile-chunk.xsl"/>

	<xsl:import href="highlight.xsl"/>
	<xsl:import href="autoidx.xsl"/>
	<xsl:import href="http://docbook.sourceforge.net/release/xsl-ns/current/xhtml/autoidx-kosek.xsl"/>

	<xsl:param name="admon.graphics" select="1"/>
	<xsl:param name="admon.graphics.extension">.svg</xsl:param>
	<xsl:param name="admon.graphics.path">admonitions/</xsl:param>
	<xsl:param name="admon.style"/>
	<xsl:param name="arg.rep.repeat.str">…</xsl:param>
	<xsl:param name="bibliography.numbered" select="1"/>
	<xsl:param name="bibliography.style">iso690</xsl:param>
	<xsl:param name="blurb.on.titlepage.enabled" select="1"/>
	<xsl:param name="callout.graphics" select="0"/>
	<xsl:param name="callout.unicode" select="1"/>
	<xsl:param name="callout.unicode.font">STIX General</xsl:param>
	<xsl:param name="chunk.section.depth" select="0"/>
	<xsl:param name="chunk.sections" select="0"/>
	<xsl:param name="css.decoration" select="1"/>
	<xsl:param name="docbook.css.source"/>
	<!--xsl:param name="ebnf.table.bgcolor">none</xsl:param-->
	<xsl:param name="ebnf.table.border" select="0"/>
	<xsl:param name="footer.rule" select="0"/>
	<xsl:param name="generate.manifest" select="0"/>
	<xsl:param name="graphicsize.extension" select="0"/>
	<xsl:param name="graphicsize.use.img.src.path" select="1"/>
	<xsl:param name="header.rule" select="0"/>
	<xsl:param name="highlight.source" select="1"/>
	<xsl:param name="html.ext">.xhtml</xsl:param>
	<xsl:param name="html.extra.head.links" select="1"/>
	<xsl:param name="index.method">kosek</xsl:param>
	<xsl:param name="index.on.type" select="1"/>
	<xsl:param name="keep.relative.image.uris" select="1"/>
	<xsl:param name="local.l10n.xml" select="document('l10n.xml')"/>
	<xsl:param name="make.clean.html" select="1"/>
	<xsl:param name="make.graphic.viewport" select="1"/>
	<xsl:param name="make.year.ranges" select="1"/>
	<xsl:param name="navig.graphics" select="1"/>
	<xsl:param name="navig.graphics.extension">.svg</xsl:param>
	<xsl:param name="navig.graphics.path">navigation/</xsl:param>
	<xsl:param name="profile.lang"/>
	<xsl:param name="section.autolabel" select="1"/>
	<xsl:param name="section.label.includes.component.label" select="1"/>
	<xsl:param name="shortcut.icon"/>
	<!--xsl:param name="table.borders.with.css" select="0"/-->
	<xsl:param name="textinsert.extension" select="1"/>
	<xsl:param name="use.extensions" select="1"/>
	<xsl:param name="use.id.as.filename" select="1"/>
	<xsl:param name="use.svg" select="1"/>
	<xsl:param name="variablelist.term.break.after" select="1"/>
	<xsl:param name="variablelist.term.separator"/>

	<xsl:output method="xml" omit-xml-declaration="yes"/>

	<xsl:template name="root.attributes">
		<xsl:attribute name="xml:lang">
			<xsl:value-of select="ancestor-or-self::d:*/@xml:lang"/>
		</xsl:attribute>
	</xsl:template>

	<xsl:template name="user.head.content">
		<meta name="viewport" content="width=device-width, initial-scale=1"/>
		<link rel="shortcut icon" href="{$shortcut.icon}" type="image/svg+xml"/>
	</xsl:template>

	<!-- https://stackoverflow.com/questions/34466480/how-do-you-get-docbook-xsls-chunking-to-generate-a-full-toc-for-each-page -->
	<xsl:template name="make.toc">
		<xsl:param name="toc-context" select="."/>
		<xsl:param name="toc.title.p" select="true()"/>
		<xsl:param name="nodes" select="/NOT-AN-ELEMENT"/>
		<xsl:variable name="root-nodes" select="/"/>

		<xsl:variable name="nodes.plus" select="$root-nodes | d:qandaset"/>
		<xsl:variable name="toc.title">
			<xsl:if test="$toc.title.p">
				<xsl:choose>
					<xsl:when test="$make.clean.html != 0">
						<div class="toc-title">
							<xsl:call-template name="gentext">
								<xsl:with-param name="key">TableofContents</xsl:with-param>
							</xsl:call-template>
						</div>
					</xsl:when>
					<xsl:otherwise>
						<p>
							<strong>
								<xsl:call-template name="gentext">
									<xsl:with-param name="key">TableofContents</xsl:with-param>
								</xsl:call-template>
							</strong>
						</p>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="$manual.toc != ''">
				<xsl:variable name="id">
					<xsl:call-template name="object.id"/>
				</xsl:variable>
				<xsl:variable name="toc" select="document($manual.toc, .)"/>
				<xsl:variable name="tocentry" select="$toc//d:tocentry[@linkend=$id]"/>
				<xsl:if test="$tocentry and $tocentry/*">
					<div class="toc">
						<xsl:copy-of select="$toc.title"/>
						<xsl:element name="{$toc.list.type}" namespace="http://www.w3.org/1999/xhtml">
							<xsl:call-template name="toc.list.attributes">
								<xsl:with-param name="toc-context" select="$toc-context"/>
								<xsl:with-param name="toc.title.p" select="$toc.title.p"/>
								<xsl:with-param name="nodes" select="$root-nodes"/>
							</xsl:call-template>
							<xsl:call-template name="manual-toc">
								<xsl:with-param name="tocentry" select="$tocentry/*[1]"/>
							</xsl:call-template>
						</xsl:element>
					</div>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="$qanda.in.toc != 0">
						<xsl:if test="$nodes.plus">
							<div class="toc">
								<xsl:copy-of select="$toc.title"/>
								<xsl:element name="{$toc.list.type}" namespace="http://www.w3.org/1999/xhtml">
									<xsl:call-template name="toc.list.attributes">
										<xsl:with-param name="toc-context" select="$toc-context"/>
										<xsl:with-param name="toc.title.p" select="$toc.title.p"/>
										<xsl:with-param name="nodes" select="$root-nodes"/>
									</xsl:call-template>
									<xsl:apply-templates select="$nodes.plus" mode="toc">
										<xsl:with-param name="toc-context" select="$toc-context"/>
									</xsl:apply-templates>
								</xsl:element>
							</div>
						</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						<xsl:if test="$root-nodes">
							<div class="toc">
								<xsl:copy-of select="$toc.title"/>
								<xsl:element name="{$toc.list.type}" namespace="http://www.w3.org/1999/xhtml">
									<xsl:call-template name="toc.list.attributes">
										<xsl:with-param name="toc-context" select="$toc-context"/>
										<xsl:with-param name="toc.title.p" select="$toc.title.p"/>
										<xsl:with-param name="nodes" select="$root-nodes"/>
									</xsl:call-template>
									<xsl:apply-templates select="$root-nodes" mode="toc">
										<xsl:with-param name="toc-context" select="$toc-context"/>
									</xsl:apply-templates>
								</xsl:element>
							</div>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="user.footer.navigation">
	</xsl:template>

	<xsl:template name="is.graphic.format">
		<xsl:param name="format"/>
		<xsl:if test="$format='SVG'
				or $format='PNG'
				or $format='JPG'
				or $format='JPEG'
				or $format='linespecific'
				or $format='GIF'
				or $format='GIF87a'
				or $format='GIF89a'
				or $format='BMP'
				or $format='HTML'
				or $format='XHTML'">1</xsl:if>
	</xsl:template>

	<xsl:template match="d:code">
		<code class="{name(.)}">
			<xsl:choose>
				<xsl:when test="$highlight.source!=0">
					<xsl:call-template name="apply-highlighting"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates/>
				</xsl:otherwise>
			</xsl:choose>
		</code>
	</xsl:template>

	<xsl:template match="d:function">
		<code class="token">
			<span class="hl-keyword">
				<xsl:apply-templates/>
			</span>
		</code>
	</xsl:template>

</xsl:stylesheet>
