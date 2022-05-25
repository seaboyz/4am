import { Component, OnInit } from '@angular/core';
import { ProductService } from "src/app/services/product.service";
import { Product } from "src/app/shared/interface/product";

@Component({
  selector: 'app-products-page',
  templateUrl: './products-page.component.html',
  styleUrls: ['./products-page.component.css']
})
export class ProductsPageComponent implements OnInit
{


  productList: Product[] = [];

  constructor(private productService: ProductService)
  {

  }

  ngOnInit(): void
  {
    this.productService.loadProducts().subscribe(productList => this.productList = productList)
  }



}
