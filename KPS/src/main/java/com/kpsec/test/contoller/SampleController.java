package com.kpsec.test.contoller;

import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kpsec.test.model.entity.Account;
import com.kpsec.test.model.entity.AccountDtl;
import com.kpsec.test.model.entity.User;
import com.kpsec.test.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Sample")
@RestController
@RequestMapping("/test")
public class SampleController {
    @Autowired
    private AccountService accountService;
    
    /**
     * 사용자를 추가한다.
     * @param useriInfo 사용자 정보 JSONArray 문자열
     * @throws ParseException
     */
    @ApiOperation(value = "addUser", tags = "사용자 추가")
    @GetMapping(value = "/addUser")
    public void addUser(@RequestParam("userInfo") String param) throws ParseException {
        accountService.addUser(param);
    }
    
    /**
     * 사용자 목록을 출력한다.
     * @return List<User>
     */
    @ApiOperation(value = "getUserList", tags = "사용자 목록")
    @GetMapping(value = "/getUserList")
    public List<User> getUserList() {
        return accountService.getUserList();
    }
    
    /**
     * 계좌 정보를 추가한다.
     * @param accountInfo 계좌 정보 JSONArray 문자열
     * @throws ParseException
     */
    @ApiOperation(value = "addAccount", tags = "계좌 추가")
    @GetMapping(value = "/addAccount")
    public void addAccount(@RequestParam("accountInfo") String param) throws ParseException {
        accountService.addAccount(param);
    }
    
    /**
     * 계좌 목록을 출력한다.
     * @return List<Account>
     */
    @ApiOperation(value = "getAccountList", tags = "계좌 목록")
    @GetMapping(value = "/getAccountList")
    public List<Account> getAccountList() {
        return accountService.getAccountList();
    }
    
    /**
     * 계좌내역 정보를 추가한다.
     * @param accountDtlInfo 계좌상세 정보 JSONArray 문자열
     * @throws ParseException
     */
    @ApiOperation(value = "addAccountDtl", tags = "계좌내역 추가")
    @GetMapping(value = "/addAccountDtl")
    public void addAccountDtl(@RequestParam("accountDtlInfo") String param) throws ParseException {
        accountService.addAccountDtl(param);
    }
    
    /**
     * 계좌내역 목록을 출력한다.
     * @return List<AccountDtl>
     */
    @ApiOperation(value = "getAccountDtlList", tags = "계좌내역 목록")
    @GetMapping(value = "/getAccountDtlList")
    public List<AccountDtl> getAccountDtlList() {
        return accountService.getAccountDtlList();
    }
    
    /**
     * 사용자를 입력받아, 사용자의 계좌별 예치금을 출력한다.
     * @param userId 사용자ID 문자열
     * @return List<Map<String, Object>>
     */
    @ApiOperation(value = "getUserAccountInfo", tags = "사용자를 입력받아, 사용자의 계좌별 예치금 출력")
    @GetMapping(value = "/getUserAccountInfo")
    public List<Map<String, Object>> getUserAccountInfo(@RequestParam("userId") String param) {
        return accountService.getUserAccountInfo(param);
    }
    
    /**
     * 사용자 나이대 별로, 평균 예치금을 출력한다.
     * @return List<Map<Integer, Object>>
     */
    @ApiOperation(value = "getAvgAmountByAge", tags = "사용자 나이대 별로, 평균 예치금을 출력")
    @GetMapping(value = "/getAvgAmountByAge")
    public List<Map<Integer, Object>> getAvgAmountByAge() {
        return accountService.getAvgAmountByAge();
    }
    
    /**
     * 년도를 입력받아, 해당년도의 예치금 총액을 출력한다.
     * @param year 년도 문자열(예: 2021)
     * @return Long
     */
    @ApiOperation(value = "getSumAmountByYear", tags = "년도를 입력받아, 해당년도의 예치금 총액을 출력")
    @GetMapping(value = "/getSumAmountByYear")
    public Long getSumAmountByYear(@RequestParam("year") String param) {
        return accountService.getSumAmountByYear(param);
    }
    
    /**
     * 기간을 입력받아, 돈을 많이 예치한 사용자 순으로 정렬해서 출력한다.
     * @param strDate 시작일자(예 : 2019-01-01)
     * @param endDate 마지막일자(예 : 2019-12-31)
     * @return List<Map<String, Object>>
     */
    @ApiOperation(value = "getSortedUserListByAmountInPeriod", tags = "기간을 입력받아, 돈을 많이 예치한 사용자 순으로 정렬해서 출력")
    @GetMapping(value = "/getSortedUserListByAmountInPeriod")
    public List<Map<String, Object>> getSortedUserListByAmountInPeriod(@RequestParam("strDate") String param1, @RequestParam("endDate") String param2) {
        return accountService.getSortedUserListByAmountInPeriod(param1, param2);
    }
}
