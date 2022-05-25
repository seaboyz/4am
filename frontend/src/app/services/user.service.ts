import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from "../shared/interface/user";
import { AuthService } from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class UserService implements OnInit
{

  currentUser: User | undefined;

  base_url: string = "http://localhost:8080/customers";

  constructor(private http: HttpClient, private authService: AuthService)
  {
    this.authService.currentUser.subscribe(user => this.currentUser = user)

  }

  ngOnInit(): void
  {

  }

  updateProile(username: string, email: string, password: string, phone: string): Observable<User>
  {


    const httpOptions = {
      headers: { 'Content-Type': 'application/json' }
    };


    return this.http.put<User>(
      this.base_url + "/" + this.currentUser?.id,
      JSON.stringify({ username, email, password, phone }), httpOptions);


  }





}
