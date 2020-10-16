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

package org.onap.pnfsimulator.simulator.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.onap.pnfsimulator.simulator.client.utils.ssl.SslAuthenticationHelper;
import org.onap.pnfsimulator.simulator.client.utils.ssl.SslSupportLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import static org.onap.pnfsimulator.logging.MdcVariables.REQUEST_ID;
import static org.onap.pnfsimulator.logging.MdcVariables.X_INVOCATION_ID;
import static org.onap.pnfsimulator.logging.MdcVariables.X_ONAP_REQUEST_ID;

public class HttpClientAdapterImpl implements HttpClientAdapter {

    private static final int CONNECTION_TIMEOUT = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientAdapterImpl.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final RequestConfig CONFIG = RequestConfig.custom()
        .setConnectTimeout(CONNECTION_TIMEOUT)
        .setConnectionRequestTimeout(CONNECTION_TIMEOUT)
        .setSocketTimeout(CONNECTION_TIMEOUT)
        .build();
    private static final Marker INVOKE = MarkerFactory.getMarker("INVOKE");
    private HttpClient client;
    private final String targetUrl;

    public HttpClientAdapterImpl(String targetUrl, SslAuthenticationHelper sslAuthenticationHelper)
            throws IOException, GeneralSecurityException {
        this.client = prepare(targetUrl, sslAuthenticationHelper).getClient(CONFIG, sslAuthenticationHelper);
        this.targetUrl = targetUrl;
    }

    HttpClientAdapterImpl(HttpClient client, String targetUrl) {
        this.client = client;
        this.targetUrl = targetUrl;
    }

    @Override
    public void send(String content) {
        try {
            HttpResponse response = sendAndRetrieve(content);
            EntityUtils.consumeQuietly(response.getEntity()); //response has to be fully consumed otherwise apache won't release connection
            LOGGER.info(INVOKE, "Message sent, ves response code: {}", response.getStatusLine());
        } catch (IOException e) {
            LOGGER.warn("Error sending message to ves: {}", e.getMessage(), e.getCause());
        }
    }

    private SslSupportLevel prepare(String targetUrl, SslAuthenticationHelper sslAuthenticationHelper) throws MalformedURLException {
        if (!sslAuthenticationHelper.isClientCertificateEnabled()) {
            return SslSupportLevel.getSupportLevelBasedOnProtocol(targetUrl);
        }
        return sslAuthenticationHelper.isStrictHostnameVerification() ? SslSupportLevel.CLIENT_CERT_AUTH_STRICT : SslSupportLevel.CLIENT_CERT_AUTH;
    }

    private HttpResponse sendAndRetrieve(String content) throws IOException {
        HttpPost request = createRequest(content);
        HttpResponse httpResponse = client.execute(request);
        request.releaseConnection();
        return httpResponse;
    }

    private HttpPost createRequest(String content) throws UnsupportedEncodingException {
        HttpPost request = new HttpPost(this.targetUrl);
        StringEntity stringEntity = new StringEntity(content);
        request.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        request.addHeader(X_ONAP_REQUEST_ID, MDC.get(REQUEST_ID));
        request.addHeader(X_INVOCATION_ID, UUID.randomUUID().toString());
        request.setEntity(stringEntity);
        return request;
    }

}
