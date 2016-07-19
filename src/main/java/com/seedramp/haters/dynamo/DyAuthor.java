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
import com.seedramp.haters.core.Author;
import com.seedramp.haters.core.Pitch;
import java.io.IOException;

/**
 * Dynamo Author.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
final class DyAuthor implements Author {

    /**
     * The region to work with.
     */
    private final transient Region region;

    /**
     * The name.
     */
    private final transient String name;

    /**
     * Ctor.
     * @param reg Region
     * @param author Author name
     */
    DyAuthor(final Region reg, final String author) {
        this.region = reg;
        this.name = author;
    }

    @Override
    public Iterable<Pitch> recent() {
        return Iterables.transform(
            this.table()
                .frame()
                .through(
                    new QueryValve()
                        .withLimit(Tv.TWENTY)
                        .withIndexName("recent")
                        .withScanIndexForward(false)
                        .withConsistentRead(false)
                )
                .where("alive", Conditions.equalTo(1)),
            item -> {
                try {
                    return this.pitch(
                        Long.parseLong(item.get("id").getN())
                    );
                } catch (final IOException ex) {
                    throw new IllegalStateException(ex);
                }
            }
        );
    }

    @Override
    public Pitch pitch(final long num) {
        return new DyPitch(this.region, this.name, num);
    }

    @Override
    public void submit(final String title, final String text)
        throws IOException {
        this.table().put(
            new Attributes()
                .with("id", System.currentTimeMillis())
                .with("title", title)
                .with("text", text)
                .with("author", this.name)
                .with("alive", 1)
                .with("created", System.currentTimeMillis())
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
