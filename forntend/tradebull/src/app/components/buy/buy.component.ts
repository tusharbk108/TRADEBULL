import { Component, OnInit } from '@angular/core';
import { Router,ActivatedRoute } from '@angular/router';
import { GetDataService } from '../../services/get-data.service';
import { ToastrService } from 'ngx-toastr';
import { interval, Observable } from 'rxjs';

@Component({
  selector: 'app-buy',
  templateUrl: './buy.component.html',
  styleUrls: ['./buy.component.css']
})
export class BuyComponent implements OnInit {
  history: Observable<History[]>;
  companyId = '';
  quantity:number=0 ;
  companyDetails;
  current_rate:number;
  // data:number=Math.floor(Math.random() * 10);
  // max_price:number;
  // min_price:number;
  amount:number;
  qty:number;
  constructor(private router: Router, 
              private getDataservice: GetDataService, 
              private _Activatedroute:ActivatedRoute,
              private toastr: ToastrService) { }

  ngOnInit() {

    // const obs$=interval(2000);
    // obs$.subscribe((d)=>{
    //     this.data=this.getDataservice.getRandomNum(this.max_price,this.min_price);
    //     this.getAmount(this.data,this.quantity);
    // });


    this._Activatedroute.paramMap.subscribe(params => { 
      this.companyId = params.get('id'); 
      });

      this.getDataservice.getOneCompany(this.companyId)
      .subscribe(
        data => {
            this.companyDetails = data;
            this.current_rate=this.companyDetails.current_rate;
        },
        error => {
            console.log(error)
        }
      )
      
  }

  pendingBuy(){
    console.log("Pending Buy working");
    this.getDataservice.pendingOrder(localStorage.getItem('username'), this.companyId, this.quantity).subscribe(
      data => {this.brokrage();
        if(data.status === "Pending"){
        alert("Your buy request is pending");
          this.router.navigate(['userOrder'])  }        
        else if(data.status=="insufficient balance")
          this.toastr.error("",'Insufficient balance in your account',{positionClass:"toast-bottom-center"});
      },
    )
  }

  buyItem() 
  {
    this.getDataservice.buyShare( localStorage.getItem('username'), this.companyId, this.quantity)
      .subscribe(
        data => {console.log(data)
          if(data.status=="success"){
	    alert("The share buy successfully");
            this.router.navigate(['my-shares'])  }        
          else if(data.status=="insufficient balance")
            this.toastr.error("",'Insufficient balance in your account',{positionClass:"toast-bottom-center"});
        },
        error => {
            console.log(error)
        }
      )
  }
  checkQuantity(qty)
  {
    
    if(isNaN(Number(qty.value)) || Number(qty.value) === 0 ||Number(this.companyDetails.volume) <1 )
      return false;
    else if(Number(qty.value) <= Number(this.companyDetails.volume))
      return true;
  }

  getQuantity(quantity:number){
    this.qty=quantity;
    this.amount = this.current_rate * quantity;
    
  }
brokrage(){
   this.getDataservice.pendingHistoryUser( localStorage.getItem('username')).subscribe(
    data =>{
     this.history = data;
    console.log(this.history);
    const mapped = Object.entries(this.history).map(([type, value]) => ({type, value}));
    console.log("size"+mapped.length);
    if((this.qty<50) && (mapped.length<10)){
      this.toastr.success('2% brokerage will apply');
      }else{
        this.toastr.success('3% brokerage will apply');
      }
    }
  );
    
 
  }

 cancel(){
    this.router.navigate(['my-shares']);
  }
}

