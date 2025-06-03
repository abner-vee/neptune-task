import { Routes } from '@angular/router';
import {OrderFormComponent} from "./order/component/order-form/order-form.component";
import {OrderListComponent} from "./order/component/order-list/order-list.component";
import {NavComponent} from "./order/component/nav/nav.component";
import {ProductListComponent} from "./product/components/product-list/product-list.component";
import {ProductFormComponent} from "./product/components/product-form/product-form.component";

export const routes: Routes = [
  { path: '', redirectTo: 'add-product', pathMatch: 'full' },
  { path: 'add-product', component: ProductFormComponent },
  { path: 'view-products', component: ProductListComponent },
  { path: 'create-order', component: OrderFormComponent },
  { path: 'order-status', component: OrderListComponent },
  { path: '**', redirectTo: 'add-product' }
];
