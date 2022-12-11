package com.atyeti.tradingApp.service;

import com.atyeti.tradingApp.models.CompanyModel;
import com.atyeti.tradingApp.repository.CompanyRepository;
import com.atyeti.tradingApp.repository.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {CompanyService.class})
@ExtendWith(SpringExtension.class)
class CompanyServiceTest {

    @MockBean
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @MockBean
    private HistoryRepository historyRepository;

    CompanyModel tcs = new CompanyModel("TCS", 100, 150, 200, 100,
            160, 1, 80, 300, 232, 23, 20);

    CompanyModel tcs2= new CompanyModel("TCS", 100, 150, 200, 100,
            2, 1, 80, 300, 232, 23, 20);
    CompanyModel ibm = new CompanyModel("IBM", 100, 150, 200, 100,
            160, 2, 80, 300, 232, 23, 25);


    @Test
    void getOne() {
        List<CompanyModel> company = new ArrayList<>();
        company.add(tcs);
        company.add(ibm);
        when(companyRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(tcs));
        assertEquals(java.util.Optional.ofNullable(tcs), companyService.getOne(1));
    }

    @Test
    void addCompany() {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "success");
        assertEquals(response, companyService.addCompany(tcs));
    }

    @Test
    void search() {
        List<CompanyModel> company = new ArrayList<>();
        company.add(tcs);
        company.add(ibm);

        when(companyRepository.findAll()).thenReturn(company);
        assertEquals(company, companyService.search());
    }

    @Test
    void deleteCompany() {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "success");

        when(companyRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(tcs));
        assertEquals(response, companyService.deleteCompany(1));
    }

    @Test
    void updateCompany() {

        when(companyRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(tcs));

        when(companyRepository.save(tcs)).thenReturn(tcs);
        assertEquals(ResponseEntity.ok(tcs), companyService.updateCompany(1, tcs2));
        assertEquals(2, companyService.updateCompany(1, tcs2).getBody().getCurrent_rate());

    }

    @Test
    void getCompanyById(){
        when(companyRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(tcs));
        assertEquals(ResponseEntity.ok(tcs), companyService.getCompanyById(1));
    }
}