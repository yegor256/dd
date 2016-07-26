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
package com.seedramp.haters.tx;

import com.seedramp.haters.core.Author;
import java.io.IOException;
import org.takes.rq.RqForm;
import org.takes.rq.form.RqFormSmart;

/**
 * Submitted question.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class TxSubmitted extends AbstractText {

    /**
     * Author.
     */
    private final transient Author author;

    /**
     * Form.
     */
    private final transient RqFormSmart form;

    /**
     * Ctor.
     * @param atr Author
     * @param frm Form
     */
    public TxSubmitted(final Author atr, final RqForm frm) {
        super();
        this.author = atr;
        this.form = new RqFormSmart(frm);
    }

    @Override
    public String toText() throws IOException {
        this.author.pitches().submit(
            this.form.single("title"),
            this.form.single("text")
        );
        return "submitted, thanks";
    }

}
