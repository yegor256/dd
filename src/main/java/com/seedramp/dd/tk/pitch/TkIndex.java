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
package com.seedramp.dd.tk.pitch;

import com.seedramp.dd.core.Base;
import com.seedramp.dd.tk.RsHtmlPage;
import com.seedramp.dd.tk.RsPage;
import com.seedramp.dd.tx.TxComments;
import com.seedramp.dd.tx.TxPitch;
import java.io.IOException;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsXslt;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeLink;

/**
 * Index of the pitch.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
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
        return new RsHtmlPage(
            req,
            new RsXslt(
                new RsPage(
                    "/com/seedramp/dd/tk/pitch/pre-pitch.xsl",
                    req,
                    new XeLink(
                        "post",
                        String.format(
                            "/p/%d/post",
                            new PitchNumber(req).longValue()
                        )
                    ),
                    new XeDirectives(
                        new TxPitch(
                            new RqPitch(this.base, req)
                        )
                    ),
                    new XeDirectives(
                        new TxComments(
                            new RqComments(this.base, req)
                        )
                    )
                )
            )
        );
    }

}
