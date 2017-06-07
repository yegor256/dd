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
package com.seedramp.dd.tx;

import java.io.IOException;

/**
 * Text.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 1.0
 */
abstract class AbstractText implements CharSequence {

    /**
     * Make it.
     * @return Text
     * @throws IOException If fails
     */
    public abstract String toText() throws IOException;

    @Override
    public final String toString() {
        try {
            return this.toText();
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public final int length() {
        return this.toString().length();
    }

    @Override
    public final char charAt(final int index) {
        return this.toString().charAt(index);
    }

    @Override
    public final CharSequence subSequence(final int start, final int end) {
        return this.toString().subSequence(start, end);
    }
}
