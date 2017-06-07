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
import com.seedramp.dd.core.Author;
import com.seedramp.dd.core.Pitch;
import com.seedramp.dd.core.Pitches;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.xembly.Xembler;

/**
 * Integration case for {@link DyPitch}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class DyPitchITCase {

    /**
     * DyPitch can delete itself.
     * @throws Exception If some problem inside
     */
    @Test
    public void deletesItself() throws Exception {
        final Author author = new DyAuthor(new Dynamo(), "jeff");
        author.pitches().submit("the title", "the body");
        final Pitch pitch =
            new Pitches.AsArray(author.pitches()).iterator().next();
        pitch.delete();
    }

    /**
     * DyPitch can count comments.
     * @throws Exception If some problem inside
     */
    @Test
    public void countsComments() throws Exception {
        final Author author = new DyAuthor(new Dynamo(), "peter");
        author.pitches().submit("the title 1", "the body 2");
        final Pitch pitch =
            new Pitches.AsArray(author.pitches()).iterator().next();
        pitch.comments().post("the comment");
        MatcherAssert.assertThat(
            new Xembler(pitch.inXembly()).xml(),
            XhtmlMatchers.hasXPaths("/pitch[comments=1]")
        );
    }

}
