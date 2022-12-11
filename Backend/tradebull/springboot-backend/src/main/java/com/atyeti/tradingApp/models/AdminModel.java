package com.atyeti.tradingApp.models;

import javax.persistence.*;
@Entity
@Table(name = "admin")
public class AdminModel {



        @Id
        @Column(name="sno",nullable = false)
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer sno ;

        @Column(name="companyid",nullable = false)
        private int company_id;

        @Column(name="userid",nullable = false)
        private String userid;

        @Column(name="date_time" ,nullable = false)
        private String dateTime;

        @Column(name="quantity",nullable = false)
        private int qty;

        @Column(name="amount" ,nullable = false)
        private int amount;

        @Column(name="transaction_Type" ,nullable = false)
        private String type;

        @Column(name="status" ,nullable = false)
        private String status;

    public AdminModel(Integer sno, int company_id, String userid, String dateTime, int qty, int amount, String type, String status) {
        this.sno = sno;
        this.company_id = company_id;
        this.userid = userid;
        this.dateTime = dateTime;
        this.qty = qty;
        this.amount = amount;
        this.type = type;
        this.status = status;
    }

    public AdminModel() {
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
