import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./auth/login/login.component";
import {RegistrationComponent} from "./auth/registration/registration.component";
import {EmailActivationComponent} from "./email-activation/email-activation.component";
import {HomePageComponent} from "./home-page/home-page-component/home-page.component";
import {HomeDashboardComponent} from "./home-page/home-dashboard/home-dashboard.component";
import {HomeHouseComponent} from "./home-page/home-house/home-house.component";
import {HouseProfileForHouseOwnerComponent} from "./house-owner/house-profile-for-house-owner/house-profile-for-house-owner.component";
import { AdventureProfileComponent } from './adventure-profile/adventure-profile.component';
import { FishingInstructorProfileComponent } from './fishing-instructor-profile/fishing-instructor-profile.component';
import {HomeBoatComponent} from "./home-page/home-boat/home-boat.component";
import {ClientHomePageComponent} from "./clientHome/client-home-page/client-home-page.component";
import {DashboardComponent} from "./clientHome/dashboard/dashboard.component";
import {HomeAdventureComponent} from "./home-page/home-adventure/home-adventure.component";
import {BoatProfileForBoatOwnerComponent} from "./boat-owner/boat-profile-for-boat-owner/boat-profile-for-boat-owner.component";
import {AddActionHouseProfileComponent} from "./house-owner/add-action-house-profile/add-action-house-profile.component";
import {ModifyHouseProfileComponent} from "./house-owner/modify-house-profile/modify-house-profile.component";
import {AdventureComponent} from "./home-page/adventure/adventure.component";
import {HouseComponent} from "./home-page/house/house.component";
import {BoatComponent} from "./home-page/boat/boat.component";
import {AddActionBoatProfileComponent} from "./boat-owner/add-action-boat-profile/add-action-boat-profile.component";
import {ModifyBoatProfileComponent} from "./boat-owner/modify-boat-profile/modify-boat-profile.component";
import {EditHouseActionComponent} from "./house-owner/edit-house-action/edit-house-action.component";
import {HomePageHouseOwnerComponent} from "./house-owner/home-page-house-owner/home-page-house-owner.component";
import {ClientProfileComponent} from "./clientHome/client-profile/client-profile.component";
import {ClientHousesComponent} from "./clientHome/client-houses/client-houses.component";
import {ClientBoatsComponent} from "./clientHome/client-boats/client-boats.component";
import {ClientAdventuresComponent} from "./clientHome/client-adventures/client-adventures.component";
import {ClientHouseComponent} from "./clientHome/client-house/client-house.component";
import {ClientBoatComponent} from "./clientHome/client-boat/client-boat.component";
import {AddHouseComponent} from "./house-owner/add-house/add-house.component";
import { DefineUnavailablePeriodHouseComponent} from "./house-owner/define-unavailable-period-house/define-unavailable-period-house.component";
import {  CreateReservationForClientComponent} from "./house-owner/create-reservation-for-client/create-reservation-for-client.component";
import {GuestProfileComponent} from "./house-owner/guest-profile/guest-profile.component";
import {HouseReportComponent} from "./house-owner/house-report/house-report.component";
import {ClientSubscriptionsComponent} from "./clientHome/client-subscriptions/client-subscriptions.component";
import { HouseReservationHistoryComponent} from "./clientHome/reservation/house-reservation-history/house-reservation-history.component";
import  {DefineUnavailablePeriodBoatComponent} from "./boat-owner/define-unavailable-period-boat/define-unavailable-period-boat.component";
import {GuestProfileBoatComponent} from "./boat-owner/guest-profile-boat/guest-profile-boat.component";
import { CreateReservationForClientBoatComponent} from "./boat-owner/create-reservation-for-client-boat/create-reservation-for-client-boat.component";
import {HomePageBoatOwnerComponent} from "./boat-owner/home-page-boat-owner/home-page-boat-owner.component";
import {AddBoatComponent} from "./boat-owner/add-boat/add-boat.component";
import {BoatReportComponent} from "./boat-owner/boat-report/boat-report.component";
import {BoatChartComponent} from "./boat-owner/boat-chart/boat-chart.component";
import {HouseChartComponent} from "./house-owner/house-chart/house-chart.component";
import {AdventureChartComponent} from "./adventure-profile/adventure-chart/adventure-chart.component";


const routes: Routes = [
  {path:'', component: HomePageComponent, children: [{path: '', component: HomeDashboardComponent}, {path: 'houses', component: HomeHouseComponent},
      {path: 'boats', component: HomeBoatComponent}, {path: 'adventures', component: HomeAdventureComponent}, {path: 'house/:id', component: HouseComponent},
      {path: 'adventure/:id', component: AdventureComponent},{path: 'boat/:id', component: BoatComponent} ]},
  {path:'login', component: LoginComponent},
  {path:'register', component: RegistrationComponent},
  {path:'email-activation', component: EmailActivationComponent},
  {path:'client', component: ClientHomePageComponent, children: [{path: '', component: DashboardComponent},{path: 'profile', component: ClientProfileComponent},
      {path: 'houses', component: ClientHousesComponent}, {path: 'boats', component: ClientBoatsComponent}, {path: 'adventures', component: ClientAdventuresComponent},
      {path: 'house/:id', component: ClientHouseComponent},{path: 'boat/:id', component: ClientBoatComponent}, {path: 'subscriptions', component: ClientSubscriptionsComponent},
      {path: 'houseReservation', component: HouseReservationHistoryComponent}]},
  {path:'client', component: ClientHomePageComponent, children: [{path: '', component: DashboardComponent}]},
  { path:'house-profile-for-house-owner/:id', component: HouseProfileForHouseOwnerComponent},
  { path:'', component: HomePageComponent, children: [{path: '', component: HomeDashboardComponent}]},
  {path: 'adventure-profile/:id', component: AdventureProfileComponent},
  {path: 'fishing-instructor', component: FishingInstructorProfileComponent},
  { path:'boat-profile-for-boat-owner/:id', component: BoatProfileForBoatOwnerComponent},
  { path:'add-action-house-profile/:id', component: AddActionHouseProfileComponent},
  { path:'modify-house-profile/:id', component: ModifyHouseProfileComponent},
  { path:'add-action-boat-profile/:id', component: AddActionBoatProfileComponent},
  { path:'modify-boat-profile/:id', component: ModifyBoatProfileComponent},
  { path:'edit-house-action/:id/:houseId', component: EditHouseActionComponent},
  { path:'home-page-house-owner', component: HomePageHouseOwnerComponent},
  { path:'add-house', component: AddHouseComponent},
  { path: 'define-unavailable-period-house/:id', component: DefineUnavailablePeriodHouseComponent},
  { path: 'create-reservation-for-client/:id', component: CreateReservationForClientComponent},
  { path: 'guest-profile/:id', component: GuestProfileComponent},
  { path: 'house-report/:id/:houseId', component: HouseReportComponent},
  { path: 'define-unavailable-period-boat/:id', component: DefineUnavailablePeriodBoatComponent},
  { path: 'guest-profile-boat/:id', component: GuestProfileBoatComponent},
  { path: 'create-reservation-for-client-boat/:id', component: CreateReservationForClientBoatComponent},
  { path:'home-page-boat-owner', component: HomePageBoatOwnerComponent},
  { path:'add-boat', component: AddBoatComponent},
  { path: 'boat-report/:id/:boatId', component: BoatReportComponent},
  { path: 'boat-chart/:id', component: BoatChartComponent},
  { path: 'house-chart/:id', component: HouseChartComponent},
  { path: 'adventure-chart/:id', component: AdventureChartComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
