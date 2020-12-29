import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { HttpclientService } from '../service/httpclient.service';
//import { HttpclientService, HttpClientService } from '../service/httpclient.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  username = ''
  password = ''
  firstName = ''
  lastName = ''
  email = ''

  constructor(private HttpClientService:HttpclientService) {

  }

  ngOnInit(): void {
  }

  handleSuccessfulresponse() //response:HttpResponse)
  {
    //this.employees=response;
  }

  //createEmployee(): void {
  //  this.httpClientService.createEmployee(this.user)
  //      .subscribe( data => {
  //        alert("Employee created successfully.");
  //      });

}
