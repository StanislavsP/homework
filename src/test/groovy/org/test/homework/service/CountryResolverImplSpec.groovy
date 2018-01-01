package org.test.homework.service

import org.springframework.http.ResponseEntity
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.web.client.AsyncRestTemplate
import org.test.homework.service.CountryResolverImpl
import spock.lang.Specification

import static org.springframework.http.HttpStatus.OK

class CountryResolverImplSpec extends Specification {

    def restTemplate = Mock AsyncRestTemplate
    def countryResolver = new CountryResolverImpl('http://freegeoip.net/json/%s', 'country_code', 1000, 'LV', restTemplate)

    def response = '{"ip":"1.2.3.4","country_code":"US","country_name":"United States","region_code":"WA","region_name":"Washington","city":"Mukilteo","zip_code":"98275","time_zone":"America/Los_Angeles","latitude":47.913,"longitude":-122.3042,"metro_code":819}'

    def future = Mock ListenableFuture

    def 'should call REST API'() {
        when:
        def result = countryResolver.resolve('1.2.3.4')

        then:
        1 * restTemplate.getForEntity('http://freegeoip.net/json/1.2.3.4', String.class) >> future
        1 * future.get(_,_) >> new ResponseEntity<String>(response, OK)
        result == 'US'
    }

    def 'when REST API call fails should return default country'() {
        when:
        def result = countryResolver.resolve('1.2.3.4')

        then:
        1 * restTemplate.getForEntity('http://freegeoip.net/json/1.2.3.4', String.class) >> future
        1 * future.get(_,_) >> { throw new  RuntimeException()}
        result == 'LV'
    }

    def 'when REST API response parsing fails should return default country'() {
        when:
        def result = countryResolver.resolve('1.2.3.4')

        then:
        1 * restTemplate.getForEntity('http://freegeoip.net/json/1.2.3.4', String.class) >> future
        1 * future.get(_,_) >> new ResponseEntity<String>('INVALID RESPONSE', OK)
        result == 'LV'
    }
}
