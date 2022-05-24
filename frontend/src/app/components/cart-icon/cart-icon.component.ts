import { Component, OnInit } from '@angular/core';
import { CartService } from "src/app/services/cart.service";

@Component({
  selector: 'app-cart-icon',
  templateUrl: './cart-icon.component.html',
  styleUrls: ['./cart-icon.component.scss']
})
export class CartIconComponent implements OnInit
{
  numberOfItemsInCart: number = 0;

  constructor(private cartService: CartService) { }

  ngOnInit(): void
  {
    this.cartService.cartItems.subscribe(cartItems => this.numberOfItemsInCart = cartItems.reduce((total, item) => item.quantity + total, 0))
  }

}
