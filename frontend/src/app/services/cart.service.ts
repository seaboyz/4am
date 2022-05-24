import { Injectable, OnInit } from '@angular/core';
import { BehaviorSubject } from "rxjs";
import { CartItem } from "../shared/interface/cartItem";



@Injectable({
  providedIn: 'root'
})


export class CartService implements OnInit
{
  cartItems = new BehaviorSubject<CartItem[]>([]);

  constructor()
  {
  }

  ngOnInit(): void
  {
    const storage: CartItem[] = JSON.parse(localStorage.getItem("cart")!);
    if (storage) {
      this.cartItems.next(storage);
    } else {
      localStorage.setItem("cart", JSON.stringify([]))
    }
  }



  addItem(itemToAdd: CartItem)
  {
    const cartItemsFromLocalStorage: CartItem[] = JSON.parse(localStorage.getItem("cart")!);

    let exitItem: CartItem | undefined = cartItemsFromLocalStorage.find(cartItem => cartItem.id === itemToAdd.id);

    if (exitItem) {
      exitItem.quantity++;
      this.cartItems.next([...cartItemsFromLocalStorage])
      this.setLocalstorage([...cartItemsFromLocalStorage])
    } else {
      this.cartItems.next([...cartItemsFromLocalStorage, { ...itemToAdd, quantity: 1 }]);
      this.setLocalstorage([...cartItemsFromLocalStorage, { ...itemToAdd, quantity: 1 }]);
    }
  }

  increase(cartItemToIncrease: CartItem)
  {
    const cartItemsFromLocalStorage: CartItem[] = JSON.parse(localStorage.getItem("cart")!);

    let exitItem: CartItem | undefined = cartItemsFromLocalStorage.find(cartItem => cartItem.id === cartItemToIncrease.id);

    if (!exitItem) return;

    exitItem.quantity++;
    this.cartItems.next([...cartItemsFromLocalStorage])

    this.setLocalstorage([...cartItemsFromLocalStorage])
  }

  decrease(cartItemToDecrease: CartItem)
  {
    const cartItemsFromLocalStorage: CartItem[] = JSON.parse(localStorage.getItem("cart")!);

    let exitItem: CartItem | undefined = cartItemsFromLocalStorage.find(cartItem => cartItem.id === cartItemToDecrease.id);

    if (!exitItem) return;
    if (exitItem.quantity == 1) {
      this.remove(cartItemToDecrease);
      return;
    }
    exitItem.quantity--;
    this.cartItems.next([...cartItemsFromLocalStorage])

    this.setLocalstorage([...cartItemsFromLocalStorage])


  }

  remove(cartItemToRemove: CartItem)
  {
    const cartItemsFromLocalStorage: CartItem[] = JSON.parse(localStorage.getItem("cart")!);

    const cartItemsAfterRemove = cartItemsFromLocalStorage.filter(cartItem => cartItem.id != cartItemToRemove.id)

    this.cartItems.next([...cartItemsAfterRemove]);
    this.setLocalstorage([...cartItemsAfterRemove]);

  }

  setLocalstorage(data: CartItem[])
  {
    localStorage.setItem("cart", JSON.stringify(data))
  }
}
