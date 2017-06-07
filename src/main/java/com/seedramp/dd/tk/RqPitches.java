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

import com.seedramp.dd.core.Author;
import com.seedramp.dd.core.Base;
import com.seedramp.dd.core.Pitch;
import com.seedramp.dd.core.Pitches;
import java.io.IOException;
import org.takes.Request;
import org.xembly.Directive;

/**
 * Pitches from HTTP request.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class RqPitches implements Pitches {

    /**
     * The author.
     */
    private final transient Author author;

    /**
     * Ctor.
     * @param base Base
     * @param req Request
     */
    public RqPitches(final Base base, final Request req) {
        this(new RqAuthor(base, req));
    }

    /**
     * Ctor.
     * @param atr Author
     */
    public RqPitches(final Author atr) {
        this.author = atr;
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
        return this.author.pitches();
    }

}
