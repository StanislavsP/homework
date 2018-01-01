package org.test.homework.service;

import org.isomorphism.util.TokenBucket;
import org.isomorphism.util.TokenBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Component
public class RateLimiterImpl implements RateLimiter {

    private final int maxRequestCount;
    private final long timeIntervalMs;
    private final ConcurrentMap<String, TokenBucket> tokenBuckets = new ConcurrentHashMap<>();

    public RateLimiterImpl(@Value("${request.limit.validator.max.request.count.per.country}") int maxRequestCount,
                           @Value("${request.limit.validator.time.interval.ms}") long timeIntervalMs) {
        this.maxRequestCount = maxRequestCount;
        this.timeIntervalMs = timeIntervalMs;
    }

    @Override
    public void check(String country) {
        tokenBuckets.putIfAbsent(country, createTokenBucket());
        TokenBucket bucketForCountry = tokenBuckets.get(country);
        if (!bucketForCountry.tryConsume()) {
            throw new IllegalStateException("Request limit exceeded for country " + country);
        }
    }

    private TokenBucket createTokenBucket() {
        return TokenBuckets.builder()
                .withCapacity(maxRequestCount)
                .withFixedIntervalRefillStrategy(maxRequestCount, timeIntervalMs, MILLISECONDS)
                .build();
    }
}