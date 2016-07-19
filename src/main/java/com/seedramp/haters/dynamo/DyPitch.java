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
package com.seedramp.haters.dynamo;

import com.google.common.collect.Iterables;
import com.jcabi.aspects.Tv;
import com.jcabi.dynamo.Attributes;
import com.jcabi.dynamo.Conditions;
import com.jcabi.dynamo.QueryValve;
import com.jcabi.dynamo.Region;
import com.jcabi.dynamo.Table;
import com.seedramp.haters.core.Comment;
import com.seedramp.haters.core.Pitch;
import java.io.IOException;

/**
 * Dynamo Pitch.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
final class DyPitch implements Pitch {

    /**
     * The region to work with.
     */
    private final transient Region region;

    /**
     * The author.
     */
    private final transient String author;

    /**
     * The number of the pitch.
     */
    private final transient long number;

    /**
     * Ctor.
     * @param reg Region
     * @param user Who is the user
     * @param num Its number
     */
    DyPitch(final Region reg, final String user, final long num) {
        this.region = reg;
        this.author = user;
        this.number = num;
    }

    @Override
    public void delete() throws IOException {
        this.table().delete(
            new Attributes().with("id", this.number)
        );
    }

    @Override
    public Iterable<Comment> recent() {
        return Iterables.transform(
            this.table()
                .frame()
                .through(
                    new QueryValve()
                        .withLimit(Tv.TWENTY)
                        .withIndexName("recent")
                        .withScanIndexForward(true)
                        .withConsistentRead(false)
                )
                .where("pitch", Conditions.equalTo(this.number)),
            item -> {
                try {
                    return this.comment(
                        Long.parseLong(item.get("id").getN())
                    );
                } catch (final IOException ex) {
                    throw new IllegalStateException(ex);
                }
            }
        );
    }

    @Override
    public Comment comment(final long num) {
        return new DyComment(this.region, this.author, num);
    }

    @Override
    public void post(final String text) throws IOException {
        this.table().put(
            new Attributes()
                .with("id", System.currentTimeMillis())
                .with("pitch", this.number)
                .with("created", System.currentTimeMillis())
                .with("text", text)
                .with("author", this.author)
        );
    }

    /**
     * Table to work with.
     * @return Table
     */
    private Table table() {
        return this.region.table("pitches");
    }

}
