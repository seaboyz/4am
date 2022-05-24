import { Component, Input, OnInit } from '@angular/core';
import { CartService } from "src/app/services/cart.service";
import { CartItem } from "src/app/shared/interface/cartItem";

@Component({
  selector: 'app-checkout',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.scss']
})
export class CartPageComponent implements OnInit
{
  cartItems: CartItem[] = [];

  totalPrice = 0;

  constructor(private cartService: CartService) { }

  ngOnInit(): void
  {
    this.cartService.cartItems.subscribe(cartItems => this.cartItems = cartItems)

    this.cartService.cartItems.subscribe(cartItems => this.totalPrice = cartItems.reduce((total, cartItem) => total + cartItem.price * cartItem.quantity, 0))

  }

}
