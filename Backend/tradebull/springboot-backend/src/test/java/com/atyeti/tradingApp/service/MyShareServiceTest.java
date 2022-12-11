package com.atyeti.tradingApp.service;

import com.atyeti.tradingApp.models.CompanyModel;
import com.atyeti.tradingApp.models.HistoryModel;
import com.atyeti.tradingApp.models.MySharesModel;
import com.atyeti.tradingApp.models.UserModel;
import com.atyeti.tradingApp.repository.CompanyRepository;
import com.atyeti.tradingApp.repository.HistoryRepository;
import com.atyeti.tradingApp.repository.MySharesRepository;
import com.atyeti.tradingApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {MyShareService.class})
@ExtendWith(SpringExtension.class)
class MyShareServiceTest {

    @Autowired
    MyShareService myShareService;
    @MockBean
    HistoryService historyService;
    @MockBean
    UserService userService;


    @MockBean
    MySharesRepository mySharesRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    CompanyRepository companyRepository;
    @MockBean
    HistoryRepository historyRepository;


    CompanyModel tcs = new CompanyModel("TCS", 100, 150, 200, 100,
            160, 1, 80, 300, 232, 23, 200);
    CompanyModel ibm = new CompanyModel("IBM", 100, 150, 200, 100,
            160, 2, 80, 300, 232, 23, 25);

    UserModel ram = new UserModel("ram", "ram@gmail.com", "12345678", "1234567890");
    UserModel sam = new UserModel("sam", "sam@gmail.com", "12345678", "1234567890");
    UserModel admin1 = new UserModel("admin", "abc@atyeti.com", "abc@123", "1234567890");

    MySharesModel share = new MySharesModel(1, "TCS", "ram@gmail.com", 100,
            150, 200, 100, 160, 1, 80, 300, 232,
            23, 10, 21, "2020-03-01", "21:19:20");
    MySharesModel newShare = new MySharesModel(2, "IBM", "ram@gmail.com", 100,
            150, 200, 100, 160, 1, 80, 300, 232,
            23, 10, 21, "2020-03-01", "21:19:20");
    MySharesModel share2 = new MySharesModel(2, "IBM", "sam@gmail.com", 100,
            150, 200, 100, 160, 1, 80, 300, 232,
            23, 10, 21, "2020-03-01", "21:19:20");

    HistoryModel data = new HistoryModel(1, "ram@gmail.com", 1, "IBM",
            120, 1, "2020-03-01", "21:19:20", "Buy", "Pending");

    HistoryModel data1 = new HistoryModel(3, "shyam@gmail.com", 1, "TCS",
            120, 1, "2020-03-01", "21:19:20", "Buy", "Pending");

    int pendingRequestNO;

    @Test
    void myShare0() {
        ArrayList<MySharesModel> myshare = new ArrayList<>();
        myshare.add(share);
        myshare.add(share2);
        when(this.mySharesRepository.findAll()).thenReturn(myshare);
        assertEquals(0, this.myShareService.myShare("r12am@gmail.com").size());
    }

    @Test
    void myShare2() {
        ArrayList<MySharesModel> myshare = new ArrayList<>();
        myshare.add(share);
        myshare.add(share2);
        when(this.mySharesRepository.findAll()).thenReturn(myshare);
        assertEquals(1, this.myShareService.myShare("ram@gmail.com").size());
    }

    //buy if share is existing
    @Test
    void buy() {
        Map<String, String> request1 = new HashMap<>();
        request1.put("email", "ram@gmail.com");
        request1.put("companyId", "1");
        request1.put("quantity", "2");
        request1.put("index", "12");


        data.setIndex(12);
        data.setIndex(13);
        ram.setAmount_left(267378);
        sam.setAmount_left(26378);

        List<UserModel> user = new ArrayList<UserModel>();
        user.add(ram);
        user.add(sam);

        List<CompanyModel> company = new ArrayList<CompanyModel>();
        company.add(tcs);
        company.add(ibm);

        ArrayList<MySharesModel> myshare = new ArrayList<>();
        myshare.add(share);
        myshare.add(share2);

        ArrayList<HistoryModel> History = new ArrayList<>();
        History.add(data);
        History.add(data1);

        List<HistoryModel> request = new ArrayList<HistoryModel>();
        request.add(data);
        request.add(data1);

        List<UserModel> admin = new ArrayList<UserModel>();
        admin.add(admin1);

        HashMap<String, String> response = new HashMap<>();
        response.put("status", "Success");

        when(mySharesRepository.findAll()).thenReturn(myshare);
        when(userRepository.findByEmail("ram@gmail.com")).thenReturn(ram);
        when(userService.getOne("abc@atyeti.com")).thenReturn(admin);

        when(companyRepository.findById(tcs.getCompany_id())).thenReturn(java.util.Optional.ofNullable(tcs));
        when(historyRepository.findById(12)).thenReturn(java.util.Optional.ofNullable(data));
        when(historyService.admintransactionHistroy()).thenReturn(History);



        assertEquals(response, myShareService.buy(request1));
    }

    //if share is not exist
    @Test
    void buy2() {
        Map<String, String> request = new HashMap<>();
        request.put("email", "ram@gmail.com");
        request.put("companyId", "2");
        request.put("quantity", "2");
        request.put("index", "12");

        ram.setAmount_left(267378);
        sam.setAmount_left(26378);

        List<UserModel> admin = new ArrayList<UserModel>();
        admin.add(admin1);

        ArrayList<HistoryModel> History = new ArrayList<>();
        History.add(data);
        History.add(data1);

        HashMap<String, String> response = new HashMap<>();
        response.put("status", "Success");

        when(userRepository.findByEmail("ram@gmail.com")).thenReturn(ram);
        when(userService.getOne("abc@atyeti.com")).thenReturn(admin);
        when(historyService.admintransactionHistroy()).thenReturn(History);
        when(historyRepository.findById(12)).thenReturn(java.util.Optional.ofNullable(data));

        when(companyRepository.findById(2)).thenReturn(java.util.Optional.ofNullable(ibm));
        when(mySharesRepository.findAll()).thenReturn(Arrays.asList(share2, share)).thenReturn(Arrays.asList(share, share2, newShare));

        assertEquals(response, myShareService.buy(request));
    }


    @Test
    void sell() {

        Map<String, String> userInput = new HashMap<>();
        userInput.put("email", "ram@gmail.com");
        userInput.put("companyId", "1");
        userInput.put("quantity", "2");
        userInput.put("index", "1");
        List<UserModel> user = new ArrayList<UserModel>();
        user.add(ram);
        user.add(sam);

        List<CompanyModel> company = new ArrayList<CompanyModel>();
        company.add(tcs);
        company.add(ibm);

        ArrayList<HistoryModel> History = new ArrayList<>();
        History.add(data);
        History.add(data1);

        ArrayList<MySharesModel> myshare = new ArrayList<>();
        myshare.add(share);
        myshare.add(share2);

        List<UserModel> admin = new ArrayList<UserModel>();
        admin.add(admin1);

        HashMap<String, String> response = new HashMap<>();
        response.put("status", "Success");

        when(mySharesRepository.findAll()).thenReturn(myshare);
        when(userService.getOne("abc@atyeti.com")).thenReturn(admin);
        when(userRepository.findAll()).thenReturn(user);
        when(companyRepository.findAll()).thenReturn(company);
        when(historyRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(data1));
        when(historyService.admintransactionHistroy()).thenReturn(History);

        assertEquals(response, myShareService.sell(userInput));
    }
}