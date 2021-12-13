package com.kpsec.test.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpsec.test.model.entity.Account;
import com.kpsec.test.model.entity.AccountDtl;
import com.kpsec.test.model.entity.User;
import com.kpsec.test.repository.AccountDtlRepository;
import com.kpsec.test.repository.AccountRepository;
import com.kpsec.test.repository.UserRepository;

@Service
@Transactional
public class AccountService {

	@Autowired
    AccountRepository accountRepository;
    
    @Autowired
    AccountDtlRepository accountDtlRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    /**
     * 사용자를 추가한다.
     * @param useriInfo 사용자 정보 JSONArray 문자열
     * @throws ParseException
     */
    public void addUser(String useriInfo) throws ParseException {
    	JSONParser jp = new JSONParser(useriInfo);
    	
    	List<User> userList = jp.parseArray().stream().map(temp -> {
    		JSONObject jo = (JSONObject) temp;
    		User user = User.builder()
    				.userId((String)jo.get("userId"))
    				.userName((String)jo.get("userName"))
    				.userAge((Integer)jo.get("userAge"))
    				.joinDate((String)jo.get("joinDate")).build();
    		
    		return user;
    	}).collect(Collectors.toList());
    	
    	userRepository.saveAll(userList);
    }
    
    /**
     * 사용자 목록을 출력한다.
     * @return List<User>
     */
    public List<User> getUserList() {
    	return userRepository.findAll();
    }
    
    /**
     * 계좌 정보를 추가한다.
     * @param accountInfo 계좌 정보 JSONArray 문자열
     * @throws ParseException
     */
    public void addAccount(String accountInfo) throws ParseException {
    	JSONParser jp = new JSONParser(accountInfo);
    	
    	List<Account> accountList = jp.parseArray().stream().map(temp -> {
    		JSONObject jo = (JSONObject) temp;
    		Account account = Account.builder()
    				.userId((String)jo.get("userId"))
    				.accountNo((String)jo.get("accountNo")).build();
    		
    		return account;
    	}).collect(Collectors.toList());
    	
    	accountRepository.saveAll(accountList);
    }
    
    /**
     * 계좌 목록을 출력한다.
     * @return List<Account>
     */
    public List<Account> getAccountList() {
    	return accountRepository.findAll();
    }
    
    /**
     * 계좌상세 정보를 추가한다.
     * @param accountDtlInfo 계좌내역 정보 JSONArray 문자열
     * @throws ParseException
     */
    public void addAccountDtl(String accountDtlInfo) throws ParseException {
    	JSONParser jp = new JSONParser(accountDtlInfo);
    	
    	List<AccountDtl> accountList = jp.parseArray().stream().map(temp -> {
    		JSONObject jo = (JSONObject) temp;
    		AccountDtl accountDtl = AccountDtl.builder()
    				.accountNo((String)jo.get("accountNo"))
    				.statement((String)jo.get("statement"))
    				.amount((Long)jo.get("amount"))
    				.depositDate((String)jo.get("depositDate")).build();
    		
    		return accountDtl;
    	}).collect(Collectors.toList());
    	
    	accountDtlRepository.saveAll(accountList);
    }
    
    /**
     * 계좌내역 목록을 출력한다.
     * @return List<AccountDtl>
     */
    public List<AccountDtl> getAccountDtlList() {
    	return accountDtlRepository.findAll();
    }
    
    /**
     * 사용자를 입력받아, 사용자의 계좌별 예치금을 출력한다.
     * @param userId 사용자ID 문자열
     * @return List<Map<String, Object>>
     */
    public List<Map<String, Object>> getUserAccountInfo(String userId) {
    	Optional<User> selUser = userRepository.findById(userId);
    	List<Account> accountList = accountRepository.findByUserId(userId);
    	List<AccountDtl> accountDtlList = this.getAccountDtlList();
    	
    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    	if(selUser.isPresent()) {
    		result = accountList.stream()
    				.filter(temp -> {
    					return temp.getUserId().equals(selUser.get().getUserId());
    				})
    				.flatMap(account -> {
						return accountDtlList.stream()
							.filter(accountDtl -> {
								return accountDtl.getAccountNo().equals(account.getAccountNo());
							})
							.map(temp -> {
								AccountDtl resultAccountDtl = new AccountDtl();
								resultAccountDtl.setAccountNo(temp.getAccountNo());
								if(temp.getStatement().equals("Y")) {
									resultAccountDtl.setAmount(temp.getAmount());
								} else {
									resultAccountDtl.setAmount(temp.getAmount() * -1);
								}
								
								return resultAccountDtl;
							});
					}).collect(Collectors.groupingBy(AccountDtl::getAccountNo))
				.entrySet().stream()
				.map(entry -> {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					
					Long sum = entry.getValue().stream().collect(Collectors.summingLong(AccountDtl::getAmount));
					resultMap.put(entry.getKey(), sum);
					return resultMap;
			}).collect(Collectors.toList());
    	}
    	
    	return result;
    }
    
