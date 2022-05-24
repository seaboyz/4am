import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { catchError, Observable, retry, throwError } from "rxjs";




@Injectable({
  providedIn: 'root'
})
export class AuthService
{
  base_url = 'http://localhost:8080/auth/';

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<any>
  {
    const base64Credential: string = btoa(email + ':' + password);

    const httpOptions = {
      headers: {
        'Authorization': 'Basic ' + base64Credential,
      }
    }

    return this.http.get(this.base_url + 'login', httpOptions);
  }

  register(username: string, email: string, password: string, phoneNumber: string): Observable<any>
  {
    console.log(username, email, password, phoneNumber);
    const httpOptions = {
      headers: { 'Content-Type': 'application/json' }
    };

    return this.http.post(this.base_url + 'register', { username, email, password, phoneNumber }, httpOptions);
  }

  createUser(body: object, options: object): Observable<any>
  {
    return this.http.post<any>(this.base_url + "/register", JSON.stringify(body), options)
      .pipe(
        retry(3),
        catchError(this.errorHandler)
      )
  }

  errorHandler(e: any): any
  {
    console.log("Error handler invoked...");
    let errorMessage = '';
    if (e.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = e.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${e.status}\nMessage: ${e.message}`;
    }
    console.log(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
