<?xml version="1.0"?>
<!--
 * Copyright (c) 2016, Yegor Bugayenko
 * All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns="http://www.w3.org/1999/xhtml" version="1.0">
    <xsl:output method="html" doctype-system="about:legacy-compat"
        encoding="UTF-8" indent="yes" />
    <xsl:include href="/xsl/layout.xsl"/>
    <xsl:template match="page" mode="head">
        <title>
            <xsl:text>pitches</xsl:text>
        </title>
        <link rel="stylesheet" href="/css/pitches.css?{version/revision}"/>
    </xsl:template>
    <xsl:template match="page" mode="body">
        <xsl:apply-templates select="pitches"/>
    </xsl:template>
    <xsl:template match="pitches">
        <xsl:apply-templates select="pitch"/>
    </xsl:template>
    <xsl:template match="pitch">
        <p>
            <xsl:if test="not(@open) or @open != 'true'">
                <xsl:value-of select="date"/>
                <xsl:text>: </xsl:text>
            </xsl:if>
            <xsl:choose>
                <xsl:when test="links/link[@rel='see']">
                    <a href="{links/link[@rel='see']/@href}">
                        <xsl:value-of select="title"/>
                    </a>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="title"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="comments &gt; 0">
                <xsl:text> /</xsl:text>
                <xsl:value-of select="comments"/>
            </xsl:if>
            <xsl:if test="not(@open) or @open != 'true'">
                <xsl:text> [closed]</xsl:text>
            </xsl:if>
            <xsl:if test="links/link[@rel='delete']">
                <xsl:text> </xsl:text>
                <a href="{links/link[@rel='delete']/@href}">
                    <xsl:text>delete</xsl:text>
                </a>
            </xsl:if>
        </p>
    </xsl:template>
</xsl:stylesheet>
