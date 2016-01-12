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
import com.jcabi.dynamo.Item;
import com.jcabi.dynamo.Region;
import com.jcabi.dynamo.Table;
import com.seedramp.haters.model.Pitch;
import com.seedramp.haters.model.Pitches;

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
            this.table().frame(),
            new Function<Item, Pitch>() {
                @Override
                public Pitch apply(final Item item) {
                    return new DyPitch(item);
                }
            }
        );
    }

    @Override
    public void post(final String text, final String author) {
        throw new UnsupportedOperationException("#post()");
    }

    /**
     * Table to work with.
     * @return Table
     */
    private Table table() {
        return this.region.table("authors");
    }
}
