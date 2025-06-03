import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {NavComponent} from "./order/component/nav/nav.component";
import {OrderFormComponent} from "./order/component/order-form/order-form.component";
import {OrderListComponent} from "./order/component/order-list/order-list.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavComponent, OrderFormComponent, OrderListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'order-processing-frontend';
}
