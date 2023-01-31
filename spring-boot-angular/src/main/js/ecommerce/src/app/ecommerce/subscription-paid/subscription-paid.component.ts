import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EcommerceComponent } from '../ecommerce.component';
import { EcommerceService } from '../services/EcommerceService';

@Component({
  selector: 'app-subscription-paid',
  templateUrl: './subscription-paid.component.html',
  styleUrls: ['./subscription-paid.component.css']
})
export class SubscriptionPaidComponent implements OnInit{

  constructor(
    private ecomercService:EcommerceService, 
    private route: ActivatedRoute
  ) {}

  token:string='';
  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      console.log(params);
      this.token = params['token'];
      this.ecomercService.confirmSubscription(this.token).subscribe(
      data=>{
        
      }
      ,(error)=>{
        alert('Something went wrong');
      });
    });
  }

  continue(){
    window.location.href='';
  }

}
