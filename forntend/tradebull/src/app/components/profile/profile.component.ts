import { Component, OnInit } from '@angular/core';
import { GetDataService } from '../../services/get-data.service';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { getCurrencySymbol } from '@angular/common';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  switchToPage = true;
  switchTowithdraw = true;
  amount;
  admin: any;
  constructor(
    private getDataservice: GetDataService,
    private _Activatedroute: ActivatedRoute,
    private toastr: ToastrService) { }
  userDetails;
  ngOnInit() {
    this.switchToPage = false;
    this.getDataservice.getUserDeatils(localStorage.getItem('username'))
      .subscribe(
        data => {
          this.userDetails = data;
        },
        error => { }
      )

    this.loadData();

  }

  loadData() {
    this.admin = this.getDataservice.getAllRequest(localStorage.getItem('username'));
  }
  switchTo() {
    this.switchToPage = true;
    if (!isNaN(Number(this.amount))) {
      this.addfund();
    }
  }
  switchTowi() {
    this.switchToPage = true;
    if (!isNaN(Number(this.amount))) {
      this.withdraw();
    }
  }

  addfund() {
    this.getDataservice.addfund(localStorage.getItem('username'), this.amount)
      .subscribe(
        data => {
          console.log(data)
          if (data.status == "pending") {
            alert("The amount is on pending status");
            this.loadData();
          }
          else
            this.toastr.error(" ", 'error', { positionClass: "toast-bottom-center" });
        },
        error => {
          console.log(error)
        }
      )
  }
  withdraw() {
    this.getDataservice.withdraw(localStorage.getItem('username'), this.amount)
      .subscribe(
        data => {
          console.log(data)
          if (data.status == "pending") {
            alert("The amount is in pending status");
             this.loadData();
          }
          else if(data.status=="invalid")
          {
            this.toastr.error('Not enough money to withdraw', this.amount, { positionClass: "toast-bottom-center" });
            
          }
          else
            this.toastr.error("", 'error', { positionClass: "toast-bottom-center" });
        },
        error => {
          console.log(error)
        }
      )
  }

  getColor(color) {
    switch (color) {
      case 'approved':
        return 'green';
      case 'pending':
        return 'rgb(218, 161, 6)';
      case 'rejected':
        return 'red';
    }
  }
}
