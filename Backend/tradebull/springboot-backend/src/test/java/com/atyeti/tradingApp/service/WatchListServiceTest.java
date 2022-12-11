package com.atyeti.tradingApp.service;

import com.atyeti.tradingApp.models.CompanyModel;
import com.atyeti.tradingApp.models.UserModel;
import com.atyeti.tradingApp.models.WatchListModel;
import com.atyeti.tradingApp.repository.CompanyRepository;
import com.atyeti.tradingApp.repository.WatchListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {WatchListService.class})
@ExtendWith(SpringExtension.class)
class WatchListServiceTest {

    WatchListModel ramWatch = new WatchListModel(1, "TCS", "ram@gmail.com", 150, 200, 100,
            160, 1, 80, 300, 232, 23, 20);
    WatchListModel samWatch = new WatchListModel(2, "IBM", "sam@gmail.com", 150, 200, 100,
            160, 2, 80, 300, 232, 23, 25);


    CompanyModel tcs = new CompanyModel("TCS", 100, 150, 200, 100,
            160, 1, 80, 300, 232, 23, 20);
    CompanyModel ibm = new CompanyModel("IBM", 100, 150, 200, 100,
            160, 2, 80, 300, 232, 23, 25);

    UserModel ram = new UserModel("ram", "ram@gmail.com", "12345678", "1234567890");
    UserModel sam = new UserModel("sam", "sam@gmail.com", "12345678", "1234567890");

    @Autowired
    private WatchListService watchListService;

    @MockBean
    private WatchListRepository watchListRepository;

    @MockBean
    private CompanyRepository companyRepository;

    @Test
    void watchList() {
        List<WatchListModel> allWatchList = new ArrayList<>();
        allWatchList.add(ramWatch);
        allWatchList.add(samWatch);

        when(watchListRepository.findAll()).thenReturn(allWatchList);
        assertEquals(samWatch, watchListService.watchList("sam@gmail.com").get(0));


    }


    @Test//if company exist
    void addWatchList() {
        Map<String,String> res = new HashMap<>();
        res.put("status", "company exist");
        when(companyRepository.findAll()).thenReturn(Arrays.asList(tcs, ibm));
        when(watchListRepository.findAll()).thenReturn(Arrays.asList(ramWatch,samWatch));

        assertEquals(res,watchListService.addWatchList("ram@gmail.com",1));

    }

    @Test////if company not exist
    void addWatchList2() {
        Map<String,String> res = new HashMap<>();
        res.put("status", "success");
        when(companyRepository.findAll()).thenReturn(Arrays.asList(tcs, ibm));
        when(watchListRepository.findAll()).thenReturn(Arrays.asList(ramWatch,samWatch));

        assertEquals(res,watchListService.addWatchList("ram@gmail.com",2));

    }
    // data is saved into watch list table even though user does not exist test case is failing
    void addWatchList3() {
        Map<String,String> res = new HashMap<>();
        res.put("status", "error");
        when(companyRepository.findAll()).thenReturn(Arrays.asList());
        when(watchListRepository.findAll()).thenReturn(Arrays.asList());

        assertEquals(res,watchListService.addWatchList("ram@gmail.com",2));

    }



    @Test
    void removeWatchList() {
        Map<String,String> res = new HashMap<>();
        res.put("status", "success");

        when(companyRepository.findAll()).thenReturn(Arrays.asList(tcs, ibm));
        when(watchListRepository.findAll()).thenReturn(Arrays.asList(ramWatch,samWatch));
        assertEquals(res,watchListService.removeWatchList("ram@gmail.com",1));

    }

    @Test
    void removeWatchList2() {
        Map<String,String> res = new HashMap<>();
        res.put("status", "success");

        when(companyRepository.findAll()).thenReturn(Arrays.asList(tcs, ibm));
        when(watchListRepository.findAll()).thenReturn(Arrays.asList(samWatch));
        assertEquals(res,watchListService.removeWatchList("ram@gmail.com",1));

    }
}