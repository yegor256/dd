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
import java.io.IOException;
import org.takes.Request;

/**
 * Comment in the request.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
final class RqComment implements Comment {

    /**
     * The comments.
     */
    private final transient Comments comments;

    /**
     * The request.
     */
    private final transient Request request;

    /**
     * Ctor.
     * @param cmt Comments
     * @param req Request
     */
    RqComment(final Comments cmt, final Request req) {
        this.comments = cmt;
        this.request = req;
    }

    @Override
    public void delete() throws IOException {
        this.comment().delete();
    }

    /**
     * Get comment.
     * @return The comment
     * @throws IOException If fails
     */
    private Comment comment() throws IOException {
        return this.comments.comment(
            new Path(this.request).comment()
        );
    }
}
