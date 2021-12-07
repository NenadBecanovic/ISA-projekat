import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";
import {EmailActivationComponent} from "./email-activation/email-activation.component";
import {HomePageComponent} from "./home-page/home-page-component/home-page.component";
import {HomeDashboardComponent} from "./home-page/home-dashboard/home-dashboard.component";

const routes: Routes = [
  {path:'halo', component: LoginComponent},
  {path:'register', component: RegistrationComponent},
  {path:'email-activation', component: EmailActivationComponent},
  { path:'', component: HomePageComponent, children: [{path: '', component: HomeDashboardComponent}]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
