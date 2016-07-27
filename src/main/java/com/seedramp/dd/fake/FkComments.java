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
package com.seedramp.dd.fake;

import com.seedramp.dd.core.Comment;
import com.seedramp.dd.core.Comments;
import java.io.IOException;
import org.xembly.Directive;
import org.xembly.Directives;

/**
 * Fake Comments.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class FkComments implements Comments {

    @Override
    public Comment comment(final long num) throws IOException {
        return new FkComment();
    }

    @Override
    public void post(final String text) throws IOException {
        // nothing
    }

    @Override
    public Iterable<Directive> inXembly() throws IOException {
        return new Directives()
            .add("comments")
            .add("comment")
            .attr("mature", "true")
            .add("id").set("55").up()
            .add("text").set("I love this startup").up()
            .add("author").set("yegor256").up()
            .add("created").set("2016-07-19T19:19:27Z");
    }

}
