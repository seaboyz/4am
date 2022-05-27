import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
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

  placeOrder(order: Order): Observable<{ orderId: string }>
  {
    const httpOptions = {
      headers: { 'Content-Type': 'application/json' }
    };

    return this.http.post<{ orderId: string }>(this.base_url, order, httpOptions)

  }
}
