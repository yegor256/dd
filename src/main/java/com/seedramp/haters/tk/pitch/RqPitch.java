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

import com.seedramp.haters.core.Base;
import com.seedramp.haters.core.Pitch;
import java.io.IOException;
import org.takes.Request;
import org.takes.rq.RqHeaders;
import org.takes.rq.RqWrap;

/**
 * Index of the pitch.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class RqPitch extends RqWrap {

    /**
     * Base.
     */
    private final transient Base base;

    /**
     * Ctor.
     * @param bse The base
     * @param req Request
     */
    public RqPitch(final Base bse, final Request req) {
        super(req);
        this.base = bse;
    }

    /**
     * Get deck.
     * @return The deck
     * @throws IOException If fails
     */
    public Pitch pitch() throws IOException {
        return this.base.pitches().pitch(
            Long.parseLong(
                new RqHeaders.Smart(
                    new RqHeaders.Base(this)
                ).single("X-Haters-Pitch")
            )
        );
    }

}
