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
import {ClientProfileComponent} from "./clientHome/dialog/client-profile/client-profile.component";
import {ClientHousesComponent} from "./clientHome/client-house-page/client-houses/client-houses.component";
import {ClientBoatsComponent} from "./clientHome/client-boat-page/client-boats/client-boats.component";
import {ClientHouseComponent} from "./clientHome/client-house-page/client-house/client-house.component";
import {ClientBoatComponent} from "./clientHome/client-boat-page/client-boat/client-boat.component";
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
import { AdminPageComponent } from './admin-page/admin-page.component';
import {BoatChartComponent} from "./boat-owner/boat-chart/boat-chart.component";
import {HouseChartComponent} from "./house-owner/house-chart/house-chart.component";
import {
  BoatReservationHistoryComponent
} from "./clientHome/reservation/boat-reservation-history/boat-reservation-history.component";
import {FutureReservationComponent} from "./clientHome/reservation/future-reservation/future-reservation.component";
import {DeleteImageDialogComponent} from "./adventure-profile/delete-image-dialog/delete-image-dialog.component";
import {HomeInstructorComponent} from "./home-page/home-instructor/home-instructor.component";
import {InstructorComponent} from "./home-page/instructor/instructor.component";
import {ClientInstructorsComponent} from "./clientHome/client-instructors/client-instructors.component";
import {
  InstructorReservationHistoryComponent
} from "./clientHome/reservation/instructor-reservation-history/instructor-reservation-history.component";
import {AuthGuardGuard} from "./auth-guard.guard";
import {InstructorChartComponent} from "./adventure-profile/instructor-chart/instructor-chart.component";
import {ClientInstructorComponent} from "./clientHome/client-instructor/client-instructor.component";
import {ClientAdventureComponent} from "./clientHome/client-adventure/client-adventure.component";
import { AdventureGuestProfileComponent } from './adventure-profile/adventure-guest-profile/adventure-guest-profile.component';



const routes: Routes = [
  {path:'', component: HomePageComponent, children: [{path: '', component: HomeDashboardComponent}, {path: 'houses', component: HomeHouseComponent},
      {path: 'boats', component: HomeBoatComponent}, {path: 'adventures', component: HomeAdventureComponent}, {path: 'house/:id', component: HouseComponent},
      {path: 'adventure/:id', component: AdventureComponent},{path: 'boat/:id', component: BoatComponent}, {path: 'instructors', component: HomeInstructorComponent},
      {path: 'instructor/:id', component: InstructorComponent}]},
  {path:'login', component: LoginComponent},
  {path:'register', component: RegistrationComponent},
  {path:'email-activation', component: EmailActivationComponent},
  {path:'client', component: ClientHomePageComponent, canActivate: [AuthGuardGuard],data: {role: 'ROLE_USER'}, children: [{path: '', component: ClientHousesComponent}, {path: 'boats', component: ClientBoatsComponent},   {path: 'house/:id', component: ClientHouseComponent},
      {path: 'boat/:id', component: ClientBoatComponent}, {path: 'subscriptions', component: ClientSubscriptionsComponent},{path: 'instructors', component: ClientInstructorsComponent},
      {path: 'houseReservation', component: HouseReservationHistoryComponent}, {path: 'boatReservation', component: BoatReservationHistoryComponent}, {path: 'futureReservations', component: FutureReservationComponent},
      {path: 'instructorReservation', component: InstructorReservationHistoryComponent}]},
  { path:'house-profile-for-house-owner/:id', component: HouseProfileForHouseOwnerComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_HOUSE_OWNER'}},
  {path: 'instructorReservation', component: InstructorReservationHistoryComponent}, {path: 'instructor/:id', component: ClientInstructorComponent},  {path: 'instructor/adventure/:id', component: ClientAdventureComponent}]},
  { path:'', component: HomePageComponent, children: [{path: '', component: HomeDashboardComponent}]},
  {path: 'adventure-profile/:id', component: AdventureProfileComponent, canActivate: [AuthGuardGuard],data: {role: 'ROLE_INSTRUCTOR'}},
  {path: 'fishing-instructor', component: FishingInstructorProfileComponent, canActivate: [AuthGuardGuard],data: {role: 'ROLE_INSTRUCTOR'}},
  { path:'boat-profile-for-boat-owner/:id', component: BoatProfileForBoatOwnerComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_BOAT_OWNER'}},
  { path:'add-action-house-profile/:id', component: AddActionHouseProfileComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_HOUSE_OWNER'}},
  { path:'modify-house-profile/:id', component: ModifyHouseProfileComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_HOUSE_OWNER'}},
  { path:'add-action-boat-profile/:id', component: AddActionBoatProfileComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_BOAT_OWNER'}},
  { path:'modify-boat-profile/:id', component: ModifyBoatProfileComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_BOAT_OWNER'}},
  { path:'home-page-house-owner', component: HomePageHouseOwnerComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_HOUSE_OWNER'}},
  { path:'add-house', component: AddHouseComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_HOUSE_OWNER'}},
  { path: 'define-unavailable-period-house/:id', component: DefineUnavailablePeriodHouseComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_HOUSE_OWNER'}},
  { path: 'create-reservation-for-client/:id', component: CreateReservationForClientComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_HOUSE_OWNER'}},
  { path: 'guest-profile/:id', component: GuestProfileComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_HOUSE_OWNER'}},
  { path: 'house-report/:id/:houseId', component: HouseReportComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_HOUSE_OWNER'}},
  { path: 'define-unavailable-period-boat/:id', component: DefineUnavailablePeriodBoatComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_BOAT_OWNER'}},
  { path: 'guest-profile-boat/:id', component: GuestProfileBoatComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_BOAT_OWNER'}},
  { path: 'create-reservation-for-client-boat/:id', component: CreateReservationForClientBoatComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_BOAT_OWNER'}},
  { path:'home-page-boat-owner', component: HomePageBoatOwnerComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_BOAT_OWNER'}},
  { path:'add-boat', component: AddBoatComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_BOAT_OWNER'}},
  { path: 'boat-report/:id/:boatId', component: BoatReportComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_BOAT_OWNER'}},
  { path: 'admin-page', component: AdminPageComponent, canActivate: [AuthGuardGuard],data: {role: 'ROLE_ADMINISTRATOR'}},
  { path: 'adventure-guest-profile/:id', component: AdventureGuestProfileComponent, canActivate: [AuthGuardGuard],data: {role: 'ROLE_INSTRUCTOR'}},
  { path: 'boat-chart/:id', component: BoatChartComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_BOAT_OWNER'}},
  { path: 'house-chart/:id', component: HouseChartComponent,canActivate: [AuthGuardGuard],data: {role: 'ROLE_HOUSE_OWNER'}},
  { path: 'instructor-chart/:id', component: InstructorChartComponent, canActivate: [AuthGuardGuard],data: {role: 'ROLE_INSTRUCTOR'}},
  { path: ' delete-image-dialog/:id', component: DeleteImageDialogComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
