package com.rahil;

/**
 * Created by rahilparikh on 20/10/18.
 */
public class Application {

    public static void main(String[] args) {
        WeatherService ws = new WeatherService();
        try {
            ws.getWeather("bangalore", "2018/06/01");
            ws.getWeather("bangalore", "2018/06/02");
            ws.getWeather("bangalore", "2018/06/03");

        } catch (Exception e) {
            e.printStackTrace();
        }
//        SSLContext sslContext = SSLContext.getDefault();
//        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
//        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
//        HttpGet getRequest = new HttpGet("https://www.metaweather.com/api/location/search/?query=bangalore");
//        CloseableHttpResponse response = client.execute(getRequest);
//        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//        StringBuilder sb = new StringBuilder();
//        String line = null;
//        while((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//        CityDetail[] cityDetail = new Gson().fromJson(sb.toString(), CityDetail[].class);
//        System.out.println(cityDetail[0].toString());
    }
}
