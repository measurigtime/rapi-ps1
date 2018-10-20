package com.rahil;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rahilparikh on 20/10/18.
 */
public class WeatherService {

    private CloseableHttpClient metaweatherAPIClient;

    private static final String WOEID_URL = "https://www.metaweather.com/api/location/search";
    private static final String WEATHER_URL = "https://www.metaweather.com/api/location";

    private static Map<String, Long> woeidMap;

    static {
        woeidMap = new ConcurrentHashMap<String, Long>();
    }

    public WeatherService() {
        this.metaweatherAPIClient = new ConnectionUtil().httpClient();
    }

    public void getWeather(String city, String date) {
        try {
            Date requestDate = new SimpleDateFormat("YYYY/MM/DD").parse(date);
            if (requestDate == null || requestDate.after(new Date())) {
                System.out.println("Please enter a valid date before today in YYYY/MM/DD format");
                return;
            }
            Long woeid = this.getWoeid(city);
            String finalUrl = WEATHER_URL + "/" + woeid + "/" + date;
            URI uri = new URIBuilder(finalUrl).build();
            HttpGet weatherRequest = new HttpGet(uri);
            CloseableHttpResponse response = this.metaweatherAPIClient.execute(weatherRequest);
            WeatherDetail[] weatherDetails = null;
            try {
                weatherDetails = (WeatherDetail[]) ConnectionUtil.parseResponse(response, WeatherDetail[].class);
            } catch (Exception e) {
                System.out.println("Error while parsing weather details");
                e.printStackTrace();
                return;
            }

            printAverageTemperatures(weatherDetails, date);
        } catch (ParseException e) {
            System.out.println("Print enter a valid date in YYYY/MM/DD format");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Long getWoeid(String city) throws Exception {
        if (woeidMap.containsKey(city)) {
            return woeidMap.get(city);
        }

        try {
            URI uri = new URIBuilder(WOEID_URL).setParameter("query", city).build();
            HttpGet woeidRequest = new HttpGet(uri);
            CloseableHttpResponse response = this.metaweatherAPIClient.execute(woeidRequest);
            CityDetail[] cityDetails = null;
            try {
                cityDetails = (CityDetail[]) ConnectionUtil.parseResponse(response, CityDetail[].class);
            } catch (Exception e) {
                System.out.println("Error while parsing city details");
                e.printStackTrace();
                return null;
            }
            if (cityDetails != null && cityDetails.length != 0) {
                Long woeid = cityDetails[0].getWoeid();
                woeidMap.put(city, woeid);
                return woeid;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new Exception("No valid data for city " + city);
    }

    public void printAverageTemperatures(WeatherDetail[] weatherDetails, String date) {
        Double minTemperature = 0.00;
        Double maxTemperature = 0.00;
        int numOfRecords = 0;
        for (WeatherDetail weatherDetail : weatherDetails) {
            minTemperature += weatherDetail.getMinTemp();
            maxTemperature += weatherDetail.getMaxTemp();
            numOfRecords += 1;
        }
        Double avgMin = minTemperature / numOfRecords;
        Double avgMax = maxTemperature / numOfRecords;

        System.out.println("Temperatures on " + date + ":");
        System.out.printf("Avg min: %.2f\n", avgMin);
        System.out.printf("Avg max: %.2f\n", avgMax);
    }

}
