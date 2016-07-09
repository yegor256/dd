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

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.jcabi.aspects.Tv;
import com.jcabi.dynamo.Attributes;
import com.jcabi.dynamo.Conditions;
import com.jcabi.dynamo.Item;
import com.jcabi.dynamo.QueryValve;
import com.jcabi.dynamo.Region;
import com.jcabi.dynamo.Table;
import com.seedramp.haters.model.Vote;
import com.seedramp.haters.model.Votes;
import java.io.IOException;

/**
 * Dynamo Votes.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class DyVotes implements Votes {

    /**
     * The region to work with.
     */
    private final transient Region region;

    /**
     * The pitch ID.
     */
    private final transient long pitch;

    /**
     * Ctor.
     * @param reg Region
     */
    public DyVotes(final Region reg, final long num) {
        this.region = reg;
        this.pitch = num;
    }

    @Override
    public Iterable<Vote> top() {
        return Iterables.transform(
            this.table()
                .frame()
                .through(
                    new QueryValve()
                        .withLimit(Tv.TWENTY)
                        .withIndexName("top")
                        .withScanIndexForward(false)
                        .withConsistentRead(false)
                )
                .where("pitch", Conditions.equalTo(this.pitch)),
            new Function<Item, Vote>() {
                @Override
                public Vote apply(final Item item) {
                    return new DyVote(item);
                }
            }
        );
    }

    @Override
    public void post(final String text, final String author)
        throws IOException {
        this.table().put(
            new Attributes()
                .with("pitch", this.pitch)
                .with("text", text)
                .with("author", author)
                .with("points", 0)
        );
    }

    @Override
    public long points() throws IOException {
        return 0L;
    }

    /**
     * Table to work with.
     * @return Table
     */
    private Table table() {
        return this.region.table("votes");
    }
}
