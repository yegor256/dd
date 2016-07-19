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

import com.jcabi.aspects.Tv;
import com.seedramp.haters.core.Author;
import com.seedramp.haters.core.Base;
import com.seedramp.haters.core.Pitch;
import java.io.IOException;
import org.takes.Request;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;

/**
 * Author from HTTP request.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class RqAuthor implements Author {

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
    public RqAuthor(final Base bse, final Request req) {
        this.base = bse;
        this.request = req;
    }

    @Override
    public Iterable<Pitch> recent() throws IOException {
        return this.author().recent();
    }

    @Override
    public Pitch pitch(final long num) throws IOException {
        return this.author().pitch(num);
    }

    @Override
    public void submit(final String title, final String text)
        throws IOException {
        this.author().submit(title, text);
    }

    /**
     * Get author.
     * @return Author from the base
     * @throws IOException If fails
     */
    private Author author() throws IOException {
        final Identity identity = new RqAuth(this.request).identity();
        if (identity.equals(Identity.ANONYMOUS)) {
            throw new RsForward(new RsFlash("you must login first"));
        }
        return this.base.author(identity.urn().split(":", Tv.THREE)[2]);
    }

}
