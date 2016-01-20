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

import java.io.IOException;
import java.net.HttpURLConnection;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;
import org.takes.facets.fork.Fork;
import org.takes.facets.fork.RqRegex;
import org.takes.facets.fork.TkRegex;
import org.takes.facets.forward.RsForward;
import org.takes.misc.Opt;
import org.takes.rq.RqHref;
import org.takes.rq.RqWithHeader;

/**
 * Fork of pitch.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
final class FkPitch extends FkWrap {

    /**
     * Ctor.
     * @param regex Regular expression
     * @param take Take
     */
    FkPitch(final String regex, final Take take) {
        super(
            new Fork() {
                @Override
                public Opt<Response> route(final Request req)
                    throws IOException {
                    return FkPitch.route(regex, take, req);
                }
            }
        );
    }

    /**
     * Route.
     * @param regex Regular expression
     * @param take Take
     * @param req Request
     * @return Response or empty
     * @throws IOException If fails
     */
    private static Opt<Response> route(final String regex, final Take take,
        final Request req) throws IOException {
        return new FkRegex(
            String.format("/p/([0-9]+)%s", regex),
            new TkRegex() {
                @Override
                public Response act(final RqRegex rreq) throws IOException {
                    final long num = Long.parseLong(rreq.matcher().group(1));
                    return FkPitch.redirect(num, take).act(
                        new RqWithHeader(
                            rreq, "X-Haters-Pitch",
                            Long.toString(num)
                        )
                    );
                }
            }
        ).route(req);
    }

    /**
     * Redirect to the bout.
     * @param num Pitch number
     * @param take Take
     * @return New take
     */
    private static Take redirect(final long num, final Take take) {
        return new Take() {
            @Override
            public Response act(final Request req) throws IOException {
                try {
                    return take.act(req);
                } catch (final RsForward ex) {
                    if (ex.code() == HttpURLConnection.HTTP_SEE_OTHER) {
                        throw new RsForward(
                            ex,
                            new RqHref.Smart(
                                new RqHref.Base(req)
                            ).home().path("p").path(num)
                        );
                    }
                    throw ex;
                }
            }
        };
    }

}
