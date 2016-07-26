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

import com.seedramp.haters.core.Pitch;
import java.io.IOException;
import org.takes.rq.RqForm;
import org.takes.rq.form.RqFormSmart;

/**
 * Comment posted.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public final class TxPosted extends AbstractText {

    /**
     * Pitch.
     */
    private final transient Pitch pitch;

    /**
     * Request.
     */
    private final transient RqForm form;

    /**
     * Ctor.
     * @param pth Pitch
     * @param frm Form
     */
    public TxPosted(final Pitch pth, final RqForm frm) {
        super();
        this.pitch = pth;
        this.form = frm;
    }

    @Override
    public String toText() throws IOException {
        this.pitch.comments().post(
            new RqFormSmart(this.form).single("text")
        );
        return "thanks!";
    }
}
