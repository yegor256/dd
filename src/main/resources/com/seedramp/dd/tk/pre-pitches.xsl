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
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    version="2.0" exclude-result-prefixes="xs">
    <xsl:output method="xml"/>
    <xsl:template match="pitch">
        <xsl:copy>
            <xsl:if test="@mature = 'false'">
                <xsl:attribute name="open">
                    <xsl:text>true</xsl:text>
                </xsl:attribute>
            </xsl:if>
            <xsl:apply-templates select="node() except (id|created)"/>
            <links>
                <link rel="see" href="/p/{id}"/>
                <xsl:if test="@mine = 'true' and @mature = 'false'">
                    <link rel="delete" href="/p/{id}/delete"/>
                </xsl:if>
            </links>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="/">
        <xsl:copy>
            <xsl:processing-instruction name="xml-stylesheet">
                <xsl:text>href='/xsl/pitches.xsl' type='text/xsl'</xsl:text>
            </xsl:processing-instruction>
            <xsl:apply-templates select="node()"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="//processing-instruction()" />
    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
