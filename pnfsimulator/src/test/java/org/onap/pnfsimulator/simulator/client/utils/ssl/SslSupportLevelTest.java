/*
 * ============LICENSE_START=======================================================
 * PNF-REGISTRATION-HANDLER
 * ================================================================================
 * Copyright (C) 2018 Nokia. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.pnfsimulator.simulator.client.utils.ssl;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SslSupportLevelTest {

    private static final String HTTPS_URL = "https://127.0.0.1:8443/";
    private static final String HTTP_URL = "http://127.0.0.1:8080/";

    @Test
    void testShouldReturnAlwaysTrustSupportLevelForHttpsUrl() throws MalformedURLException {
        SslSupportLevel actualSupportLevel = SslSupportLevel.getSupportLevelBasedOnProtocol(HTTPS_URL);
        assertEquals(SslSupportLevel.ALWAYS_TRUST, actualSupportLevel);
    }

    @Test
    void testShouldReturnNoneSupportLevelForHttpUrl() throws MalformedURLException {
        SslSupportLevel actualSupportLevel = SslSupportLevel.getSupportLevelBasedOnProtocol(HTTP_URL);
        assertEquals(SslSupportLevel.NONE, actualSupportLevel);
    }

    @Test
    void testShouldRaiseExceptionWhenInvalidUrlPassed() {
        assertThrows(MalformedURLException.class, () -> SslSupportLevel.getSupportLevelBasedOnProtocol("http://bla:VES-PORT/"));
    }

}