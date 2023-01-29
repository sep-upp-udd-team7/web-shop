import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {EcommerceComponent} from './ecommerce/ecommerce.component';
import {ProductsComponent} from './ecommerce/products/products.component';
import {ShoppingCartComponent} from './ecommerce/shopping-cart/shopping-cart.component';
import {OrdersComponent} from './ecommerce/orders/orders.component';
import {EcommerceService} from "./ecommerce/services/EcommerceService";
import { CancelOrderComponent } from './ecommerce/cancel-order/cancel-order.component';
import { ConfirmOrderComponent } from './ecommerce/confirm-order/confirm-order.component';

@NgModule({
    declarations: [
        AppComponent,
        EcommerceComponent,
        ProductsComponent,
        ShoppingCartComponent,
        OrdersComponent,
        CancelOrderComponent,
        ConfirmOrderComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [EcommerceService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
