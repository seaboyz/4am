import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { AuthService } from "src/app/services/auth.service";
import { UserService } from "src/app/services/user.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit
{

  #username: string = "";
  #email: string = "";
  #password: string = "";
  #repassword: string = "";
  #phone: string = "";


  constructor(
    private userSerive: UserService,
    private router: Router,
    private authService: AuthService
  )
  {

    authService.currentUser.subscribe(user =>
    {
      if (!user)
        return;

    })
  }

  ngOnInit(): void
  {
  }



  updateProfile(username: string, email: string, password: string, phone: string): void
  {

    this.userSerive.updateProile(username, email, password, phone).subscribe(
      user =>
      {
        alert(`Update successfull ${user.username}, please login use you email and password.`);
        this.router.navigateByUrl("login")
      }
    );
  }

}
