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

import com.jcabi.dynamo.Item;
import com.seedramp.haters.model.Pitch;
import com.seedramp.haters.model.Votes;
import java.io.IOException;
import java.util.Date;

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
    public Votes votes() {
        throw new UnsupportedOperationException("#votes()");
    }

    @Override
    public int id() throws IOException {
        return Integer.parseInt(this.item.get("id").getN());
    }

    @Override
    public void approve() {
        throw new UnsupportedOperationException("#approve()");
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
    public int points() throws IOException {
        return Integer.parseInt(this.item.get("points").getN());
    }

    @Override
    public int voted() throws IOException {
        return Integer.parseInt(this.item.get("votes").getN());
    }
}
