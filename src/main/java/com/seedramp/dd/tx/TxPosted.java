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

import com.seedramp.dd.core.Comments;
import java.io.IOException;
import org.takes.rq.RqForm;
import org.takes.rq.form.RqFormSmart;

/**
 * Comment posted.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TxPosted extends AbstractText {

    /**
     * Comments.
     */
    private final transient Comments comments;

    /**
     * Request.
     */
    private final transient RqForm form;

    /**
     * Ctor.
     * @param cmt Comments
     * @param frm Form
     */
    public TxPosted(final Comments cmt, final RqForm frm) {
        super();
        this.comments = cmt;
        this.form = frm;
    }

    @Override
    public String toText() throws IOException {
        this.comments.post(
            new RqFormSmart(this.form).single("text")
        );
        return "thanks!";
    }
}
