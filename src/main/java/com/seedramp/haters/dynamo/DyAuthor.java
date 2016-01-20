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

import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.jcabi.dynamo.Attributes;
import com.jcabi.dynamo.Item;
import com.jcabi.dynamo.QueryValve;
import com.jcabi.dynamo.Region;
import com.jcabi.dynamo.Table;
import com.seedramp.haters.model.Author;
import java.io.IOException;
import java.util.Iterator;

/**
 * Dynamo Author.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class DyAuthor implements Author {

    /**
     * The region to work with.
     */
    private final transient Region region;

    /**
     * The name of him.
     */
    private final transient String handle;

    /**
     * Ctor.
     * @param reg Region
     * @param name Name of him
     */
    public DyAuthor(final Region reg, final String name) {
        this.region = reg;
        this.handle = name;
    }

    @Override
    public long points() throws IOException {
        return Long.parseLong(
            this.item().get("points").getN()
        );
    }

    @Override
    public void add(final long points) throws IOException {
        this.item().put(
            "points",
            new AttributeValueUpdate()
                .withAction(AttributeAction.ADD)
                .withValue(
                    new AttributeValue()
                        .withN(Long.toString(points))
                )
        );
    }

    /**
     * My item.
     * @return Item
     * @throws IOException If fails
     */
    private Item item() throws IOException {
        final Iterator<Item> items = this.table()
            .frame()
            .through(new QueryValve().withLimit(1))
            .where("name", this.handle)
            .iterator();
        final Item item;
        if (items.hasNext()) {
            item = items.next();
        } else {
            item = this.table().put(
                new Attributes()
                    .with("name", this.handle)
                    .with("points", 0)
            );
        }
        return item;
    }

    /**
     * Table to work with.
     * @return Table
     */
    private Table table() {
        return this.region.table("authors");
    }

}
