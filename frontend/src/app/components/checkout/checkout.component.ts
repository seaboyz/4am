import { Component, OnInit } from '@angular/core';

import { AuthService } from "src/app/services/auth.service";
import { CartService } from "src/app/services/cart.service";
import { OrderService } from "src/app/services/order.service";
import { Address } from "src/app/shared/interface/address";
import { Card } from "src/app/shared/interface/card";
import { CartItem } from "src/app/shared/interface/cartItem";
import { Order } from "src/app/shared/interface/order";
import { User } from "src/app/shared/interface/user";

@Component({
  selector: 'app-checkout-component',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit
{

  currentUser: User | undefined = undefined;

  cartItems: CartItem[] = [];

  #firstname: string = "";
  #lastname: string = "";
  #phone: string = "";
  #address: string = "";
  #zipCode: string = "";
  #city: string = "";
  #state: string = "";
  #country: string = "";

  #cardNumber: string = "";
  #exp: string = "";
  #cvv: string = "";


  constructor(
    private authService: AuthService,
    private cartService: CartService,
    private orderService: OrderService)
  {

    this.authService.currentUser.subscribe(user => this.currentUser = user);

    this.cartService.cartItems.subscribe(cartItems => this.cartItems = cartItems)
  }


  ngOnInit(): void
  {

  }

  placeOrder(address: Address, card: Card)
  {

    const customerId: number | undefined = this.currentUser?.id;

    const shippingAddress = address;

    const orderItems = this.cartItems.map(cartitem => ({ productId: cartitem.id, quantity: cartitem.quantity }))

    if (!customerId || !shippingAddress || !orderItems)
      return;

    const order: Order = {
      customerId, shippingAddress, orderItems
    }

    this.orderService.placeOrder(order).subscribe(orderId =>
    {
      if (orderId) {
        alert("Thank you for your payment.")
      } else {
        alert("Somthing went wrong, please try again later.")
      }
    })

  }




}
