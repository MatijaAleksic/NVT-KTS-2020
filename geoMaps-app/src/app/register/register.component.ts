import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../service/httpclient.service';
import { Observable } from 'rxjs';
import { HttpclientService } from '../service/httpclient.service'


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user : User = new User("","","","","");

  constructor(private httpClientService: HttpclientService) { }

  ngOnInit(): void {
  }

  registerUser(): void {
    this.httpClientService.signUp(this.user)
      .subscribe( data => {alert("User added successfuly.")});
  }

}
