import { Injectable, ɵSWITCH_COMPILE_NGMODULE__POST_R3__ } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { EmailValidator } from '@angular/forms';

export class User{
  constructor(
    public username:string,
    public password:string,
    public firstName:string,
    public lastName:string,
    public email:string,
  ){ }

}
@Injectable({
  providedIn: 'root'
})
export class HttpclientService {

  constructor(private httpClient:HttpClient ) { }


registerUser(user: User){
  return this.httpClient.post("localhost:8080/sing-up", user, {
      headers: new HttpHeaders({
       'Content-Type': 'aplication/json'
      })
    })
    //.pipe(catchError(this.handleError));
  }
}

//getUsers()
//{
//  console.log("test call");
//  retun this.httpClient.get<User[]>('gttp://localhost:8080/users');
//}
