import { Component, OnInit } from '@angular/core';

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
  password: string = "";
  lName: string = "";
  email: string = "";
  address: string = "";
  zipCode: string = "";
  city: string = "";
  country: string = "";
  cardNumber: string = "";
  exp: string = "";
  cvv: string = "";


  constructor() { }


  ngOnInit(): void
  {

  }

  placeOrder() { }



}
