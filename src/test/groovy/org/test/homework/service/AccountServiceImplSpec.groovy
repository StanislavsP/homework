package org.test.homework.service

import org.test.homework.domain.AccountOperation
import org.test.homework.repository.AccountOperationRepository
import spock.lang.Specification

class AccountServiceImplSpec extends Specification {

    def countryResolver = Mock CountryResolver
    def rateLimiter = Mock RateLimiter
    def balanceManager = Mock BalanceManager
    def repository = Mock AccountOperationRepository
    def accountService = new AccountServiceImpl(countryResolver, rateLimiter, balanceManager, repository)

    def 'should check request limit, update balance and save operation'() {
        when:
        accountService.createAccountOperation(new AccountOperation('PERSON_ID', 'Name', 'Surname', 12345.00), '1.2.3.4')

        then:
        1 * countryResolver.resolve('1.2.3.4') >> 'US'
        1 * rateLimiter.check('US')
        1 * balanceManager.add('PERSON_ID', 12345.00)
        1 * repository.save(new AccountOperation(personId: 'PERSON_ID', name: 'Name', surname: 'Surname', amount: 12345.00, country: 'US'))
    }

    def 'when request limit exceeded should throw exception'() {
        when:
        accountService.createAccountOperation(new AccountOperation('PERSON_ID', 'Name', 'Surname', 12345.00), '1.2.3.4')

        then:
        1 * countryResolver.resolve('1.2.3.4') >> 'US'
        1 * rateLimiter.check('US') >> {throw new IllegalStateException()}
        0 * balanceManager.add(_,_)
        0 * repository.save(_)
        thrown(IllegalStateException)
    }

    def 'when negative balance should throw exception'() {
        when:
        accountService.createAccountOperation(new AccountOperation('PERSON_ID', -12345.00), '1.2.3.4')

        then:
        1 * countryResolver.resolve('1.2.3.4') >> 'US'
        1 * rateLimiter.check('US')
        1 * balanceManager.add('PERSON_ID', -12345.00) >> {throw new IllegalStateException()}
        0 * repository.save(_)
        thrown(IllegalStateException)
    }

    def 'when get all operations should call repository'() {
        when:
        accountService.getAllOperations()

        then:
        1 * repository.findAllByOrderByCreatedAsc()
    }

    def 'when get all operations by user should call repository'() {
        when:
        accountService.getOperationsByUser('PERSON_ID')

        then:
        1 * repository.findByPersonIdIgnoreCaseOrderByCreatedAsc('PERSON_ID')
    }
}
