package com.rahil;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;

/**
 * Created by rahilparikh on 20/10/18.
 */
public class ConnectionUtil {

    private static final Logger log = LoggerFactory.getLogger(ConnectionUtil.class);

    private static PoolingHttpClientConnectionManager cm;
    private static SSLConnectionSocketFactory socketFactory;

    static {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);

        try {
            socketFactory = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static PoolingHttpClientConnectionManager getConnectionManager() {
        return cm;
    }

    public static SSLConnectionSocketFactory getSocketFactory() {
        return socketFactory;
    }

    public CloseableHttpClient httpClient() {
        return HttpClients.custom().setSSLSocketFactory(socketFactory).setConnectionManager(cm).build();
    }

    public static Object parseResponse(CloseableHttpResponse response, Class clazz) {
        if (response == null) {
            ;
            return response;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            Gson gson = new Gson();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return gson.fromJson(sb.toString(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
