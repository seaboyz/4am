import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from "rxjs";
import { User } from "../shared/interface/user";




@Injectable({
  providedIn: 'root'
})
export class AuthService
{
  base_url = 'http://localhost:8080/auth/';


  currentUser = new BehaviorSubject<User | undefined>(undefined);

  baseUrl: string = "http://localhost:8080/users";

  constructor(private http: HttpClient)
  {
    const userData = localStorage.getItem("user");

    if (!userData) {
      localStorage.setItem("user", JSON.stringify({}))
    }
    const user: User = JSON.parse(localStorage.getItem("user")!);
    if (user) {
      this.currentUser.next(user);
    } else {
      localStorage.setItem("user", JSON.stringify({}))
    }

  }

  ngOnInit(): void
  {

  }

  login(email: string, password: string): Observable<any>
  {
    const base64Credential: string = btoa(email + ':' + password);

    const httpOptions = {
      headers: {
        'Authorization': 'Basic ' + base64Credential,
      }
    }

    return this.http.get<User>(this.base_url + 'login', httpOptions);
  }

  register(username: string, email: string, password: string, phoneNumber: string): Observable<any>
  {
    console.log(username, email, password, phoneNumber);
    const httpOptions = {
      headers: { 'Content-Type': 'application/json' }
    };

    return this.http.post(this.base_url + 'register', { username, email, password, phoneNumber }, httpOptions);
  }

  signOut()
  {
    localStorage.setItem("user", JSON.stringify({}))
    this.currentUser.next(undefined)
  }


}
