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

import com.jcabi.dynamo.Attributes;
import com.jcabi.dynamo.Item;
import com.jcabi.dynamo.Region;
import com.seedramp.haters.core.Comments;
import com.seedramp.haters.core.Pitch;
import java.io.IOException;
import org.xembly.Directive;

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
    public Comments comments() {
        return new DyComments(this.region, this.author, this.number);
    }

    @Override
    public void delete() throws IOException {
        final Item item = new TblPitch(
            this.region, this.author, this.number
        ).item();
        final long comments = Long.parseLong(item.get("comments").getN());
        if (comments != 0L) {
            throw new IOException(
                String.format(
                    "pitch can't be deleted, since there are %d comments",
                    comments
                )
            );
        }
        this.region.table("pitches").delete(
            new Attributes().with("id", this.number)
        );
    }

    @Override
    public Iterable<Directive> inXembly() throws IOException {
        return new ItmPitch(
            new TblPitch(this.region, this.author, this.number).item(),
            this.author
        ).inXembly();
    }

}
