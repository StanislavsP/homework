package org.test.homework.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static javax.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude={"created"})
public final class AccountOperation {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Getter private Long id;

    @Getter private String personId;

    @JsonInclude(NON_NULL)
    @Getter private String name;

    @JsonInclude(NON_NULL)
    @Getter private String surname;

    @Getter private BigDecimal amount;

    @Getter @Setter private String country;

    @Getter private Date created = new Date();

    public AccountOperation(String personId, BigDecimal amount) {
        this.personId = personId;
        this.amount = amount;
    }

    public AccountOperation(String personId, String name, String surname, BigDecimal amount) {
        this.personId = personId;
        this.name = name;
        this.surname = surname;
        this.amount = amount;
    }
}