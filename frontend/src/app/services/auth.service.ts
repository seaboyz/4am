import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";

const AUTH_API = 'http://localhost:8080/api/auth/';

const httpOptions = {
  headers: { 'Content-Type': 'application/json' }
};

@Injectable({
  providedIn: 'root'
})
export class AuthService
{

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<any>
  {
    return this.http.post(AUTH_API + 'login', { email, password }, httpOptions);
  }

  register(username: string, email: string, password: string)
  {
    return this.http.post(AUTH_API + 'register', { username, email, password }, httpOptions);
  }
}
