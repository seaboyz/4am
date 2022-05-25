import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
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

  constructor(private authService: AuthService, private router: Router)
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
        alert(`Welcome ${user.username}.`)
        this.router.navigateByUrl("");
      }
    )
  }
}
