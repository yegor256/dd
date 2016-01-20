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
import java.io.IOException;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeTransform;
import org.takes.rs.xe.XeWrap;

/**
 * Pitches as a Xembly source.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class XePitches extends XeWrap {

    /**
     * Ctor.
     * @param pitches Pitches
     */
    public XePitches(final Iterable<Pitch> pitches) {
        super(
            new XeAppend(
                "pitches",
                new XeTransform<>(
                    pitches,
                    new XeTransform.Func<Pitch>() {
                        @Override
                        public XeSource transform(final Pitch pitch)
                            throws IOException {
                            return new XePitch(pitch);
                        }
                    }
                )
            )
        );
    }
}
