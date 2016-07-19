/**
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
 */
package com.seedramp.haters.tk;

import com.google.common.collect.Iterables;
import com.jcabi.manifests.Manifests;
import com.seedramp.haters.core.Base;
import com.seedramp.haters.tk.xe.XeAuthor;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import lombok.EqualsAndHashCode;
import org.takes.Request;
import org.takes.Response;
import org.takes.facets.auth.XeIdentity;
import org.takes.facets.auth.XeLogoutLink;
import org.takes.facets.auth.social.XeGithubLink;
import org.takes.facets.flash.XeFlash;
import org.takes.facets.fork.FkTypes;
import org.takes.facets.fork.RsFork;
import org.takes.rs.RsPrettyXML;
import org.takes.rs.RsWithType;
import org.takes.rs.RsWrap;
import org.takes.rs.RsXSLT;
import org.takes.rs.xe.RsXembly;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeDate;
import org.takes.rs.xe.XeLink;
import org.takes.rs.xe.XeLinkHome;
import org.takes.rs.xe.XeLinkSelf;
import org.takes.rs.xe.XeLocalhost;
import org.takes.rs.xe.XeMillis;
import org.takes.rs.xe.XeSLA;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeStylesheet;

/**
 * Index resource, front page of the website.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@EqualsAndHashCode(callSuper = true)
public final class RsPage extends RsWrap {

    /**
     * Ctor.
     * @param xsl XSL
     * @param req Request
     * @param src Source
     * @throws IOException If fails
     */
    public RsPage(final String xsl, final Request req, final XeSource... src)
        throws IOException {
        this(xsl, req, Arrays.asList(src));
    }

    /**
     * Ctor.
     * @param xsl XSL
     * @param req Request
     * @param src Source
     * @throws IOException If fails
     */
    public RsPage(final Base base, final String xsl,
        final Request req, final XeSource... src)
        throws IOException {
        this(
            xsl,
            req,
            Iterables.concat(
                Arrays.asList(src),
                RsPage.extra(base, req)
            )
        );
    }

    /**
     * Ctor.
     * @param xsl XSL
     * @param req Request
     * @param src Source
     * @throws IOException If fails
     */
    public RsPage(final String xsl, final Request req,
        final Iterable<XeSource> src)
        throws IOException {
        super(RsPage.make(xsl, req, src));
    }

    /**
     * Make it.
     * @param xsl XSL
     * @param req Request
     * @param src Source
     * @return Response
     * @throws IOException If fails
     */
    private static Response make(final String xsl, final Request req,
        final Iterable<XeSource> src) throws IOException {
        final Response raw = new RsXembly(
            new XeStylesheet(xsl),
            new XeAppend(
                "page",
                new XeMillis(false),
                new XeChain(src),
                new XeLinkHome(req),
                new XeLinkSelf(req),
                new XeLink("favicon", "/favicon.ico", "image/png"),
                new XeMillis(true),
                new XeDate(),
                new XeSLA(),
                new XeLocalhost(),
                new XeIdentity(req),
                new XeFlash(req),
                new XeGithubLink(req, Manifests.read("Haters-GithubId")),
                new XeLogoutLink(req),
                new XeLink("submit", "/submit"),
                new XeAppend(
                    "version",
                    new XeAppend("name", Manifests.read("Haters-Version")),
                    new XeAppend("revision", Manifests.read("Haters-Revision")),
                    new XeAppend("date", Manifests.read("Haters-Date"))
                )
            )
        );
        return new RsFork(
            req,
            new FkTypes(
                "application/xml,text/xml",
                new RsPrettyXML(new RsWithType(raw, "text/xml"))
            ),
            new FkTypes(
                "*/*",
                new RsXSLT(new RsWithType(raw, "text/html"))
            )
        );
    }

    /**
     * Ctor.
     * @param base Base
     * @param req Request
     * @throws IOException If fails
     */
    private static Iterable<XeSource> extra(final Base base,
        final Request req) throws IOException {
        final Collection<XeSource> extra = new LinkedList<>();
        final RqAuthor author = new RqAuthor(req);
        if (author.authorized()) {
            extra.addAll(
                Arrays.asList(
                    new XeLink("pending", "/pending"),
                    new XeAuthor(base.author(author.name()))
                )
            );
        }
        return extra;
    }

}
