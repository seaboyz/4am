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

  username: string | undefined = undefined;
  email: string | undefined = undefined;
  password: string | undefined = undefined;
  repassword: string | undefined = undefined;
  phone: string | undefined = undefined;


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

      this.username = user.username;
      this.email = user.email;
      this.phone = user.phone;

    })
  }

  ngOnInit(): void
  {
  }

  getValue(event: Event): string
  {
    return (event.target as HTMLInputElement).value
  }



  updateProfile(username: string, email: string, password: string, phone: string): void
  {

    console.log(username, email, password, phone)


    this.userSerive.updateProile(username, email, password, phone).subscribe(
      user =>
      {
        if (!user)
          return;

        alert(`Update successfull ${user.username}, please login use you email and password.`);
        this.router.navigateByUrl("login")
      }
    );
  }

}
