import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";
import {EmailActivationComponent} from "./email-activation/email-activation.component";
import {HomePageComponent} from "./home-page/home-page-component/home-page.component";
import {HomeDashboardComponent} from "./home-page/home-dashboard/home-dashboard.component";
import {HomeHouseComponent} from "./home-page/home-house/home-house.component";
import {ClientHomePageComponent} from "./client-home-page/client-home-page.component";
import {HouseProfileForHouseOwnerComponent} from "./house-profile-for-house-owner/house-profile-for-house-owner.component";
import { AdventureProfileComponent } from './adventure-profile/adventure-profile.component';
import {BoatProfileForBoatOwnerComponent} from "./boat-profile-for-boat-owner/boat-profile-for-boat-owner.component";
import {AddActionHouseProfileComponent} from "./add-action-house-profile/add-action-house-profile.component";
import {ModifyHouseProfileComponent} from "./modify-house-profile/modify-house-profile.component";


const routes: Routes = [
  {path:'', component: HomePageComponent, children: [{path: '', component: HomeDashboardComponent}, {path: 'house', component: HomeHouseComponent}]},
  {path:'login', component: LoginComponent},
  {path:'register', component: RegistrationComponent},
  {path:'email-activation', component: EmailActivationComponent},
  { path:'house-profile-for-house-owner', component: HouseProfileForHouseOwnerComponent},
  { path:'', component: HomePageComponent, children: [{path: '', component: HomeDashboardComponent}]},
  {path: 'adventure-profile', component: AdventureProfileComponent},
  { path:'boat-profile-for-boat-owner', component: BoatProfileForBoatOwnerComponent},
  { path:'add-action-house-profile/:id', component: AddActionHouseProfileComponent},
  { path:'modify-house-profile/:id', component: ModifyHouseProfileComponent},
  {path:'client', component: ClientHomePageComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
