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
package com.seedramp.dd.tk.pitch;

import com.jcabi.matchers.XhtmlMatchers;
import com.seedramp.dd.core.Base;
import com.seedramp.dd.fake.FkBase;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.takes.Take;
import org.takes.rq.RqFake;
import org.takes.rq.RqWithHeaders;
import org.takes.rs.RsPrint;

/**
 * Test case for {@link TkIndex}.
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class TkIndexTest {

    /**
     * TkIndex can render home page.
     * @throws Exception If some problem inside
     */
    @Test
    public void rendersHomePage() throws Exception {
        final Base base = new FkBase();
        final Take take = new TkIndex(base);
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(
                new RsPrint(
                    take.act(
                        new RqWithHeaders(
                            new RqFake("GET", "/"),
                            "Accept: text/xml",
                            "X-Haters-Pitch: 123"
                        )
                    )
                ).printBody()
            ),
            XhtmlMatchers.hasXPaths(
                "/page/millis",
                "/page/pitch[@open='true']",
                "/page/comments/comment"
            )
        );
    }

}
