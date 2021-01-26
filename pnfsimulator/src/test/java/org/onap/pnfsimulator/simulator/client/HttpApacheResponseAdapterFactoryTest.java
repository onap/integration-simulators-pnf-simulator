/*
 * ============LICENSE_START=======================================================
 * PNF-REGISTRATION-HANDLER
 * ================================================================================
 * Copyright (C) 2021 Nokia. All rights reserved.
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

package org.onap.pnfsimulator.simulator.client;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.onap.pnfsimulator.simulator.client.HttpTestUtils.createMockedHttpEntity;
import static org.onap.pnfsimulator.simulator.client.HttpTestUtils.createStatusLine;

class HttpApacheResponseAdapterFactoryTest {

    private HttpResponse httpResponse;

    @BeforeEach
    void setup() {
        httpResponse = mock(HttpResponse.class);
    }

    @Test
    void shouldCreateCorrectHttpResponseAdapterFromApacheHttpAcceptedResponse() throws IOException {
        final int responseCode = HttpStatus.SC_ACCEPTED;
        final String responseBody = "Accepted";
        doReturn(createStatusLine(responseCode)).when(httpResponse).getStatusLine();
        doReturn(createMockedHttpEntity(responseBody)).when(httpResponse).getEntity();

        HttpResponseAdapter httpResponseAdapter = new HttpApacheResponseAdapterFactory().create(httpResponse);

        assertEquals(responseCode, httpResponseAdapter.getCode());
        assertEquals(responseBody, httpResponseAdapter.getMessage());
    }

    @Test
    void shouldCreateCorrectHttpResponseAdapterFromApacheHttpForbiddenResponse() throws IOException {
        final int responseCode = HttpStatus.SC_FORBIDDEN;
        final String responseBody = "Forbidden";
        doReturn(createStatusLine(responseCode)).when(httpResponse).getStatusLine();
        doReturn(createMockedHttpEntity(responseBody)).when(httpResponse).getEntity();

        HttpResponseAdapter httpResponseAdapter = new HttpApacheResponseAdapterFactory().create(httpResponse);

        assertEquals(responseCode, httpResponseAdapter.getCode());
        assertEquals(responseBody, httpResponseAdapter.getMessage());
    }

    @Test
    void shouldCreateCorrectHttpResponseAdapterFromApacheHttpResponseWithEmptyEntity() throws IOException {
        final int responseCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        doReturn(createStatusLine(responseCode)).when(httpResponse).getStatusLine();
        doReturn(null).when(httpResponse).getEntity();

        HttpResponseAdapter httpResponseAdapter = new HttpApacheResponseAdapterFactory().create(httpResponse);

        assertEquals(responseCode, httpResponseAdapter.getCode());
        assertEquals("", httpResponseAdapter.getMessage());
    }


}
