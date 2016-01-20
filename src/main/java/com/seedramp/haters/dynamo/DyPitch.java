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
import com.jcabi.dynamo.Conditions;
import com.jcabi.dynamo.Item;
import com.jcabi.dynamo.QueryValve;
import com.seedramp.haters.model.Pitch;
import com.seedramp.haters.model.Votes;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * Dynamo Pitch.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class DyPitch implements Pitch {

    /**
     * The item.
     */
    private final transient Item item;

    /**
     * Ctor.
     * @param itm Item with pitch
     */
    public DyPitch(final Item itm) {
        this.item = itm;
    }

    @Override
    public Votes votes() throws IOException {
        return new DyVotes(this.item.frame().table().region(), this.id());
    }

    @Override
    public long id() throws IOException {
        return Long.parseLong(this.item.get("id").getN());
    }

    @Override
    public void approve(final String author) throws IOException {
        this.item.put(
            "visible",
            new AttributeValueUpdate()
                .withAction(AttributeAction.PUT)
                .withValue(new AttributeValue().withN("1"))
        );
    }

    @Override
    public void delete() throws IOException {
        final Iterator<Item> items = this.item.frame()
            .through(new QueryValve().withLimit(1))
            .where("id", Conditions.equalTo(this.id()))
            .iterator();
        items.next();
        items.remove();
    }

    @Override
    public String author() throws IOException {
        return this.item.get("author").getS();
    }

    @Override
    public String text() throws IOException {
        return this.item.get("text").getS();
    }

    @Override
    public Date date() throws IOException {
        return new Date(Long.parseLong(this.item.get("date").getN()));
    }

    @Override
    public long points() throws IOException {
        return Long.parseLong(this.item.get("points").getN());
    }

    @Override
    public long voted() throws IOException {
        return Long.parseLong(this.item.get("votes").getN());
    }
}
