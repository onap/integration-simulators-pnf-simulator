/*
 * ============LICENSE_START=======================================================
 * PNF-REGISTRATION-HANDLER
 * ================================================================================
 * Copyright (C) 2020 Nokia. All rights reserved.
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

import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class SslAuthenticationHelperTest {

    private SslAuthenticationHelper sslAuthenticationHelper = new SslAuthenticationHelper();

    @Test
    void shouldBeCreated(){
        Assert.assertNotNull(sslAuthenticationHelper);
    }

    @Test
    void shouldSetClientCertificateEnabled(){
        sslAuthenticationHelper.setClientCertificateEnabled(true);
        Assert.assertTrue(sslAuthenticationHelper.isClientCertificateEnabled());
    }

    @Test
    void shouldSetClientCertificateDisable(){
        sslAuthenticationHelper.setClientCertificateEnabled(false);
        Assert.assertFalse(sslAuthenticationHelper.isClientCertificateEnabled());
    }

    @Test
    void shouldSetClientCertificateDir(){
        sslAuthenticationHelper.setClientCertificateDir("dir");
        Assert.assertEquals("dir",sslAuthenticationHelper.getClientCertificateDir());
    }

    @Test
    void shouldSetClientCertificatePassword(){
        sslAuthenticationHelper.setClientCertificatePassword("pass");
        Assert.assertEquals("pass",sslAuthenticationHelper.getClientCertificatePassword());
    }
    @Test
    void shouldSetTrustStoreDir(){
        sslAuthenticationHelper.setTrustStoreDir("dir");
        Assert.assertEquals("dir",sslAuthenticationHelper.getTrustStoreDir());
    }

    @Test
    void shouldSetTrustStorePassword(){
        sslAuthenticationHelper.setTrustStorePassword("pass");
        Assert.assertEquals("pass",sslAuthenticationHelper.getTrustStorePassword());
    }

}
