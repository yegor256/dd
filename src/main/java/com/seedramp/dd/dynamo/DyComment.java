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
package com.seedramp.dd.dynamo;

import com.jcabi.dynamo.Attributes;
import com.jcabi.dynamo.Conditions;
import com.jcabi.dynamo.Item;
import com.jcabi.dynamo.QueryValve;
import com.jcabi.dynamo.Region;
import com.jcabi.dynamo.Table;
import com.seedramp.dd.core.Comment;
import java.io.IOException;
import java.util.Iterator;

/**
 * Dynamo Comment.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 1.0
 */
final class DyComment implements Comment {

    /**
     * The region to work with.
     */
    private final transient Region region;

    /**
     * The author.
     */
    private final transient String author;

    /**
     * The number of the comment.
     */
    private final transient long number;

    /**
     * Ctor.
     * @param reg Region
     * @param user Who is the user
     * @param num Its number
     */
    DyComment(final Region reg, final String user, final long num) {
        this.region = reg;
        this.author = user;
        this.number = num;
    }

    @Override
    public void delete() throws IOException {
        final Table table = this.region.table("comments");
        final Iterator<Item> items = table.frame()
            .through(new QueryValve().withLimit(1))
            .where("id", Conditions.equalTo(this.number))
            .iterator();
        if (!items.hasNext()) {
            throw new IOException(
                String.format(
                    "comment #%d doesn't exist",
                    this.number
                )
            );
        }
        final long pitch = Long.parseLong(items.next().get("pitch").getN());
        table.delete(new Attributes().with("id", this.number));
        new TblPitch(this.region, this.author, pitch).inc(-1L);
    }

}
