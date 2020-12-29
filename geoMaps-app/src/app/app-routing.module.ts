import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { AuthGaurdService } from './service/auth-guard.service';
import { UserComponent } from './user/user.component'
import { RegisterComponent } from './register/register.component'
import { HeaderComponent } from './header/header.component'
import { FooterComponent } from './footer/footer.component'

const routes: Routes = [
  //{ path: '', component: LoginComponent },
  { path: '/sign-up', component: RegisterComponent },
  { path: '', component: LogoutComponent },
  { path: '', component: UserComponent },
  { path: '', component: HeaderComponent },
  { path: '', component: FooterComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

