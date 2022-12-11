package com.atyeti.tradingApp.service;

import com.atyeti.tradingApp.models.AdminModel;
import com.atyeti.tradingApp.models.HistoryModel;
import com.atyeti.tradingApp.models.UserModel;
import com.atyeti.tradingApp.repository.UserRepository;
import org.sonatype.plexus.components.sec.dispatcher.PasswordDecryptor;
import org.sonatype.plexus.components.sec.dispatcher.SecDispatcherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.stream.Collectors;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Map<String, String> register(Map<String, String> userInput) {

        Map<String, String> response = new HashMap<>();
        String email = userInput.get("email");

        String password = this.passwordEncoder.encode(userInput.get("password"));

        String phone = userInput.get("phone");
        String name = userInput.get("name");
        UserModel userModel = new UserModel(name, email, password, phone);
        userRepository.save(userModel);
        response.put("status", "success");
        return response;

    }

    public HashMap<String, String> forgot(Map<String, String> payload) {
        HashMap<String, String> map = new HashMap<>();
        List<UserModel> users = (List<UserModel>) userRepository.findAll();

        try {
            Iterator<UserModel> iter = users.iterator();
            while (iter.hasNext()) {
                UserModel us = (UserModel) iter.next();
                if (payload.get("email").equals("" + us.getEmail())) {
                    if (us.getName().equals(payload.get("username"))) {

                        String password = this.passwordEncoder.encode(payload.get("password"));

                        UserModel user = new UserModel(us.getName(), us.getEmail(), password, us.getPhone());
                        userRepository.save(user);
                        map.put("status", "success");
                        return map;
                    } else {
                        map.put("status", "failure");
                        return map;
                    }
                }
            }
        } catch (Exception e) {
            map.put("status", e.getMessage());
            return map;
        }

        map.put("status", "uncaugh error");
        return map;
    }

    public Map<String, String> login(Map<String, String> userInput) {
        String email = (String) userInput.get("email");
        String password = userInput.get("password");
        Map<String, String> response = new HashMap<String, String>();
        try {
            UserModel userModel = userRepository.findByEmail(email);
            if (userModel.getEmail().equals(email)) {


                if (passwordEncoder.matches(password, userModel.getPassword())) {

                    response.put("status", "success");
                    return response;
                } else {
                    response.put("status", "Incorrect credentials");
                }
            }

        } catch (Exception e) {
            response.put("status", "User doesn't exist");
            return response;
        }

        return response;
    }


    public List<UserModel> getOne(String email) {
        List<UserModel> users = (List<UserModel>) userRepository.findAll();
        List<UserModel> user = new ArrayList<UserModel>();
        try {
            Iterator<UserModel> iter = users.iterator();
            while (iter.hasNext()) {
                UserModel us = (UserModel) iter.next();
                if (email.equals(us.getEmail())) {
                    user.add(us);
                }
            }
        } catch (Exception e) {
            System.out.print("Exception : ");
            System.out.print(e.getMessage());
        }
        return user;
    }

    public Map<String, String> addfund(Map<String, String> userInput) {
        String email = (String) userInput.get("email");
        int qty = Integer.parseInt(userInput.get("amount_left").toString());
        HashMap<String, String> map = new HashMap<>();
        UserModel userModel = userRepository.findByEmail(email);
        int userAmount = userModel.getAmount_left();
        int currentAmount = userAmount + qty;
        userModel.setAmount_left(currentAmount);
        userRepository.save(userModel);
        map.put("status", "success");
        return map;
    }

    public Map<String, String> withdraw(Map<String, String> userInput) {
        String email = (String) userInput.get("email");
        int qty = Integer.parseInt(userInput.get("amount_left").toString());
        HashMap<String, String> map = new HashMap<>();
        UserModel userModel = userRepository.findByEmail(email);
        int userAmount = userModel.getAmount_left();
        int currentAmount = userAmount - qty;
        userModel.setAmount_left(currentAmount);
        userRepository.save(userModel);
        map.put("status", "success");
        return map;
    }

    public List<UserModel> getallclient() {
        List<UserModel> allOrders = (List<UserModel>) userRepository.findAll();

        List<UserModel> users = new ArrayList<UserModel>();
        Iterator<UserModel> item1 = allOrders.iterator();
        while (item1.hasNext()) {
            UserModel ms = (UserModel) item1.next();
            if (!("abc@atyeti.com".equals(ms.getEmail()))) {

                users.add(ms);
            }
        }


        return  users;

    }


}


