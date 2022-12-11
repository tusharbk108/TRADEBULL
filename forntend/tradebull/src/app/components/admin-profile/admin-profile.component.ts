import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { GetDataService } from 'src/app/services/get-data.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent implements OnInit {
  switchToPage=true;
  switchTowithdraw=true;
  amount;
  admin:any;
    constructor( 
      private getDataservice: GetDataService, 
      private _Activatedroute:ActivatedRoute,
      private router: Router,
      private toastr: ToastrService) { }
    userDetails;
    ngOnInit() {
      this.switchToPage=false;
      this.getDataservice.getUserDeatils(localStorage.getItem('username'))
      .subscribe(
        data => {
            this.userDetails = data;
        },
        error => { }
      )
  
      this.loadData();
     
    }

      
  goToAdmin()
  {
    this.router.navigate(['admin']);
  }
  
    loadData() {
      this.admin = this.getDataservice.getAllRequest(localStorage.getItem('username'));
    }
    switchTo(){
      this.switchToPage=true;
      if(!isNaN(Number(this.amount))){
      this.addfund();}
    }
    switchTowi(){
      this.switchToPage=true;
      if(!isNaN(Number(this.amount))){
      this.withdraw();}
    }
  
    addfund() 
    {
      this.getDataservice.addfund( localStorage.getItem('username'), this.amount)
        .subscribe(
          data => {console.log(data)
            if(data.status=="pending"){
        alert("The amount is on pending status"); 
        window.location.reload();
       }        
            else
              this.toastr.error(" ",'error',{positionClass:"toast-bottom-center"});
          },
          error => {
              console.log(error)
          }
        )
    }
    withdraw() 
    {
      this.getDataservice.withdraw( localStorage.getItem('username'), this.amount)
        .subscribe(
          data => {console.log(data)
            if(data.status=="pending"){
        alert("The amount is in pending status");
        window.location.reload();
       }        
            else
              this.toastr.error("",'error',{positionClass:"toast-bottom-center"});
          },
          error => {
              console.log(error)
          }
        )
    }
}
