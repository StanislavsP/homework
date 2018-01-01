package org.test.homework.rest

import org.test.homework.domain.AccountOperation
import org.test.homework.service.AccountService
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class AccountControllerSpec extends Specification {

    def accountService = Mock AccountService
    def request = Mock HttpServletRequest
    def controller = new AccountController(accountService)

    def 'should deposit'() {
        when:
        controller.deposit('PERSON_ID', 'Name', 'Surname', 12345.00, request)

        then:
        1 * request.getRemoteAddr() >> '1.2.3.4'
        1 * accountService.createAccountOperation( new AccountOperation('PERSON_ID', 'Name', 'Surname', 12345.00), '1.2.3.4')
    }

    def 'should withdraw'() {
        when:
        controller.withdrawal('PERSON_ID', 12345.00, request)

        then:
        1 * request.getRemoteAddr() >> '1.2.3.4'
        1 * accountService.createAccountOperation( new AccountOperation('PERSON_ID', -12345.00), '1.2.3.4')
    }

    def 'should list all operations'() {
        when:
        controller.getAllOperations()

        then:
        1 * accountService.getAllOperations()
    }

    def 'should list all operations by user'() {
        when:
        controller.getOperationsByUser('PERSON_ID')

        then:
        1 * accountService.getOperationsByUser('PERSON_ID')
    }
}
