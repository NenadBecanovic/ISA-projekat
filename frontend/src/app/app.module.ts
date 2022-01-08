import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {LoginComponent} from "./login/login.component";
import { NgProgressModule } from 'ngx-progressbar';
import { RegistrationComponent } from './registration/registration.component';
import { EmailActivationComponent } from './email-activation/email-activation.component';
import { AlertModule } from 'ngx-alerts';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HomePageModule} from "./home-page/home-page-module/home-page.module";
import { HomeDashboardComponent } from './home-page/home-dashboard/home-dashboard.component';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import {TokenInterceptor} from "./interceptor/token-interceptor";
import {RouterModule} from "@angular/router";
import { HouseProfileForHouseOwnerComponent } from './house-profile-for-house-owner/house-profile-for-house-owner.component';
import { AdventureProfileComponent } from './adventure-profile/adventure-profile.component';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule } from 'ngx-bootstrap/modal';
import { MatSliderModule } from '@angular/material/slider';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule  } from '@angular/material/icon';
import { MatGridListModule} from '@angular/material/grid-list';
import { MatCardModule} from '@angular/material/card';
import { MatBadgeModule} from '@angular/material/badge';
import { MatCarouselModule } from 'ng-mat-carousel';
import { MatDialogModule} from '@angular/material/dialog';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule} from '@angular/material/input';
import { MatCheckboxModule} from '@angular/material/checkbox';
import { MatStepperModule} from '@angular/material/stepper';
import { MatRadioModule} from '@angular/material/radio';
import { MatDividerModule} from '@angular/material/divider';
import { MatListModule} from '@angular/material/list';
import { MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {MatMenuModule} from '@angular/material/menu';
import { MatSelectModule} from '@angular/material/select';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatTabsModule} from '@angular/material/tabs';
import { SharedModule } from './home-page/shared/shared.module';
import { FishingInstructorProfileComponent } from './fishing-instructor-profile/fishing-instructor-profile.component';
import { CalendarDialogComponent } from './fishing-instructor-profile/calendar-dialog/calendar-dialog.component';
import { DemoUtilsModule } from './fishing-instructor-profile/calendar-dialog/demo-utils/calendar.module';
import { DefineAvaibilityPeriodComponent } from './fishing-instructor-profile/define-avaibility-period/define-avaibility-period.component';
import { MakeReservationDialogComponent } from './fishing-instructor-profile/make-reservation-dialog/make-reservation-dialog.component';
import { AddAdventureDialogComponent } from './fishing-instructor-profile/add-adventure-dialog/add-adventure-dialog.component';
import {ClientModule} from "./clientHome/client-module/client-module";
import { BoatProfileForBoatOwnerComponent } from './boat-profile-for-boat-owner/boat-profile-for-boat-owner.component';
import {AgmCoreModule} from '@agm/core';
import { AddActionHouseProfileComponent } from './add-action-house-profile/add-action-house-profile.component';
import { ModifyHouseProfileComponent } from './modify-house-profile/modify-house-profile.component';
import { AddActionBoatProfileComponent } from './add-action-boat-profile/add-action-boat-profile.component';
import { ModifyBoatProfileComponent } from './modify-boat-profile/modify-boat-profile.component';
import { EditHouseActionComponent } from './edit-house-action/edit-house-action.component';
import { HomePageHouseOwnerComponent } from './home-page-house-owner/home-page-house-owner.component';
import { AddHouseComponent } from './add-house/add-house.component';
import { AddImageDialogComponent } from './adventure-profile/add-image-dialog/add-image-dialog.component';
import { CalendarDialogHouseComponent } from './house-profile-for-house-owner/calendar-dialog/calendar-dialog-house.component';
import { DefineUnavailablePeriodHouseComponent } from './define-unavailable-period-house/define-unavailable-period-house.component';
import {DatePipe} from "@angular/common";
import { CreateReservationForClientComponent } from './create-reservation-for-client/create-reservation-for-client.component';
import { GuestProfileComponent } from './guest-profile/guest-profile.component';
import { HouseReportComponent } from './house-report/house-report.component';
import { HomePageBoatOwnerComponent } from './home-page-boat-owner/home-page-boat-owner.component';
import { DefineUnavailablePeriodBoatComponent } from './define-unavailable-period-boat/define-unavailable-period-boat.component';
import { GuestProfileBoatComponent } from './guest-profile-boat/guest-profile-boat.component';
import { CreateReservationForClientBoatComponent } from './create-reservation-for-client-boat/create-reservation-for-client-boat.component';


const MaterialComponents = [
  MatSliderModule,
  MatToolbarModule,
  MatButtonModule,
  MatIconModule,
  MatGridListModule,
  MatCardModule,
  MatBadgeModule,
  MatCarouselModule.forRoot(),
  MatDialogModule,
  MatFormFieldModule,
  MatInputModule,
  MatCheckboxModule,
  MatStepperModule,
  MatRadioModule,
  MatDividerModule,
  MatListModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatSelectModule,
  MatSnackBarModule,
  MatTabsModule,
  MatMenuModule
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    EmailActivationComponent,
    HouseProfileForHouseOwnerComponent,
    AdventureProfileComponent,
    FishingInstructorProfileComponent,
    CalendarDialogComponent,
    DefineAvaibilityPeriodComponent,
    MakeReservationDialogComponent,
    AddAdventureDialogComponent,
    BoatProfileForBoatOwnerComponent,
    AddActionHouseProfileComponent,
    ModifyHouseProfileComponent,
    AddActionBoatProfileComponent,
    ModifyBoatProfileComponent,
    EditHouseActionComponent,
    HomePageHouseOwnerComponent,
    AddHouseComponent,
    AddImageDialogComponent,
    DefineUnavailablePeriodHouseComponent,
    CreateReservationForClientComponent,
    GuestProfileComponent,
    HouseReportComponent,
    CalendarDialogHouseComponent,
    BoatProfileForBoatOwnerComponent,
    HomePageBoatOwnerComponent,
    DefineUnavailablePeriodBoatComponent,
    GuestProfileBoatComponent,
    CreateReservationForClientBoatComponent,
  ],
  imports: [
    RouterModule,
    ClientModule,
    HomePageModule,
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    NgProgressModule,
    MaterialComponents,
    BrowserAnimationsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    Ng2SearchPipeModule,
    DemoUtilsModule,
    MatCarouselModule,
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    AlertModule.forRoot({maxMessages: 5, timeout: 5000, positionX: "right", positionY: "top"}),
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDhUaf84F4NwNDUjw-feRmJusep1T1EB6s'   // za google maps
    }),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
