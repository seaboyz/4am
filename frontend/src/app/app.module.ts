import { HttpClientModule } from "@angular/common/http";
import { NgModule } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from "@angular/material/input";
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from "@angular/router";
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddToCartBtn } from "./components/add-to-cart-btn/add-to-cart-btn.component";
import { CartIconComponent } from "./components/cart-icon/cart-icon.component";
import { CheckoutBtnComponent } from "./components/checkout-btn/checkout-btn.component";
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './components/login/login.component';
import { PlaceOrderBtnComponent } from "./components/place-order-btn/place-order-btn.component";
import { ProductComponent } from "./components/product/product.component";
import { ProductsComponent } from "./components/products/products.component";
import { RegisterComponent } from "./components/register/register.component";
import { HomeComponent } from './views/home-page/home-page.component';
import { LoginPageComponent } from './views/login-page/login-page.component';
import { ProductsPageComponent } from './views/products-page/products-page.component';

import { CartItemComponent } from "./components/cart-item/cart-item.component";
import { CartPageComponent } from "./views/cart-page/cart-page.component";
import { CartService } from "./services/cart.service";
import { AuthService } from "./services/auth.service";
import { ProductService } from "./services/product.service";





@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    HeaderComponent,
    LoginPageComponent,
    ProductsPageComponent,
    CartIconComponent,
    ProductComponent,
    AddToCartBtn,
    PlaceOrderBtnComponent,
    CheckoutBtnComponent,
    ProductsComponent,
    ProductComponent,
    CartItemComponent,
    CartPageComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    MatInputModule,
    MatCardModule,
    MatTabsModule,
    MatFormFieldModule,
    MatButtonModule,
    MatCheckboxModule,
    MatIconModule,
    MatToolbarModule,
    RouterModule.forRoot([
      {
        path: '',
        component: ProductsPageComponent
      },
      {
        path: 'login',
        component: LoginPageComponent
      },
      {
        path: 'register',
        component: RegisterComponent
      },
      {
        path: 'cart',
        component: CartPageComponent
      }

    ])

  ],
  providers: [CartService, AuthService, ProductService],
  bootstrap: [AppComponent]
})
export class AppModule { }
