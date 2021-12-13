package com.kpsec.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kpsec.test.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Query(value = "SELECT user_id as userId, account_no as accountNo FROM account WHERE user_id = :userId", nativeQuery = true)
    List<Account> getAccountByUserId(@Param("userId") String userId);

    List<Account> findByUserId(@Param("userId") String userId);
}
