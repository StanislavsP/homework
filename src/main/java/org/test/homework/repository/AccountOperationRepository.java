package org.test.homework.repository;

import org.test.homework.domain.AccountOperation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountOperationRepository extends CrudRepository<AccountOperation, Long> {

    List<AccountOperation> findAllByOrderByCreatedAsc();

    List<AccountOperation> findByPersonIdIgnoreCaseOrderByCreatedAsc(String personId);
}