import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";
import {EmailActivationComponent} from "./email-activation/email-activation.component";
import {HomePageComponent} from "./home-page/home-page-component/home-page.component";
import {HomeDashboardComponent} from "./home-page/home-dashboard/home-dashboard.component";
import { AdventureProfileComponent } from './adventure-profile/adventure-profile.component';
import { FishingInstructorProfileComponent } from './fishing-instructor-profile/fishing-instructor-profile.component';

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'registration', component: RegistrationComponent},
  {path:'email-activation', component: EmailActivationComponent},
  { path:'', component: HomePageComponent, children: [{path: '', component: HomeDashboardComponent}]},
  {path: 'adventure-profile', component: AdventureProfileComponent},
  {path: 'fishing-instructor', component: FishingInstructorProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
