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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Optional;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;

public class CertAuthSslContextFactory {

    private CertAuthSslContextFactory() {}

    static SSLContext createSslContext(SslAuthenticationHelper sslAuthenticationHelper)
        throws GeneralSecurityException, IOException {
        final String keystorePasswordPath = sslAuthenticationHelper.getClientCertificatePasswordPath();

        final KeyStore keystore = readCertificate(sslAuthenticationHelper.getClientCertificatePath(),
            keystorePasswordPath, "PKCS12");
        final KeyStore truststore = readCertificate(sslAuthenticationHelper.getTrustStorePath(),
            sslAuthenticationHelper.getTrustStorePasswordPath(), "JKS");

        return SSLContexts.custom()
            .loadKeyMaterial(keystore, getPasswordAsCharArray(keystorePasswordPath))
            .loadTrustMaterial(truststore, new TrustSelfSignedStrategy())
            .build();
    }

    private static KeyStore readCertificate(String certificatePath, String passwordPath, String type) throws GeneralSecurityException, IOException {
        try (InputStream keyStoreStream = new FileInputStream(certificatePath)) {
            KeyStore keyStore = KeyStore.getInstance(type);
            keyStore.load(keyStoreStream, getPasswordAsCharArray(passwordPath));
            return keyStore;
        }
    }

    private static char[] getPasswordAsCharArray(String passwordPath) throws IOException {
        final String password = Files.readString(Path.of(passwordPath));
        return Optional.ofNullable(password).map(String::toCharArray).orElse(null);
    }
}
