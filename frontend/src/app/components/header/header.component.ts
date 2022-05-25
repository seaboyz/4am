import { Component, OnInit } from '@angular/core';
import { AuthService } from "src/app/services/auth.service";
import { UserService } from "src/app/services/user.service";
import { User } from "src/app/shared/interface/user";



@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit
{
  user: User | undefined;

  constructor(private authService: AuthService) { }

  ngOnInit()
  {
    this.authService.currentUser.subscribe(currentUser => this.user = currentUser)
  }

  signOut()
  {
    this.authService.signOut();
  }

}