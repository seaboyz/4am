import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService
{

  constructor() { }

  login(email: string, password: string)
  {
    if (email === 'test@test.com' && password === '123456') {
      localStorage.setItem('username', email);
      return true;
    }
    alert('Login Failed');
    return false;
  }
}
