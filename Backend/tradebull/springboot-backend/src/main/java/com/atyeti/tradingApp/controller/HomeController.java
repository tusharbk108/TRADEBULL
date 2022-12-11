package com.atyeti.tradingApp.controller;

import com.atyeti.tradingApp.models.*;
import com.atyeti.tradingApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@CrossOrigin(origins = "*")
@RestController
public class HomeController {


    @Autowired
    private UserService userService;

    @Autowired
    private MyShareService myShareService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private AdminService adminService;


    @PostMapping("/signin")
    public Map<String, String> login(@RequestBody Map<String, String> userInput) {

        return userService.login(userInput);

    }

    @GetMapping("/remove-watchlist")
    public Map<String, String> removeWatchList(@RequestParam String email, @RequestParam int id) {
        return watchListService.removeWatchList(email, id);
    }

    @PostMapping("/forgot")
    public Map<String, String> forgot(@RequestBody Map<String, String> userInput) {

        return userService.forgot(userInput);

    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> userInputs) {

        return userService.register(userInputs);
    }


    @GetMapping("/companysearch")
    public List<CompanyModel> search() {
        return companyService.search();
    }

    @GetMapping("/getAllclient")
    public List<UserModel> getall() {
        return userService.getallclient();
    }

    @GetMapping("/get-one")
    public Optional<CompanyModel> getOne(@RequestParam int id) {

        return companyService.getOne(id);
    }

    @GetMapping("/my-share")
    public List<MySharesModel> myShare(@RequestParam("email") String email) {
        return myShareService.myShare(email);
    }

    @GetMapping("/get-user")
    public List<UserModel> getOne(@RequestParam String email) {
        return userService.getOne(email);
    }

    //Admin Approve
    @PostMapping("/approveBuy")
    public Map<String, String> adminApprove(@RequestBody Map<String, Integer> payload) {

        return myShareService.adminApprove(payload);
    }

    //Admin Reject
    @PostMapping("/rejectOrder")
    public Map<String, String> rejectOrder(@RequestBody Map<String, Integer> payload) {

        return adminService.rejectOrder(payload);
    }

    @PostMapping("/buy")
    public Map<String, String> buy(@RequestBody Map<String, String> payload) {

        return myShareService.buy(payload);
    }

    //USer Pending Buy
    @PostMapping("/pendingBuy")
    public Map<String, String> pendingBuy(@RequestBody Map<String, String> payload) {

        return adminService.pendingBuy(payload);
    }

    //User Pending Sell
    @PostMapping("/pendingSell")
    public Map<String, String> pendingSell(@RequestBody Map<String, String> payload) {

        return adminService.pendingSell(payload);
    }

    @GetMapping("/history")
    public List<HistoryModel> transactionHistroy(@RequestParam("email") String email) {
        return historyService.transactionHistory(email);
    }

    @GetMapping("/pendingHistory")
    public List<HistoryModel> pendingHistory(@RequestParam("email") String email) {
        return historyService.pendingHistory(email);
    }

    @GetMapping("/pendingHistoryUser")
    public List<HistoryModel> pendingHistoryUser(@RequestParam("email") String email) {
        return historyService.pendingHistoryUSer(email);
    }

    //Admin pending Order
    @GetMapping("/adminHistory")
    public List<HistoryModel> admintransactionHistroy() {
        return historyService.admintransactionHistroy();
    }

    @GetMapping("/watch-list")
    public List<WatchListModel> watchList(@RequestParam("email") String email) {
        return watchListService.watchList(email);


    }

    @GetMapping("/add-watchlist")
    public Map<String, String> addWatchList(String email, int id) {
        return watchListService.addWatchList(email, id);
    }

    @PostMapping("/sell")
    public Map<String, String> sell(@RequestBody Map<String, String> payload) {
        return myShareService.sell(payload);
    }

    @DeleteMapping("/delete")
    public Map<String, String> deleteCompany(@RequestParam("company_id") int company_id) {
        return companyService.deleteCompany(company_id);
    }

    @PostMapping("/addCompany")
    public Map<String, String> addCompany(@RequestBody() CompanyModel companyModel) {
        return companyService.addCompany(companyModel);
    }


    @PutMapping("/updateCompany/{company_id}")
    public ResponseEntity<CompanyModel> updateCompany(@PathVariable int company_id, @RequestBody CompanyModel company1) {

        return companyService.updateCompany(company_id, company1);
    }

    @GetMapping("/getCompanyById")
    public ResponseEntity<CompanyModel> getCompany(@RequestParam("id") int company_id) {
        System.out.println(company_id);
        return companyService.getCompanyById(company_id);

    }

    @PostMapping("/add-fund")
    public Map<String, String> addfund(@RequestBody Map<String, String> payload) {

        return adminService.pendingAddFund(payload);
    }

    @PostMapping("/withdraw")
    public Map<String, String> pendingWithdraw(@RequestBody Map<String, String> payload) {

        return adminService.pendingWithdrawFund(payload);
    }

    @GetMapping("admin/approve/fund")
    public Map<String, String> approveWithdraw(@RequestParam("sno") Integer sno) {

        return adminService.approveFund(sno);
    }

    @GetMapping("admin/pending/fund")
    public List<AdminModel> pendingOrders() {

        return adminService.getPendingRequest();
    }

    @GetMapping("admin/reject/fund")
    public Map<String, String> rejectedorders(@RequestParam("sno") Integer sno) {
        return adminService.rejectedFund(sno);
    }

    @GetMapping("user/order/fund")
    public List<AdminModel> userPending(@RequestParam("email") String email) {
        return adminService.userFundOrders(email);
    }

}


