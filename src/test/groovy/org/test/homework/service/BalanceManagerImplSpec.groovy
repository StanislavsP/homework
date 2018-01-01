package org.test.homework.service

import spock.lang.Specification

class BalanceManagerImplSpec extends Specification {

    def balanceManager = new BalanceManagerImpl()

    def 'start balance is zero'() {
        expect:
        balanceManager.get('PERSON_ID') == 0.00
    }

    def 'should sum all balance operations'() {
        when:
        balanceManager.add('PERSON_ID', 100.00)
        balanceManager.add('PERSON_ID', -50.00)

        then:
        balanceManager.get('PERSON_ID') == 50.00
    }

    def 'when balance is negative should throw exception'() {
        when:
        balanceManager.add('PERSON_ID', 100.00)
        balanceManager.add('PERSON_ID', -200.00)

        then:
        thrown(IllegalStateException)
    }
}
