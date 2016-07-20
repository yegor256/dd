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
package com.seedramp.haters.core;

import com.google.common.collect.Iterators;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import java.io.IOException;
import java.util.Iterator;
import org.xembly.Directive;
import org.xembly.Xembler;

/**
 * Pitches.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public interface Pitches {

    /**
     * Get one by number.
     * @param num Number
     * @return Pitch
     * @throws IOException If fails
     */
    Pitch pitch(long num) throws IOException;

    /**
     * Submit a new pitch.
     * @param title Title of the pitch
     * @param text Text to post
     * @throws IOException If fails
     */
    void submit(String title, String text) throws IOException;

    /**
     * Print them to Xembly.
     * @return Xembly
     * @throws IOException If fails
     */
    Iterable<Directive> inXembly() throws IOException;

    /**
     * List them as array.
     */
    final class AsArray implements Iterable<Pitch> {
        /**
         * Original.
         */
        private final transient Pitches pitches;
        /**
         * Ctor.
         * @param origin Pitches
         */
        public AsArray(final Pitches origin) {
            this.pitches = origin;
        }
        @Override
        public Iterator<Pitch> iterator() {
            final XML xml;
            try {
                xml = new XMLDocument(
                    new Xembler(this.pitches.inXembly()).xmlQuietly()
                );
            } catch (final IOException ex) {
                throw new IllegalStateException(ex);
            }
            return Iterators.transform(
                xml.xpath("//pitch/id/text()").iterator(),
                input -> {
                    try {
                        return this.pitches.pitch(Long.parseLong(input));
                    } catch (final IOException ex) {
                        throw new IllegalStateException(ex);
                    }
                }
            );
        }
    }

}
