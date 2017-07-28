package com.caiyi.mycalendar.Okhttp;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Https工具类.
 * see: square/okhttp/samples/guide/src/main/java/okhttp3/recipes/CustomTrust.java
 *
 *
 * @author Ren ZeQiang
 * @since 2017/7/26
 */
public class HttpsUtil {
    public static class SSLParams {
        public SSLSocketFactory sSLSocketFactory;
        public X509TrustManager trustManager;
    }

    public static SSLParams getSslSocketFactory(InputStream certificates) {
        return getSslSocketFactory(certificates, null, null);
    }

    public static SSLParams getSslSocketFactory(InputStream certificates, InputStream bksFile, String password) {
        SSLParams sslParams = new SSLParams();
        try {
            X509TrustManager trustManager = prepareX509TrustManager(certificates);
            KeyManager[] keyManagers = prepareKeyManager(bksFile, password);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, new TrustManager[]{trustManager}, null);
            sslParams.sSLSocketFactory = sslContext.getSocketFactory();
            sslParams.trustManager = trustManager;
            return sslParams;
        } catch (Exception e) {
            throw new AssertionError("证书出错" + e);
        }
    }

    private static X509TrustManager prepareX509TrustManager(InputStream certificate) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate ca = certificateFactory.generateCertificate(certificate);
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        keyStore.setCertificateEntry("ca", ca);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        TrustManager[] trustManagers = tmf.getTrustManagers();

        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private static KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
        if (bksFile == null || password == null) {
            return null;
        }
        try {
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
