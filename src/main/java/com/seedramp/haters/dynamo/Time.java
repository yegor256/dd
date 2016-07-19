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
package com.seedramp.haters.dynamo;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Date+time in DynamoDB.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
final class Time {

    /**
     * The value.
     */
    private final transient long date;

    /**
     * Ctor.
     * @param attr Attribute value
     */
    Time(final AttributeValue attr) {
        this(Long.parseLong(attr.getN()));
    }

    /**
     * Ctor.
     * @param day Day
     */
    Time(final long day) {
        this.date = day;
    }

    /**
     * Print it to ISO 8601.
     * @return ISO 8601
     */
    public String iso() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
            .withZone(ZoneOffset.UTC)
            .format(Instant.ofEpochMilli(this.date));
    }

    /**
     * Is it mature enough?
     * @return TRUE if mature (over 12 hours)
     */
    public boolean isMature() {
        return this.date - System.currentTimeMillis()
            + TimeUnit.HOURS.toMillis(12L) < 0L;
    }

}
