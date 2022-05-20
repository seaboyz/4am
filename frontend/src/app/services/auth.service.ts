import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";

const AUTH_API = 'http://localhost:8080/auth/';



@Injectable({
  providedIn: 'root'
})
export class AuthService
{

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<any>
  {
    const base64Credential: string = btoa(email + ':' + password);

    const httpOptions = {
      headers: {
        'Authorization': 'Basic ' + base64Credential,
      }
    }

    return this.http.get(AUTH_API + 'login', httpOptions);
  }

  register(username: string, email: string, password: string)
  {
    const httpOptions = {
      headers: { 'Content-Type': 'application/json' }
    };

    return this.http.post(AUTH_API + 'register', { username, email, password }, httpOptions);
  }
}
