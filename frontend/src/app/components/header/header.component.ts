import { Component, OnInit } from '@angular/core';
import { User } from "src/app/shared/interface/user";



@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit
{
  user: User | null = {
    id: "1",
    email: "john@example.com",
    displayName: "John Doe",
  };

  constructor() { }

  ngOnInit() { }


  signOut()
  {
    this.user = null;
  }

}