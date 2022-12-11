package com.atyeti.tradingApp.service;

import com.atyeti.tradingApp.models.CompanyModel;
import com.atyeti.tradingApp.models.HistoryModel;
import com.atyeti.tradingApp.models.UserModel;
import com.atyeti.tradingApp.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    @Autowired
    HistoryRepository historyRepository;

    public List<HistoryModel> transactionHistory(String email) {

        List<HistoryModel> myHistoryModel = null;
        try {
            myHistoryModel = historyRepository.findAll().stream()
                    .filter(data -> data.getUser_id().equalsIgnoreCase(email) &&
                            data.getStatus().equalsIgnoreCase("Success")).collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println(e);
        }

        Collections.reverse(myHistoryModel);
        return myHistoryModel;

    }

    public List<HistoryModel> admintransactionHistroy() {
        List<HistoryModel> historyModel = historyRepository.findAll();
        List<HistoryModel> history = new ArrayList<HistoryModel>();

        Iterator<HistoryModel> iter = historyModel.iterator();
        while (iter.hasNext()) {
            HistoryModel h = (HistoryModel) iter.next();
            if (h.getStatus().equals("Pending"))
                history.add(h);
        }
        Collections.reverse(history);
        return history;
    }


    public List<HistoryModel> pendingHistory(String email) {
        List<HistoryModel> myHistoryModel = null;
        try {
            myHistoryModel = historyRepository.findAll().stream()
                    .filter(data -> data.getUser_id().equalsIgnoreCase(email) &&
                            (data.getStatus().equalsIgnoreCase("Pending") || data.getStatus().equalsIgnoreCase("Rejected")))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println(e);
        }

        Collections.reverse(myHistoryModel);
        return myHistoryModel;
    }

    public List<HistoryModel> pendingHistoryUSer(String email) {
        List<HistoryModel> myHistoryModel = null;
        try {
            myHistoryModel = historyRepository.findAll().stream()
                    .filter(data -> (data.getUser_id().equalsIgnoreCase(email)) &&
                            (data.getStatus().equalsIgnoreCase("Pending") ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println(e);
        }

        Collections.reverse(myHistoryModel);
        return myHistoryModel;
    }
}
