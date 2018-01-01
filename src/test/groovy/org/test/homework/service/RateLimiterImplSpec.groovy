package org.test.homework.service

import spock.lang.Specification

class RateLimiterImplSpec extends Specification {

    def 'when limit is not exceeded should not throw exception'() {
        given:
        def requestLimitValidator = new RateLimiterImpl(2,1000)

        when:
        requestLimitValidator.check('US')
        requestLimitValidator.check('US')

        then:
        noExceptionThrown()
    }

    def 'when limit is exceeded should throw exception'() {
        given:
        def requestLimitValidator = new RateLimiterImpl(2,1000)

        when:
        requestLimitValidator.check('US')
        requestLimitValidator.check('US')
        requestLimitValidator.check('US')

        then:
        thrown(IllegalStateException)
    }

    def 'when limit is not exceeded for each country should not throw exception'() {
        given:
        def requestLimitValidator = new RateLimiterImpl(2,1000)

        when:
        requestLimitValidator.check('US')
        requestLimitValidator.check('US')
        requestLimitValidator.check('LV')
        requestLimitValidator.check('LV')

        then:
        noExceptionThrown()
    }

    def 'when request already expired should not throw exception'() {
        given:
        def requestLimitValidator = new RateLimiterImpl(1,1)

        when:
        requestLimitValidator.check('US')
        sleep(1)
        requestLimitValidator.check('US')
        sleep(1)
        requestLimitValidator.check('US')

        then:
        noExceptionThrown()
    }
}
