import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Order } from "../shared/interface/order";

@Injectable({
  providedIn: 'root'
})
export class OrderService
{

  base_url = 'http://localhost:8080/orders';



  constructor(private http: HttpClient)
  {

  }

  placeOrder(order: Order)
  {
    const httpOptions = {
      headers: { 'Content-Type': 'application/json' }
    };

    return this.http.post<string>(this.base_url, order, httpOptions,);

  }
}
