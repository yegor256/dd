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
package com.seedramp.dd.tk;

import com.seedramp.dd.core.Base;
import com.seedramp.dd.tx.TxPitches;
import java.io.IOException;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsXslt;
import org.takes.rs.xe.XeDirectives;

/**
 * List of all pitches.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
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
        return new RsHtmlPage(
            req,
            new RsXslt(
                new RsPage(
                    "/com/seedramp/dd/tk/pre-pitches.xsl",
                    req,
                    new XeDirectives(
                        new TxPitches(new RqPitches(this.base, req))
                    )
                )
            )
        );
    }

}
