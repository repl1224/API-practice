package com.kpsec.test.infra;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kpsec.test.model.entity.Account;
import com.kpsec.test.model.entity.AccountDtl;
import com.kpsec.test.model.entity.User;
import com.kpsec.test.repository.AccountDtlRepository;
import com.kpsec.test.repository.AccountRepository;
import com.kpsec.test.repository.UserRepository;

@Component
public class InitData {

    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    AccountDtlRepository accountDtlRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initAccount() throws IOException {
    	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! PostConstruct");
    	
        if (accountRepository.count() == 0) {
            Resource resource1 = new ClassPathResource("계좌.csv");
            List<Account> accountList = Files.readAllLines(resource1.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split(",");
                        System.out.println(split);
                        return Account.builder().userId(split[0]).accountNo(split[1])
                                .build();
                    }).collect(Collectors.toList());
            accountRepository.saveAll(accountList);
        }
        
        if (accountDtlRepository.count() == 0) {
            Resource resource2 = new ClassPathResource("계좌내역.csv");
            List<AccountDtl> accountDtlList = Files.readAllLines(resource2.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split(",");
                        System.out.println(split);
                        return AccountDtl.builder().accountNo(split[0]).statement(split[1]).amount(Long.parseLong(split[2])).depositDate(split[3])
                                .build();
                    }).collect(Collectors.toList());
            accountDtlRepository.saveAll(accountDtlList);
        }
        
        if (userRepository.count() == 0) {
            Resource resource3 = new ClassPathResource("사용자.csv");
            List<User> userList = Files.readAllLines(resource3.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split(",");
                        System.out.println(split);
                        return User.builder().userId(split[0]).userName(split[1]).userAge(Integer.parseInt(split[2].trim())).joinDate(split[3])
                                .build();
                    }).collect(Collectors.toList());
            userRepository.saveAll(userList);
        }
    }
}
