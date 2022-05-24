import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { BehaviorSubject, catchError, Observable, retry, throwError } from 'rxjs';
import { User } from "../shared/interface/user";

@Injectable({
  providedIn: 'root'
})
export class UserService implements OnInit
{

  currentUser = new BehaviorSubject<User>({ id: "", displayName: "", email: "" });

  emptyUser: User = { id: "", displayName: "", email: "" }

  baseUrl: string = "http://localhost:8080/users";

  constructor(private http: HttpClient) { }

  ngOnInit(): void
  {
    const storage = JSON.parse(localStorage.getItem("currentUser")!);
    if (storage) {
      this.currentUser.next(storage);
    } else {
      localStorage.setItem("currentUser", JSON.stringify(this.emptyUser))
    }
  }

  signOut()
  {
    localStorage.setItem("currentUser", "")
    this.currentUser.next(this.emptyUser)
  }



}
