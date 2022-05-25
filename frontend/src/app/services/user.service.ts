import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { BehaviorSubject, catchError, Observable, retry, throwError } from 'rxjs';
import { User } from "../shared/interface/user";

@Injectable({
  providedIn: 'root'
})
export class UserService implements OnInit
{


  baseUrl: string = "http://localhost:8080/users";

  constructor(private http: HttpClient)
  {


  }

  ngOnInit(): void
  {

  }





}
