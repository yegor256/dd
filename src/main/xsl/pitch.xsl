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
            <xsl:value-of select="pitch/title"/>
        </title>
        <link rel="stylesheet" href="/css/pitch.css?{version/revision}"/>
    </xsl:template>
    <xsl:template match="page" mode="body">
        <xsl:apply-templates select="pitch"/>
        <form action="{links/link[@rel='post']/@href}" method="post">
            <label>What do you think about it?</label>
            <textarea name="text" style="width:100%;height:5em;">
                <xsl:text> </xsl:text>
            </textarea>
            <button type="submit">Submit</button>
        </form>
        <xsl:apply-templates select="comments"/>
    </xsl:template>
    <xsl:template match="pitch">
        <p>
            <strong><xsl:value-of select="title"/></strong>
        </p>
        <p>
            <xsl:value-of select="text"/>
        </p>
    </xsl:template>
    <xsl:template match="comments">
        <xsl:apply-templates select="comment"/>
    </xsl:template>
    <xsl:template match="comment">
        <p>
            <xsl:text>@</xsl:text>
            <xsl:value-of select="author"/>
            <xsl:text>: </xsl:text>
            <xsl:value-of select="text"/>
        </p>
    </xsl:template>
</xsl:stylesheet>
