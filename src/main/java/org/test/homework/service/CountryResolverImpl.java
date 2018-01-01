package org.test.homework.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CountryResolverImpl implements CountryResolver {

    private static final Logger LOG = getLogger(CountryResolverImpl.class);

    private final AsyncRestTemplate restTemplate;
    private final String url;
    private final String fieldForCountry;
    private final long timeout;
    private final String defaultCountry;

    @Autowired
    public CountryResolverImpl(@Value("${country.resolver.url}") String url,
                               @Value("${country.resolver.field}") String fieldForCountry,
                               @Value("${country.resolver.timeout.ms}") long timeout,
                               @Value("${country.resolver.default.country}") String defaultCountry,
                               AsyncRestTemplate restTemplate) {
        this.url = url;
        this.fieldForCountry = fieldForCountry;
        this.timeout = timeout;
        this.defaultCountry = defaultCountry;
        this.restTemplate = restTemplate;
    }

    @Override
    public String resolve(String ip) {
        try {
            String response = getResponseFromWebService(ip);
            return getCountryCodeFromJson(response);
        } catch (Exception e) {
            LOG.error("Error when resolving country by IP " + ip, e);
            return defaultCountry;
        }
    }

    private String getResponseFromWebService(String ip) throws InterruptedException, ExecutionException, TimeoutException {
        String requestUrl = String.format(url, ip);
        Future<ResponseEntity<String>> future = restTemplate.getForEntity(requestUrl, String.class);
        return future.get(timeout, MILLISECONDS).getBody();
    }

    private String getCountryCodeFromJson(String response) {
        JSONObject jsonObject = new JSONObject(response);
        return (String)jsonObject.get(fieldForCountry);
    }
}