package com.kpsec.test.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(AccountDtl.class)
public class AccountDtl implements Serializable {
	@Id
    private String accountNo;

	@Id
    private String statement;
    
	@Id
    private Long amount;
    
	@Id
    private String depositDate;
}
