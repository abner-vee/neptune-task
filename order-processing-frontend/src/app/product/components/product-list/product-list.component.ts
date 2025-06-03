import {Component, OnInit} from '@angular/core';

import { CommonModule } from '@angular/common';
import { NgIf, NgForOf } from '@angular/common';
import { ProductService } from '../../service/product.service';
import { Product } from '../../model/product';
import {ProductResponse} from "../../model/product-response";

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, NgIf, NgForOf],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit {
  products: ProductResponse[] = [];
  selectedProduct: ProductResponse | null = null;
  productToDelete: ProductResponse | null = null;

  showViewModal: boolean = false;
  showDeleteConfirmModal: boolean = false;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: data => this.products = data,
      error: err => console.error('Failed to fetch products', err)
    });
  }

  openViewModal(product: Product): void {
    this.selectedProduct = { ...product };
    this.showViewModal = true;
  }

  closeViewModal(): void {
    this.selectedProduct = null;
    this.showViewModal = false;
  }

  openDeleteConfirmModal(product: Product): void {
    this.productToDelete = product;
    this.showDeleteConfirmModal = true;
  }

  closeDeleteConfirmModal(): void {
    this.productToDelete = null;
    this.showDeleteConfirmModal = false;
  }

  confirmDelete(): void {
    if (!this.productToDelete?.id) return;

    this.productService.deleteProduct(this.productToDelete.id).subscribe({
      next: () => {
        this.products = this.products.filter(p => p.id !== this.productToDelete?.id);
        this.closeDeleteConfirmModal();
        alert(`Product "${this.productToDelete?.name}" deleted successfully.`);
      },
      error: err => {
        console.error('Delete failed', err);
        alert('Failed to delete product.');
      }
    });
  }
}
