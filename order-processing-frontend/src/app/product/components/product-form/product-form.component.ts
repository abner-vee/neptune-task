import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../service/product.service';
import { Product } from '../../model/product';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent {
  name: string = '';
  quantity: number = 1;

  constructor(private productService: ProductService) {}

  addProduct() {
    const newProduct: Product = {
      name: this.name,
      quantity: this.quantity
    };

    this.productService.addProduct(newProduct).subscribe({
      next: response => {
        alert(`Product "${response.name}" added successfully!`);
        this.name = '';
        this.quantity = 1;
      },
      error: err => {
        console.error('Product creation failed:', err);
        alert('An error occurred while adding the product.');
      }
    });
  }
}
