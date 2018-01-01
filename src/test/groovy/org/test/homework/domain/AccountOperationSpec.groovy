package org.test.homework.domain

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class AccountOperationSpec extends Specification {

    def 'should be correct equals and hashcode'() {
        expect:
        EqualsVerifier.forClass(AccountOperation.class).verify()
    }
}
