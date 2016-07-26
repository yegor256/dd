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

import com.seedramp.haters.core.Base;
import com.seedramp.haters.core.Pitch;
import com.seedramp.haters.core.Pitches;
import java.io.IOException;
import org.takes.Request;
import org.xembly.Directive;

/**
 * Pitches from HTTP request.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class RqPitches implements Pitches {

    /**
     * The base.
     */
    private final transient Base base;

    /**
     * The request.
     */
    private final transient Request request;

    /**
     * Ctor.
     * @param bse Base
     * @param req Request
     */
    public RqPitches(final Base bse, final Request req) {
        this.base = bse;
        this.request = req;
    }

    @Override
    public Pitch pitch(final long num) throws IOException {
        return this.pitches().pitch(num);
    }

    @Override
    public void submit(final String title, final String text)
        throws IOException {
        this.pitches().submit(title, text);
    }

    @Override
    public Iterable<Directive> inXembly() throws IOException {
        return this.pitches().inXembly();
    }

    /**
     * Get pitches.
     * @return Pitches
     * @throws IOException If fails
     */
    private Pitches pitches() throws IOException {
        return new RqAuthor(this.base, this.request).pitches();
    }

}
