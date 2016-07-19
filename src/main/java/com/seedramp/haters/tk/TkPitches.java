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

import com.seedramp.haters.model.Base;
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
 * List of all pitches.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
final class TkPitches implements Take {

    /**
     * Base.
     */
    private final transient Base base;

    /**
     * Ctor.
     * @param bse Base
     */
    TkPitches(final Base bse) {
        this.base = bse;
    }

    @Override
    public Response act(final Request req) throws IOException {
        return new RsPage(
            this.base,
            "/xsl/pitches.xsl",
            req,
            new XeAppend(
                "pitches",
                new XeChain(
                    Arrays.<XeSource>asList(
                        new XeAppend(
                            "pitch",
                            new XeChain(
                                new XeLink("see", "/p/123"),
                                new XeLink("delete", "/p/123"),
                                new XeDirectives(
                                    new Directives()
                                        .attr("alive", true)
                                        .add("title").set("rultor, a DevOps chat bot").up()
                                        .add("comments").set("3").up()
                                        .add("left").set("12 days").up()
                                )
                            )
                        ),
                        new XeAppend(
                            "pitch",
                            new XeChain(
                                new XeLink("see", "/p/124"),
                                new XeDirectives(
                                    new Directives()
                                        .add("title").set("jare.io, a free CDN").up()
                                        .add("comments").set("7").up()
                                        .add("date").set("5 May").up()
                                )
                            )
                        )
                    )
                )
            )
        );
    }

}
