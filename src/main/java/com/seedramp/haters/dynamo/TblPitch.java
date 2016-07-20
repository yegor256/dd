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
import com.jcabi.dynamo.AttributeUpdates;
import com.jcabi.dynamo.Conditions;
import com.jcabi.dynamo.Item;
import com.jcabi.dynamo.QueryValve;
import com.jcabi.dynamo.Region;
import java.io.IOException;
import java.util.Iterator;

/**
 * Pitch in Dynamo table.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class TblPitch {

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
    TblPitch(final Region reg, final String user, final long num) {
        this.region = reg;
        this.author = user;
        this.number = num;
    }

    /**
     * Get it as an item.
     * @return The item
     * @throws IOException If fails
     */
    public Item item() throws IOException {
        assert this.author != null;
        final Iterator<Item> items = this.region.table("pitches")
            .frame()
            .through(
                new QueryValve().withLimit(1).withAttributesToGet(
                    "id", "title", "text", "author",
                    "created", "valid", "comments"
                )
            )
            .where("id", Conditions.equalTo(this.number))
            .iterator();
        if (!items.hasNext()) {
            throw new IOException(
                String.format("pitch #%d doesn't exist", this.number)
            );
        }
        return items.next();
    }

    /**
     * Increment comments counter.
     * @param inc How many to add
     * @throws IOException If fails
     */
    public void inc(final long inc) throws IOException {
        this.item().put(
            new AttributeUpdates().with(
                "comments",
                new AttributeValueUpdate()
                    .withAction(AttributeAction.ADD)
                    .withValue(new AttributeValue().withN(Long.toString(inc)))
            )
        );
    }

}
