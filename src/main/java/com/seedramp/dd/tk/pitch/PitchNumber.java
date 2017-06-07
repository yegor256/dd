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

import java.io.IOException;
import org.takes.Request;
import org.takes.rq.RqHeaders;

/**
 * Path to the pitch.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 1.0
 */
final class PitchNumber extends Number {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 408648085627351628L;

    /**
     * The request.
     */
    private final Request request;

    /**
     * Ctor.
     * @param req Request
     */
    PitchNumber(final Request req) {
        super();
        this.request = req;
    }

    @Override
    public int intValue() {
        throw new UnsupportedOperationException("#intValue()");
    }

    @Override
    public long longValue() {
        try {
            return Long.parseLong(
                new RqHeaders.Smart(
                    new RqHeaders.Base(this.request)
                ).single("X-Haters-Pitch")
            );
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public float floatValue() {
        throw new UnsupportedOperationException("#floatValue()");
    }

    @Override
    public double doubleValue() {
        throw new UnsupportedOperationException("#doubleValue()");
    }
}
