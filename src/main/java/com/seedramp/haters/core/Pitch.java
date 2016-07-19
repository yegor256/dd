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

import java.io.IOException;

/**
 * Pitch.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
public interface Pitch {

    /**
     * Votes.
     * @return Its votes
     * @throws IOException If fails
     */
    Votes votes() throws IOException;

    /**
     * ID of it.
     * @return ID of the pitch
     * @throws IOException If fails
     */
    long number() throws IOException;

    /**
     * Approve it.
     * @param author Who approves it
     * @throws IOException If fails
     */
    void approve(String author) throws IOException;

    /**
     * Delete it.
     * @throws IOException If fails
     */
    void delete() throws IOException;

    /**
     * Name of the author.
     * @return Author of the pitch (twitter handle)
     * @throws IOException If fails
     */
    String author() throws IOException;

    /**
     * Text of the pitch.
     * @return The text
     * @throws IOException If fails
     */
    String text() throws IOException;

}
