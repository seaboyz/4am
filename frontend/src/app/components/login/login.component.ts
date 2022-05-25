import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit
{
  #email = "";
  #password = "";

  constructor(private authService: AuthService)
  {
    this.authService = authService;
  }

  ngOnInit(): void
  {
  }



  login(email: string, password: string): void
  {
    this.authService.login(email, password).subscribe(
      user =>
      {
        console.log(user);
      }
    )
  }
}
