package com.atyeti.tradingApp.service;

import com.atyeti.tradingApp.models.*;
import com.atyeti.tradingApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private MyShareService myShareService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private MySharesRepository mySharesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;


    public Map<String, String> pendingWithdrawFund(Map<String, String> userInput) {

        UserModel data1 = userRepository.findByEmail(userInput.get("email"));
        Map<String, String> res = new HashMap<>();
        int amt = Integer.parseInt(userInput.get("amount_left"));

        int originalamt = data1.getAmount_left();

        if (amt > originalamt) {
            res.put("status", "invalid");
            return res;
        } else {
            AdminModel data = new AdminModel();
            data.setAmount(Integer.parseInt(userInput.get("amount_left")));
            data.setUserid(userInput.get("email"));
            data.setDateTime(myShareService.getDate() + " " + myShareService.getTime());
            data.setStatus("pending");
            data.setType("withdrawFund");
            adminRepository.save(data);
            res.put("status", "pending");
            return res;

        }
    }

    public Map<String, String> approveFund(Integer sno) {

        AdminModel data = adminRepository.findById(sno).orElseThrow(()
                -> new RuntimeException("record not found" + sno));
        String email = data.getUserid();
        UserModel data1 = userRepository.findByEmail(email);
        Map<String, String> res1 = new HashMap<>();
        if (data.getAmount() > data1.getAmount_left() && data.getType()
                .equalsIgnoreCase("withdrawFund")) {
            res1.put("status", "invalid");
            return res1;
        } else {
            Map<String, String> approve = new HashMap<>();
            approve.put("email", data.getUserid());
            approve.put("amount_left", data.getAmount() + "");
            data.setStatus("approved");
            if (data.getType().equalsIgnoreCase("withdrawFund")) {
                adminRepository.save(data);
                Map<String, String> res = userService.withdraw(approve);
                return res;
            } else {
                adminRepository.save(data);
                Map<String, String> res = userService.addfund(approve);
                return res;
            }
        }
    }

    public List<AdminModel> getPendingRequest() {
        List<AdminModel> allOrders = adminRepository.findAll();
        List<AdminModel> pendingOrders = null;
        Iterator i = allOrders.iterator();
        try {
            pendingOrders = adminRepository.findAll().stream().filter(data -> data.getStatus()
                    .equalsIgnoreCase("pending") &&
                    data.getType().equalsIgnoreCase("addFund") || data.getStatus()
                    .equalsIgnoreCase("pending") && data.getType()
                    .equalsIgnoreCase("withdrawFund")).collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println(e);
        }
        return pendingOrders;


    }

    public List<AdminModel> userFundOrders(String userId) {
        List<AdminModel> orders = null;

        try {
            orders = adminRepository.findAll().stream().filter(data -> data.getUserid().equalsIgnoreCase(userId) &&
                    data.getType().equalsIgnoreCase("addFund") || data.getUserid()
                    .equalsIgnoreCase(userId) && data.getType()
                    .equalsIgnoreCase("withdrawFund")).collect(Collectors.toList());

        } catch (Exception e) {

        }
        return orders;
    }

    //    add funds
    public Map<String, String> pendingAddFund(Map<String, String> userInput) {

        Map<String, String> res = new HashMap<>();
        AdminModel data = new AdminModel();
        data.setAmount(Integer.parseInt(userInput.get("amount_left")));
        data.setUserid(userInput.get("email"));
        data.setDateTime(myShareService.getDate() + " " + myShareService.getTime());
        data.setStatus("pending");
        data.setType("addFund");
        adminRepository.save(data);
        res.put("status", "pending");
        return res;


    }


    public Map<String, String> pendingBuy(Map<String, String> userInput) {
        String email = (String) userInput.get("email");
        int companyId = Integer.parseInt(userInput.get("companyId").toString());
        int qty = Integer.parseInt(userInput.get("quantity").toString());

        String date = myShareService.getDate();
        String time = myShareService.getTime();

        HashMap<String, String> map = new HashMap<>();


        try {
            UserModel userModel = userRepository.findByEmail(email);
            CompanyModel companyModel = companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("Company not Found"));




            if (companyModel.getVolume() < qty) {
                map.put("status", "insufficient quantity");
                return map;
            }else if (userModel.getAmount_left() <= (qty * companyModel.getCurrent_rate())) {
                map.put("status", "insufficient balance");
                return map;
            }else{
                HistoryModel history = new HistoryModel(1, email, companyId,
                        companyModel.getName(), -companyModel.getCurrent_rate() * qty, qty, date, time, "Buy", "Pending");

                historyRepository.save(history);
                map.put("status", "Pending");
                return map;
            }


        } catch (Exception e) {
            map.put("status", e.getMessage());
            return map;
        }

    }

    public Map<String, String> pendingSell(Map<String, String> userInput) {
        String email = (String) userInput.get("email");
        int companyId = Integer.parseInt(userInput.get("companyId").toString());
        int qty = Integer.parseInt(userInput.get("quantity").toString());

        String date = myShareService.getDate();
        String time = myShareService.getTime();

        HashMap<String, String> map = new HashMap<>();


        try {
            UserModel userModel = userRepository.findByEmail(email);
            CompanyModel companyModel = companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("Company not Found"));


            //(int sno, String user_id, int company_id, String company_name, int price, int quantity, String date,
            //        String time, String type,String status)

            HistoryModel history = new HistoryModel(1, email, companyId,
                    companyModel.getName(), companyModel.getCurrent_rate() * qty, qty, date, time, "Sell", "Pending");

            historyRepository.save(history);


            map.put("status", "Pending");
            return map;
        } catch (Exception e) {
            map.put("status", e.getMessage());
            return map;
        }
    }

    public Map<String, String> rejectOrder(Map<String, Integer> userInput) {
        int index = userInput.get("index");
        HistoryModel history = historyRepository.findById(index).orElseThrow(() -> new RuntimeException("Company not Found"));

        HashMap<String, String> map = new HashMap<>();

        history.setStatus("Rejected");
        historyRepository.save(history);

        map.put("status", "Rejected");
        return map;

    }


    //reject funds

    public Map<String, String> rejectedFund(Integer sno) {
        AdminModel data = adminRepository.findById(sno).orElseThrow(()
                -> new RuntimeException("record not found" + sno));

        data.setStatus("rejected");
        adminRepository.save(data);
        Map<String, String> res = new HashMap<>();
        res.put("status", "rejected");
        return res;

    }


}
