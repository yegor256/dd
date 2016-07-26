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

import com.seedramp.haters.core.Comment;
import com.seedramp.haters.core.Comments;
import com.seedramp.haters.core.Pitch;
import java.io.IOException;
import org.xembly.Directive;

/**
 * Comments in the request.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
final class RqComments implements Comments {

    /**
     * The pitch.
     */
    private final transient Pitch pitch;

    /**
     * Ctor.
     * @param pth Pitch
     */
    RqComments(final Pitch pth) {
        this.pitch = pth;
    }

    @Override
    public Comment comment(final long num) throws IOException {
        return this.comments().comment(num);
    }

    @Override
    public void post(final String text) throws IOException {
        this.comments().post(text);
    }

    @Override
    public Iterable<Directive> inXembly() throws IOException {
        return this.comments().inXembly();
    }

    /**
     * Get comments.
     * @return The comments
     * @throws IOException If fails
     */
    private Comments comments() throws IOException {
        return this.pitch.comments();
    }
}
