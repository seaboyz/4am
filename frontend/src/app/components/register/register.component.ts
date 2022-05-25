import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { AuthService } from "../../services/auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit
{
  #username: string = "";
  #email: string = "";
  #password: string = "";
  #repassword: string = "";
  #phone: string = "";


  constructor(private authService: AuthService, private router: Router)
  {
    this.authService = authService;
  }

  ngOnInit(): void
  {
  }



  register(username: string, email: string, password: string, phone: string): void
  {
    this.authService.register(username, email, password, phone).subscribe(
      user =>
      {
        alert(`Welcom ${user.username}, please login use you email and password.`);
        this.router.navigateByUrl("login")
      }
    );
  }

}
