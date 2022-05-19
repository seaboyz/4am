import { Component, OnInit } from '@angular/core';
import { AuthService } from "../services/auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit
{
  #username = "";
  #email = "";
  #password = "";
  #repassword = "";


  constructor(private authService: AuthService)
  {
    this.authService = authService;
  }

  ngOnInit(): void
  {
  }



  register(username: string, email: string, password: string): void
  {
    this.authService.register(username, email, password);

  }

}
