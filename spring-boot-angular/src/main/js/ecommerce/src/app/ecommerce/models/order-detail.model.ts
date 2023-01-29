import { ProductOrder } from "./product-order.model";
import {Product} from "./product.model";

export class OrderDetail {
    constructor(
        public status: string = '',
        public orderId: string = '',
        public price: number = 0,
        public productOrders: ProductOrder[] = [],
    ) {}
}
