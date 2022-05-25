import { Component, OnInit } from '@angular/core';
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

  constructor(private userService: UserService) { }

  ngOnInit()
  {
    this.userService.currentUser.subscribe(currentUser => this.user = currentUser)
  }

  signOut()
  {
    this.userService.signOut();
  }

}