import { Component, Input, OnInit } from '@angular/core';
import { CartService } from "src/app/services/cart.service";
import { CartItem } from "src/app/shared/interface/cartItem";

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.scss']
})
export class CartItemComponent implements OnInit
{

  @Input() cartItem: CartItem | any;


  constructor(private cartService: CartService) { }

  ngOnInit(): void
  {
  }

  increaceQuantity()
  {
    this.cartService.increase(this.cartItem);
  }

  decreaseQuantity()
  {
    this.cartService.decrease(this.cartItem);
  }

  remove()
  {
    this.cartService.remove(this.cartItem);
  }

}