    /**
     * 사용자 나이대 별로, 평균 예치금을 출력한다.
     * @return List<Map<Integer, Object>>
     */
    public List<Map<Integer, Object>> getAvgAmountByAge() {
    	List<User> userList = this.getUserList();
    	List<Account> accountList = this.getAccountList();
    	List<AccountDtl> accountDtlList = this.getAccountDtlList();
    	
    	List<Map<Integer, Object>> resultList = userList.stream()
			.flatMap(user -> {
				return accountList.stream()
					.filter(filt -> filt.getUserId().equals(user.getUserId()))
					.flatMap(account -> {
						return accountDtlList.stream()
							.filter(accountDtl -> {
								return accountDtl.getAccountNo().equals(account.getAccountNo());
							})
							.map(temp -> {
								Map<String, Object> result = new HashMap<String, Object>();
								result.put("userAge", user.getUserAge() / 10 * 10);
								
								if(temp.getStatement().equals("Y")) {
									result.put("amount", temp.getAmount());
								} else {
									result.put("amount", temp.getAmount() * -1);
								}
								
								return result;
							});
					});
			}).collect(Collectors.groupingBy(map -> map.get("userAge")))
					.entrySet().stream()
					.map(entry -> {
						Map<Integer, Object> resultMap = new HashMap<Integer, Object>();
						
						Double avg = entry.getValue().stream()
								.filter(temp -> {
									return temp.get("userAge").equals(entry.getKey());
								})
								.collect(Collectors.averagingLong(map -> (Long)map.get("amount")));
								
						resultMap.put((Integer) entry.getKey(), avg);
						
						return resultMap;
					}).collect(Collectors.toList());
    	
    	return resultList;
    }
    
    /**
     * 년도를 입력받아, 해당년도의 예치금 총액을 출력한다.
     * @param year 년도 문자열(예: 2021)
     * @return Long
     */
    public Long getSumAmountByYear(String year) {
    	List<AccountDtl> accountDtlList = this.getAccountDtlList();
    	
    	Long sum = accountDtlList.stream()
    			.filter(accountDtl -> {
    				return accountDtl.getDepositDate().substring(0, 4).equals(year);
    			})
    			.map(temp -> {
    				Map<String, Long> resultMap = new HashMap<String, Long>();
    				
    				if(temp.getStatement().equals("Y")) {
    					resultMap.put("amount", temp.getAmount());
    				} else {
    					resultMap.put("amount", temp.getAmount() * -1);
    				}
    				
    				return resultMap;
    			}).collect(Collectors.summingLong(map -> map.get("amount")));
    	
    	return sum;
    }
    
    /**
     * 기간을 입력받아, 돈을 많이 예치한 사용자 순으로 정렬해서 출력한다.
     * @param strDate 시작일자(예 : 2019-01-01)
     * @param endDate 마지막일자(예 : 2019-12-31)
     * @return List<Map<String, Object>>
     */
    public List<Map<String, Object>> getSortedUserListByAmountInPeriod(String strDate, String endDate) {
    	List<User> userList = this.getUserList();
    	List<Account> accountList = this.getAccountList();
    	List<AccountDtl> accountDtlList = this.getAccountDtlList();
    	
    	List<Map<String, Object>> resultList = userList.stream()
			.flatMap(user -> {
				return accountList.stream()
					.filter(filt -> filt.getUserId().equals(user.getUserId()))
					.flatMap(account -> {
						return accountDtlList.stream()
							.filter(accountDtl1 -> {
								return accountDtl1.getAccountNo().equals(account.getAccountNo());
							})
							.filter(accountDtl2 -> {
								int strDt = Integer.parseInt(strDate.replaceAll("-", ""));
								int endDt = Integer.parseInt(endDate.replaceAll("-", ""));
										
								return Integer.parseInt(accountDtl2.getDepositDate().replaceAll("-", "")) >= strDt
										&& Integer.parseInt(accountDtl2.getDepositDate().replaceAll("-", "")) <= endDt;
							})
							.map(temp -> {
								Map<String, Object> result = new HashMap<String, Object>();
								result.put("userId", user.getUserId());
								result.put("userName", user.getUserName());
								
								if(temp.getStatement().equals("Y")) {
									result.put("amount", temp.getAmount());
								} else {
									result.put("amount", temp.getAmount() * -1);
								}
								
								return result;
							});
					});
			}).collect(Collectors.groupingBy(map -> map.get("userId")))
					.entrySet().stream()
					.map(entry -> {
						Map<String, Object> resultMap = new HashMap<String, Object>();
						
						Long sum = entry.getValue().stream().collect(Collectors.summingLong(map -> (Long)map.get("amount")));
						resultMap.put("userId", entry.getValue().get(0).get("userId"));
						resultMap.put("userName", entry.getValue().get(0).get("userName"));
						resultMap.put("amount", sum);
						
						return resultMap;
					}).collect(Collectors.toList());
    	
    	Collections.sort(resultList, new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Long amount1 = (Long) o1.get("amount");
				Long amount2 = (Long) o2.get("amount");
				
				return amount2.compareTo(amount1);
			}
    	});
    	
    	return resultList;
    }
}
