package com.kpsec.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kpsec.test.model.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT user_id as userId, user_name as userName, user_age as userAge, join_date as joinDate FROM user WHERE user_Id = :userId", nativeQuery = true)
    List<User> getAccountByUserId(@Param("userId") String userId);

    Optional<User> findById(@Param("userId") String userId);
}
