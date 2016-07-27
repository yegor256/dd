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

import com.jcabi.dynamo.Item;
import java.io.IOException;
import org.xembly.Directive;
import org.xembly.Directives;

/**
 * Pitch item (in DynamoDB).
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class ItmPitch {

    /**
     * The item.
     */
    private final transient Item item;

    /**
     * Who is looking at it.
     */
    private final transient String user;

    /**
     * Ctor.
     * @param itm Item
     * @param usr User
     */
    ItmPitch(final Item itm, final String usr) {
        this.item = itm;
        this.user = usr;
    }

    /**
     * Turn it to Xembly.
     * @return Xembly
     * @throws IOException If fails
     */
    public Iterable<Directive> inXembly() throws IOException {
        final String author = this.item.get("author").getS();
        final Time created = new Time(this.item.get("created"));
        return new Directives()
            .add("pitch")
            .attr("mature", created.isMature())
            .attr("mine", author.equals(this.user))
            .attr("valid", "1".equals(this.item.get("valid").getN()))
            .add("id").set(this.item.get("id").getN()).up()
            .add("title").set(this.item.get("title").getS()).up()
            .add("text").set(this.item.get("text").getS()).up()
            .add("comments").set(this.item.get("comments").getN()).up()
            .add("author").set(author).up()
            .add("created").set(created.iso()).up()
            .up();
    }

}
