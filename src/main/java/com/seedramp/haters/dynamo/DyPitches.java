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
import com.seedramp.haters.model.Pitch;
import com.seedramp.haters.model.Pitches;
import java.io.IOException;

/**
 * Dynamo Pitches.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class DyPitches implements Pitches {

    /**
     * The region to work with.
     */
    private final transient Region region;

    /**
     * Ctor.
     * @param reg Region
     */
    public DyPitches(final Region reg) {
        this.region = reg;
    }

    @Override
    public Iterable<Pitch> home() {
        return Iterables.transform(
            this.table()
                .frame()
                .through(
                    new QueryValve()
                        .withLimit(Tv.TWENTY)
                        .withIndexName("home")
                        .withScanIndexForward(false)
                        .withConsistentRead(false)
                )
                .where("visible", Conditions.equalTo(1)),
            new Function<Item, Pitch>() {
                @Override
                public Pitch apply(final Item item) {
                    return new DyPitch(item);
                }
            }
        );
    }

    @Override
    public Iterable<Pitch> pending() {
        return Iterables.transform(
            this.table()
                .frame()
                .through(
                    new QueryValve()
                        .withLimit(Tv.TWENTY)
                        .withIndexName("home")
                        .withScanIndexForward(false)
                        .withConsistentRead(false)
                )
                .where("visible", Conditions.equalTo(0)),
            new Function<Item, Pitch>() {
                @Override
                public Pitch apply(final Item item) {
                    return new DyPitch(item);
                }
            }
        );
    }

    @Override
    public Pitch pitch(final long num) {
        return new DyPitch(
            this.table()
                .frame()
                .through(new QueryValve().withLimit(1))
                .where("id", Conditions.equalTo(num))
                .iterator()
                .next()
        );
    }

    @Override
    public Pitch post(final String text, final String author)
        throws IOException {
        final Item item = this.table().put(
            new Attributes()
                .with("id", System.currentTimeMillis())
                .with("text", text)
                .with("author", author)
                .with("points", 0)
                .with("votes", 0)
                .with("visible", 0)
                .with("date", System.currentTimeMillis())
                .with("rank", 0)
        );
        return new DyPitch(item);
    }

    /**
     * Table to work with.
     * @return Table
     */
    private Table table() {
        return this.region.table("pitches");
    }
}
