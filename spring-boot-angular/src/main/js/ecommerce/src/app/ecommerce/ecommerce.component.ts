import {Component, OnInit, ViewChild} from '@angular/core';
import {ProductsComponent} from "./products/products.component";
import {ShoppingCartComponent} from "./shopping-cart/shopping-cart.component";
import {OrdersComponent} from "./orders/orders.component";

@Component({
    selector: 'app-ecommerce',
    templateUrl: './ecommerce.component.html',
    styleUrls: ['./ecommerce.component.scss']
})
export class EcommerceComponent implements OnInit {
    public collapsed = true;
    orderFinished = false;
    public confirmOrder = false;
    public cancelOrder = false;
    public orderId = '';

    @ViewChild('productsC')
    productsC: ProductsComponent;

    @ViewChild('shoppingCartC')
    shoppingCartC: ShoppingCartComponent;

    @ViewChild('ordersC')
    ordersC: OrdersComponent;



    constructor() {
    }

    ngOnInit() {
        let url = window.location.href;
        if (url.includes('success')) {
            this.confirmOrder = true;
        } else if (url.includes('cancel')) {
            this.cancelOrder = true;
        }
    }

    toggleCollapsed(): void {
        this.collapsed = !this.collapsed;
    }

    finishOrder(orderFinished: boolean) {
        this.orderFinished = orderFinished;
    }

    reset() {
        this.orderFinished = false;
        this.productsC.reset();
        this.shoppingCartC.reset();
        this.ordersC.paid = false;
        this.confirmOrder = false;
        this.cancelOrder = false;
    }
}
