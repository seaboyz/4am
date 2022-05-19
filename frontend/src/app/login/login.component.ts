import { Component, OnInit } from '@angular/core';
import { AuthService } from "../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit
{
  email = "";
  password = "";

  constructor(private authService: AuthService)
  {
    this.authService = authService;
  }

  ngOnInit(): void
  {
  }

  onEmailChange(event: Event): void
  {
    this.email = (<HTMLInputElement>event.target).value;
  }

  onPasswordChange(event: Event): void
  {
    this.password = (<HTMLInputElement>event.target).value;

  }


  login(): void
  {
    const success = this.authService.login(this.email, this.password);

    if(success){
      alert("Welcome!")
    }else{
      alert("Email and Password not match!")
    }
    

  }

}
