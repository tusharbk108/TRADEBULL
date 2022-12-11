import { ElementSchemaRegistry } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/services/authenticate.service';
import { GetDataService } from 'src/app/services/get-data.service';

@Component({
  selector: 'app-fundrequest',
  templateUrl: './fundrequest.component.html',
  styleUrls: ['./fundrequest.component.css']
})
export class FundrequestComponent implements OnInit {
  admin: any
 sno:any;
  constructor(private getDataservice: GetDataService,
    private loginService: AuthenticationService,
    private router: Router,private toastr: ToastrService) { }

  ngOnInit() {
    this.loadData();
  }

  
  goToAdmin()
  {
    this.router.navigate(['admin']);
  }
  
  loadData() {
    this.admin = this.getDataservice.getPendingFunds()
  }

  approvefund(sno: number,email:string) {

    this.getDataservice.approveFund(sno).subscribe(data =>
      {
        if(data.status ==='success'){
          alert("Ticket No "+sno+" approved");
          this.loadData();
        }
        else if(data.status==='invalid')
        {
          this.toastr.error('User doesnt have enough money to withdraw', this.sno, { positionClass: "toast-bottom-center" });
          this.loadData(); 
        }
      });
  }

  rejectfund(sno:number): void
  {
      this.getDataservice.rejectFund(sno).subscribe(data=>{
       if(data.status ==='rejected')
       {
        alert("Ticket No "+sno+" rejected");
         this.loadData();
       }
      });
  }

  isLogedIn() {
    if (this.loginService.isUserLoggedIn()) {
      return true;
    } else {
      alert("Please login");
      this.router.navigate(['home']);
      return false;
    }
  }
}

