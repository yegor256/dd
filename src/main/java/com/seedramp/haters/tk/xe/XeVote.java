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
package com.seedramp.haters.tk.xe;

import com.seedramp.haters.model.Pitch;
import com.seedramp.haters.model.Vote;
import java.io.IOException;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeLink;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

/**
 * Vote as a Xembly source.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class XeVote extends XeWrap {

    /**
     * Ctor.
     * @param vote Vote
     * @throws IOException If fails
     */
    public XeVote(final Pitch pitch, final Vote vote) throws IOException {
        super(
            new XeAppend(
                "vote",
                new XeChain(
                    new XeDirectives(
                        new Directives()
                            .add("author").set(vote.author()).up()
                            .add("text").set(vote.text()).up()
                            .add("points").set(vote.points()).up()
                            .add("positive").set(vote.positive()).up()
                    ),
                    new XeLink(
                        "vote",
                        String.format(
                            "/p/%d/vote/%s",
                            pitch.id(), vote.author()
                        )
                    )
                )
            )
        );
    }
}
