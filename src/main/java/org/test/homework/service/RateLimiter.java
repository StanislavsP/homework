package org.test.homework.service;

public interface RateLimiter {

    void check(String country);
}