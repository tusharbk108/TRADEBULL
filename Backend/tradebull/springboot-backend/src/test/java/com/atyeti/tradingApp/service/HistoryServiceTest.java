package com.atyeti.tradingApp.service;

import static junit.framework.TestCase.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.atyeti.tradingApp.models.HistoryModel;
import com.atyeti.tradingApp.repository.HistoryRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {HistoryService.class})
@ExtendWith(SpringExtension.class)
class HistoryServiceTest {

    HistoryModel ram = new HistoryModel(1, "ram@gmail.com", 1, "IBM",
            120, 1, "2020-03-01", "21:19:20", "Buy", "Pending");
    HistoryModel sam = new HistoryModel(1, "sam@gmail.com", 1, "IBM",
            120, 1, "2020-03-01", "21:19:20", "Buy", "Pending");
    @MockBean
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryService historyService;


    //Valid User
    @Test
    void transactionHistory() {
        List<HistoryModel> allHistoryModel = new ArrayList<HistoryModel>();
        ram.setStatus("success");
        sam.setStatus("success");
        allHistoryModel.add(ram);
        allHistoryModel.add(sam);
        when(historyRepository.findAll()).thenReturn(allHistoryModel);

        assertEquals(1, historyService.transactionHistory("ram@gmail.com").size());
    }

    //Invalid User
    @Test
    void transactionHistory2() {
        List<HistoryModel> allHistoryModel = new ArrayList<HistoryModel>();
        ram.setStatus("success");
        sam.setStatus("success");
        allHistoryModel.add(ram);
        allHistoryModel.add(sam);
        when(historyRepository.findAll()).thenReturn(allHistoryModel);

        assertTrue(historyService.transactionHistory("1234@gmail.com").isEmpty());
    }

    @Test
    void transactionHistory3() {
        List<HistoryModel> allHistoryModel = new ArrayList<HistoryModel>();
        ram.setStatus("success");
        sam.setStatus("success");
        allHistoryModel.add(ram);
        allHistoryModel.add(sam);
        when(historyRepository.findAll()).thenReturn(allHistoryModel);

        assertEquals("ram@gmail.com", historyService.transactionHistory("ram@gmail.com").get(0).getUser_id());
    }

    @Test
    void admintransactionHistroy() {

        when(historyRepository.findAll()).thenReturn(Arrays.asList(ram, sam));
        assertEquals(2,historyService.admintransactionHistroy().size());
    }

    @Test
    void pendingHistory() {


        when(historyRepository.findAll()).thenReturn(Arrays.asList(ram, sam));
        assertEquals(1,historyService.pendingHistory("sam@gmail.com").size());
    }
}


