import { Component } from '@angular/core';
import {OrderService} from "../../service/order.service";
import {Order} from "../../model/order";
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {ProductResponse} from "../../../product/model/product-response";
import {ProductService} from "../../../product/service/product.service";

@Component({
  selector: 'app-order-form',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf
  ],
  templateUrl: './order-form.component.html',
  styleUrl: './order-form.component.css'
})
export class OrderFormComponent {
  name = '';
  products: ProductResponse[] = [];
  selectedProduct: string = '';
  quantity: number = 1;


  constructor(private productService: ProductService, private orderService: OrderService) {
  }
  ngOnInit() {
    this.productService.getAllProducts().subscribe(data => this.products = data);
  }

  submitOrder() {
    const order: Order = {
      name: this.selectedProduct,
      quantity: this.quantity
    };
    this.orderService.createOrder(order).subscribe({
      next: () => alert('Order submitted!'),
      error: err => console.error(err)
    });
  }
}
