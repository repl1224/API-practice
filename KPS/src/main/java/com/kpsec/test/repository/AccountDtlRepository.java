package com.kpsec.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kpsec.test.model.AccountResult;
import com.kpsec.test.model.entity.AccountDtl;

public interface AccountDtlRepository extends JpaRepository<AccountDtl, String> {

    @Query(value = "SELECT account_no as accountNo, statement, amount, deposit_date as depositDate FROM accountDtl WHERE account_no = :accountNo", nativeQuery = true)
    List<AccountResult> getAccountDtlByAccountNo(@Param("accountNo") String accountNo);

}
