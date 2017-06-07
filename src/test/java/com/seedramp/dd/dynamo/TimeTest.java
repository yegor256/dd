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

import com.jcabi.aspects.Tv;
import java.util.concurrent.TimeUnit;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Time}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TimeTest {

    /**
     * Time can print ISO 8601.
     * @throws Exception If some problem inside
     */
    @Test
    public void printsToIso() throws Exception {
        final Time time = new Time(System.currentTimeMillis());
        MatcherAssert.assertThat(
            time.iso(),
            Matchers.containsString("T")
        );
    }

    /**
     * Time can check maturity.
     * @throws Exception If some problem inside
     */
    @Test
    public void checksMaturity() throws Exception {
        MatcherAssert.assertThat(
            new Time(System.currentTimeMillis()).isMature(),
            Matchers.is(false)
        );
        MatcherAssert.assertThat(
            new Time(
                System.currentTimeMillis()
                - TimeUnit.DAYS.toMillis((long) Tv.TEN)
            ).isMature(),
            Matchers.is(true)
        );
    }

}
