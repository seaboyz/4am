import { Component, Input, OnInit } from '@angular/core';
import { CartService } from "src/app/services/cart.service";
import { Product } from "src/app/shared/interface/product";




@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit
{
  @Input() product?: Product;

  constructor(private cartService: CartService) { }

  ngOnInit(): void
  {
  }

  url(url: string | undefined): string
  {
    return url ? `url(${url})` : 'url(assets/no-image.png)';
  }

  addToCart(product?: Product)
  {
    if (product) {
      this.cartService.addItem({ ...product, quantity: 1 });
    }
    return;
  }

}
