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
package com.seedramp.haters.tk.pitch;

import com.seedramp.haters.model.Base;
import com.seedramp.haters.tk.RsPage;
import java.io.IOException;
import java.util.Arrays;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeLink;
import org.takes.rs.xe.XeSource;
import org.xembly.Directives;

/**
 * Index of the pitch.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
final class TkIndex implements Take {

    /**
     * Base.
     */
    private final transient Base base;

    /**
     * Ctor.
     * @param bse Base
     */
    TkIndex(final Base bse) {
        this.base = bse;
    }

    @Override
    public Response act(final Request req) throws IOException {
        return new RsPage(
            this.base,
            "/xsl/pitch.xsl",
            req,
            new XeAppend(
                "pitch",
                new XeDirectives(
                    new Directives()
                        .add("title").set("rultor, a DevOps chat bot").up()
                        .add("text").set("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                        "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                        "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                        "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.").up()
                        .add("author").set("yegor256").up()
                        .add("alive").set("true").up()
                        .add("age").set("14 hours").up()
                )
            ),
            new XeAppend(
                "comments",
                new XeChain(
                    Arrays.<XeSource>asList(
                        new XeAppend(
                            "comment",
                            new XeChain(
                                new XeLink("vote", "/p/123/c/5/vote"),
                                new XeDirectives(
                                    new Directives()
                                        .add("text").set("I love it").up()
                                        .add("author").set("yegor256").up()
                                )
                            )
                        ),
                        new XeAppend(
                            "comment",
                            new XeChain(
                                new XeLink("vote", "/p/123/c/6/vote"),
                                new XeDirectives(
                                    new Directives()
                                        .add("text").set("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                                        "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                                        "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                                        "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.").up()
                                        .add("author").set("jeff").up()
                                )
                            )
                        )
                    )
                )
            )
        );
    }

}
