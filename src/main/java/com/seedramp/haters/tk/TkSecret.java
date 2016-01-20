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

import com.seedramp.haters.model.Author;
import com.seedramp.haters.model.Base;
import java.io.IOException;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.forward.RsFailure;

/**
 * Secure take.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
final class TkSecret implements Take {

    /**
     * Base.
     */
    private final transient Base base;

    /**
     * Origin take.
     */
    private final transient Take origin;

    /**
     * Minimim points required.
     */
    private final transient long min;

    /**
     * Ctor.
     * @param bse Base
     */
    TkSecret(final Base bse, final Take take, final long threshold) {
        this.base = bse;
        this.origin = take;
        this.min = threshold;
    }

    @Override
    public Response act(final Request req) throws IOException {
        final Author author = this.base.author(new RqAuthor(req).name());
        if (author.points() < this.min) {
            throw new RsFailure(
                String.format(
                    "you have %d points, but need at least %d to access this",
                    author.points(), this.min
                )
            );
        }
        return this.origin.act(req);
    }

}
