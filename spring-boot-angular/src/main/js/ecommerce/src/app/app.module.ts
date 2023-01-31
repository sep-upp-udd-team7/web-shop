import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router'

import {AppComponent} from './app.component';
import {EcommerceComponent} from './ecommerce/ecommerce.component';
import {ProductsComponent} from './ecommerce/products/products.component';
import {ShoppingCartComponent} from './ecommerce/shopping-cart/shopping-cart.component';
import {OrdersComponent} from './ecommerce/orders/orders.component';
import {EcommerceService} from "./ecommerce/services/EcommerceService";
import { CancelOrderComponent } from './ecommerce/cancel-order/cancel-order.component';
import { ConfirmOrderComponent } from './ecommerce/confirm-order/confirm-order.component';
import { SubscriptionPaidComponent } from './ecommerce/subscription-paid/subscription-paid.component';
import { SubscriptionCanceledComponent } from './ecommerce/subscription-canceled/subscription-canceled.component';

const routes: Routes = [
    { path: 'home', component: EcommerceComponent },
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    {path:'confirm-subscription',component:SubscriptionPaidComponent},
    {path:'cancel-subscription',component:SubscriptionCanceledComponent},
    {path:'success/:id',component:ConfirmOrderComponent},
    {path:'cancel/:id',component:CancelOrderComponent}
  ]
  
@NgModule({
    declarations: [
        AppComponent,
        EcommerceComponent,
        ProductsComponent,
        ShoppingCartComponent,
        OrdersComponent,
        CancelOrderComponent,
        ConfirmOrderComponent,
        SubscriptionPaidComponent,
        SubscriptionCanceledComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule.forRoot(routes)
    ],
    providers: [EcommerceService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
