import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EcommerceService } from '../services/EcommerceService';

@Component({
  selector: 'app-subscription-canceled',
  templateUrl: './subscription-canceled.component.html',
  styleUrls: ['./subscription-canceled.component.css']
})
export class SubscriptionCanceledComponent {

  constructor(
    private ecomercService:EcommerceService, 
    private route: ActivatedRoute
  ) {}

  token:string='';
  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      console.log(params);
      this.token = params['token'];
      this.ecomercService.cancelSubscription(this.token).subscribe(
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
