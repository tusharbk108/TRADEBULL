package com.atyeti.tradingApp.service;

import com.atyeti.tradingApp.models.UserModel;
import com.atyeti.tradingApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    UserModel ram = new UserModel("ram", "ram@gmail.com", "$2a$10$LvxVOEBsdDZvE42ay10FUuw4V.xHEcfXCVp1f/QsEgv0269NAJ9/i", "1234567890");
    UserModel sam = new UserModel("sam", "sam@gmail.com", "12345678", "1234567890");

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @MockBean
    PasswordEncoder passwordEncoder;

    //valid user
    @Test
    void login() {
        Map<String, String> userInput = new HashMap<>();
        userInput.put("email", "ram@gmail.com");
        userInput.put("password", "12345678");
        when(userRepository.findByEmail("ram@gmail.com")).thenReturn(ram);
        when(passwordEncoder.matches("12345678",ram.getPassword())).thenReturn(true);
        Map<String, String> res = new HashMap<>();
        res.put("status", "success");

        assertEquals(res, userService.login(userInput));
    }

    @Test
    void register() {

        Map<String, String> input = new HashMap<>();

        input.put("email", "ram@gmail.com");
        input.put("password", "12345678");
        input.put("phone", "1234567890");
        input.put("name", "ram");

        Map<String, String> res = new HashMap<>();
        res.put("status", "success");

        assertEquals(res, userService.register(input));


    }

    @Test
    void forgot() {
        Map<String, String> userInput = new HashMap<>();
        userInput.put("password", "1235678");
        userInput.put("email", "ram@gmail.com");
        userInput.put("username", "ram");

        when(userRepository.findAll()).thenReturn(Arrays.asList(ram, sam));

        Map<String, String> res = new HashMap<>();
        res.put("status", "success");

        assertEquals(res, userService.forgot(userInput));
    }

    //if mail id is wrong
    @Test
    void forgot2() {
        Map<String, String> userInput = new HashMap<>();
        userInput.put("password", "1235678");
        userInput.put("email", "ram1@gmail.com");
        userInput.put("username", "ram");

        when(userRepository.findAll()).thenReturn(Arrays.asList(ram, sam));

        Map<String, String> res = new HashMap<>();
        res.put("status", "uncaugh error");

        assertEquals(res, userService.forgot(userInput));
    }

    //invalid password
    @Test
    void login2() {
        Map<String, String> userInput = new HashMap<>();
        userInput.put("email", "ram@gmail.com");
        userInput.put("password", "1234567812");
        when(userRepository.findByEmail("ram@gmail.com")).thenReturn(ram);

        Map<String, String> res = new HashMap<>();
        res.put("status", "Incorrect credentials");

        assertEquals(res, userService.login(userInput));
    }

    //invalid user
    @Test
    void login3() {
        Map<String, String> userInput = new HashMap<>();
        userInput.put("email", "ram1@gmail.com");
        userInput.put("password", "12345678");
        when(userRepository.findByEmail("ram@gmail.com")).thenReturn(ram);

        Map<String, String> res = new HashMap<>();
        res.put("status", "User doesn't exist");

        assertEquals(res, userService.login(userInput));
    }

    @Test
    void getOne() {
        String email = "ram@gmail.com";
        List<UserModel> user = new ArrayList<UserModel>();
        user.add(ram);
        user.add(sam);
        when(userRepository.findAll()).thenReturn(user);

        assertEquals(ram.getEmail(), userService.getOne("ram@gmail.com").get(0).getEmail());

    }

    @Test
    void getOneWithOutExceptionCode() {
        String email = "ram@gmail.com";
        List<UserModel> user = new ArrayList<UserModel>();
        user.add(ram);
        user.add(sam);
        when(userRepository.findAll()).thenReturn(user);

        assertEquals("[]", userService.getOne("ram11@gmail.com").toString());

    }

    @Test
    void getOneExceptionCode() {
        String email = "ram@gmail.com";
        List<UserModel> user = new ArrayList<UserModel>();
        user.add(ram);
        user.add(sam);
        when(userRepository.findAll()).thenReturn(null);

        assertEquals("[]", userService.getOne("ram11@gmail.com").toString());

    }

    @Test
    void withdraw() {
        Map<String, String> userInputs = new HashMap<>();
        userInputs.put("email", "ram@gmail.com");
        userInputs.put("amount_left", "25000");
        HashMap<String, String> res = new HashMap<>();
        res.put("status", "success");
        ram.setAmount_left(50000);
        when(userRepository.findByEmail("ram@gmail.com")).thenReturn(ram);
        assertEquals(res, userService.withdraw(userInputs));
        assertEquals(25000, ram.getAmount_left());

    }

    @Test
    void addFund() {
        Map<String, String> userInputs = new HashMap<>();
        userInputs.put("email", "ram@gmail.com");
        userInputs.put("amount_left", "25000");
        HashMap<String, String> res = new HashMap<>();
        res.put("status", "success");
        ram.setAmount_left(50000);
        when(userRepository.findByEmail("ram@gmail.com")).thenReturn(ram);
        assertEquals(res, userService.addfund(userInputs));
        assertEquals(75000, ram.getAmount_left());

    }

    @Test
    void getallclient() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(ram, sam));
        assertEquals(2, userService.getallclient().size());
    }
}