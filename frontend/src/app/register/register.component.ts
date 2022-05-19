import { Component, OnInit } from '@angular/core';
import { AuthService } from "../services/auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit
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

  onUsernameChange(event: Event)
  {

  }

  login(): void
  {
    this.authService.login(this.email, this.password);
  }

}
