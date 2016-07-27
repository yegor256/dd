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

import com.jcabi.matchers.XhtmlMatchers;
import com.seedramp.dd.core.Pitches;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.xembly.Xembler;

/**
 * Integration case for {@link DyPitches}.
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class DyPitchesITCase {

    /**
     * DyAuthor can post and list.
     * @throws Exception If some problem inside
     */
    @Test
    public void postsAndLists() throws Exception {
        final Pitches pitches = new DyAuthor(new Dynamo(), "judy").pitches();
        pitches.submit("hello", "the body");
        MatcherAssert.assertThat(
            new Xembler(pitches.inXembly()).xml(),
            XhtmlMatchers.hasXPaths(
                "/pitches[count(pitch)>0]",
                "/pitches/pitch[id]",
                "/pitches/pitch[title]",
                "/pitches/pitch[text]",
                "/pitches/pitch[comments]",
                "/pitches/pitch[author]"
            )
        );
    }

}
