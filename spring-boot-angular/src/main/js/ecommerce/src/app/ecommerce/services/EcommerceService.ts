import {ProductOrder} from "../models/product-order.model";
import {Subject} from "rxjs/internal/Subject";
import {ProductOrders} from "../models/product-orders.model";
import {HttpClient} from '@angular/common/http';
import {Injectable} from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable()
export class EcommerceService {
    private productsUrl = environment.backendUrl + "products";
    private ordersUrl = environment.backendUrl + "orders";

    private productOrder: ProductOrder;
    private orders: ProductOrders = new ProductOrders();

    private productOrderSubject = new Subject();
    private ordersSubject = new Subject();
    private totalSubject = new Subject();

    private total: number;

    ProductOrderChanged = this.productOrderSubject.asObservable();
    OrdersChanged = this.ordersSubject.asObservable();
    TotalChanged = this.totalSubject.asObservable();

    constructor(private http: HttpClient) {
    }

    getAllProducts() {
        return this.http.get(this.productsUrl);
    }

    saveOrder(order: ProductOrders) {
        return this.http.post<any>(this.ordersUrl, order);
    }

    confirmOrder(orderId: string) {
        return this.http.get<any>(this.ordersUrl + "/confirm/" + orderId);
    }

    cancelOrder(orderId: string) {
        return this.http.get<any>(this.ordersUrl + "/cancel/" + orderId);
    }

    getOrderDetails(orderId: string) {
        return this.http.get<any>(this.ordersUrl + "/" + orderId);
    }

    set SelectedProductOrder(value: ProductOrder) {
        this.productOrder = value;
        this.productOrderSubject.next();
    }

    get SelectedProductOrder() {
        return this.productOrder;
    }

    set ProductOrders(value: ProductOrders) {
        this.orders = value;
        this.ordersSubject.next();
    }

    get ProductOrders() {
        return this.orders;
    }

    get Total() {
        return this.total;
    }

    set Total(value: number) {
        this.total = value;
        this.totalSubject.next();
    }
}
