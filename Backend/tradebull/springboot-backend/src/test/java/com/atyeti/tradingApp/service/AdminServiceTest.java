package com.atyeti.tradingApp.service;

import com.atyeti.tradingApp.models.CompanyModel;
import com.atyeti.tradingApp.models.UserModel;
import com.atyeti.tradingApp.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AdminService.class})
@ExtendWith(SpringExtension.class)
class AdminServiceTest {

    UserModel ram = new UserModel("ram", "ram@gmail.com", "12345678", "1234567890");
    UserModel sam = new UserModel("sam", "sam@gmail.com", "12345678", "1234567890");

    CompanyModel tcs = new CompanyModel("TCS", 100, 150, 200, 100,
            160, 52, 80, 300, 232, 23, 20);

    @Autowired
    private AdminService adminService;
    @MockBean
    private MyShareService myShareService;
    @MockBean
    private UserService userService;

    @MockBean
    private AdminRepository adminRepository;
    @MockBean
    private HistoryRepository historyRepository;
    @MockBean
    private CompanyRepository companyRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private MySharesRepository mySharesRepository;


    @Test
    void pendingAddFund() {
        Map<String, String> addfund = new HashMap<>();
        addfund.put("email", "ram@gmail.com");
        addfund.put("amount_left", "133");
        Map<String, String> res2 = new HashMap<>();
        res2.put("status", "pending");
        assertEquals(res2, adminService.pendingAddFund(addfund));
    }

    @Test
    void pendingWithdrawFund() {
        Map<String, String> user = new HashMap<>();
        user.put("email", "ram@gmail.com");
        user.put("amount_left", "100");
        Map<String, String> res1 = new HashMap<>();
        res1.put("status", "pending");
        sam.setAmount_left(50000);
        when(userRepository.findByEmail(user.get("email"))).thenReturn(sam);
        assertEquals(res1, adminService.pendingWithdrawFund(user));
    }

    @Test
    void pendingWithdrawFund2() {
        Map<String, String> user = new HashMap<>();
        user.put("email", "ram@gmail.com");
        user.put("amount_left", "100");
        Map<String, String> res1 = new HashMap<>();
        res1.put("status", "invalid");

        when(userRepository.findByEmail(user.get("email"))).thenReturn(sam);
        assertEquals(res1, adminService.pendingWithdrawFund(user));
    }


    @Test
    void pendingBuy() {

        //arrange
        Map<String, String> userInput = new HashMap<>();
        userInput.put("email", "sam@gmail.com");
        userInput.put("companyId", "52");
        userInput.put("quantity", "2");

        Map<String, String> result = new HashMap<>();
        result.put("status", "Pending");

        sam.setAmount_left(50000);
        when(companyRepository.findById(52)).thenReturn(java.util.Optional.ofNullable(tcs));
        when(userRepository.findByEmail("sam@gmail.com")).thenReturn(sam);
        //assert
        assertEquals(result, adminService.pendingBuy(userInput));


    }

    @Test
    void pendingSell(){
        Map<String, String> userInput = new HashMap<>();
        userInput.put("email", "sam@gmail.com");
        userInput.put("companyId", "52");
        userInput.put("quantity", "2");

        Map<String, String> result = new HashMap<>();
        result.put("status", "Pending");

        sam.setAmount_left(50000);
        when(companyRepository.findById(52)).thenReturn(java.util.Optional.ofNullable(tcs));
        when(userRepository.findByEmail("sam@gmail.com")).thenReturn(sam);
        //assert
        assertEquals(result, adminService.pendingBuy(userInput));

    }

}