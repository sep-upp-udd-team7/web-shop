import { Component, OnInit } from '@angular/core';
import { OrderDetail } from '../models/order-detail.model';
import { EcommerceService } from '../services/EcommerceService';

@Component({
  selector: 'app-cancel-order',
  templateUrl: './cancel-order.component.html',
  styleUrls: ['./cancel-order.component.css']
})
export class CancelOrderComponent implements OnInit {

  constructor(private ecommerceService : EcommerceService) { }

  orderId: string = '';
  orderDetail: OrderDetail = new OrderDetail();

  ngOnInit() {
    let url = window.location.href;
    let urlParts = url.split('/')
    this.orderId = urlParts[urlParts.length - 1]
    this.cancelOrder()
  }

  cancelOrder() {
    this.ecommerceService.cancelOrder(this.orderId).subscribe(
      data => {
        console.log(data)
        this.getOrderDetail()
      },
      error => {
        console.log(error)
      } 
    )
  }

  getOrderDetail() {
    this.ecommerceService.getOrderDetails(this.orderId).subscribe(
      data => {
        console.log(data)
        this.orderDetail = data
      },
      error => {
        console.log(error)
      } 
    )
  }


  continueShoping() {
    window.location.href = '/'
  }

}
