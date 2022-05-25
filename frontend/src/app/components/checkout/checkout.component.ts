import { Component, OnInit } from '@angular/core';
import { Address } from "src/app/shared/interface/address";
import { Card } from "src/app/shared/interface/card";

@Component({
  selector: 'app-checkout-component',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit
{

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


  constructor() { }


  ngOnInit(): void
  {

  }

  placeOrder(address: Address, card: Card)
  {
    console.log(address, card)
  }




}
