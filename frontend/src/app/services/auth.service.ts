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

  login(email: string, password: string): Observable<User>
  {
    const base64Credential: string = btoa(email + ':' + password);

    const httpOptions = {
      headers: {
        'Authorization': 'Basic ' + base64Credential,
      }
    }

    const user: Observable<User> = this.http.get<User>(this.base_url + 'login', httpOptions);

    user.subscribe(user =>
    {
      this.currentUser.next(user);
      localStorage.setItem("user", JSON.stringify(user))
    });

    return user;
  }

  register(username: string, email: string, password: string, phone: string): Observable<User>
  {
    const httpOptions = {
      headers: { 'Content-Type': 'application/json' }
    };

    return this.http.post<User>(this.base_url + 'register', JSON.stringify({ username, email, password, phone }), httpOptions);
    
  }

  signOut()
  {
    localStorage.setItem("user", JSON.stringify({}))
    this.currentUser.next(undefined)
  }


}
