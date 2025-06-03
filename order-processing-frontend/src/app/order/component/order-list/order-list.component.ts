import { Component } from '@angular/core';
import {OrderService} from "../../service/order.service";
import {Order} from "../../model/order";
import {NgForOf, NgIf} from "@angular/common";
import {OrderResponse} from "../../model/order-response";

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.css'
})
export class OrderListComponent {
  orders: OrderResponse[] = [];
  showViewModal: boolean = false;
  selectedOrder: OrderResponse | null = null;

  showDeleteConfirmModal: boolean = false;
  orderIdToDelete: number | null = null;
  productToDeleteName: string | null = null;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders() {
    this.orderService.getOrders().subscribe({
      next: data => this.orders = data,
      error: err => console.error(err)
    });
  }
  viewOrder(order: Order){

  }
  deleteOrder(id?: number){

  }

  openViewModal(order: Order): void {
    this.selectedOrder = { ...order };
    this.showViewModal = true;
  }

  closeViewModal(): void {
    this.showViewModal = false;
    this.selectedOrder = null;
  }

  // --- Delete Confirmation Modal Methods ---
  openDeleteConfirmModal(name: string, id?: number): void {
    this.orderIdToDelete = id as number;
    this.productToDeleteName = name;
    this.showDeleteConfirmModal = true;
  }

  closeDeleteConfirmModal(): void {
    this.showDeleteConfirmModal = false;
    this.orderIdToDelete = null;
    this.productToDeleteName = null;
  }

  confirmDelete(): void {
    if (this.orderIdToDelete !== null) {
      this.orderService.deleteOrder(this.orderIdToDelete).subscribe({
        next: (res) => {
          // Remove the order from the list
          this.orders = this.orders.filter(order => order.id !== this.orderIdToDelete);
          alert(`Product "${this.productToDeleteName}" deleted successfully!`);
          this.closeDeleteConfirmModal();
        },
        error: (err) => {
          console.error('Delete failed', err);
          alert(`Failed to delete product "${this.productToDeleteName}"`);
          this.closeDeleteConfirmModal();
        }
      });
    }
  }

}
