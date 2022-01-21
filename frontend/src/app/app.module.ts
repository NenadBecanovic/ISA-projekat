import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {LoginComponent} from "./auth/login/login.component";
import { NgProgressModule } from 'ngx-progressbar';
import { RegistrationComponent } from './auth/registration/registration.component';
import { EmailActivationComponent } from './email-activation/email-activation.component';
import { AlertModule } from 'ngx-alerts';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HomePageModule} from "./home-page/home-page-module/home-page.module";
import { HomeDashboardComponent } from './home-page/home-dashboard/home-dashboard.component';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import {TokenInterceptor} from "./auth/interceptor/token-interceptor";
import {RouterModule} from "@angular/router";
import { HouseProfileForHouseOwnerComponent } from './house-owner/house-profile-for-house-owner/house-profile-for-house-owner.component';
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
import { BoatProfileForBoatOwnerComponent } from './boat-owner/boat-profile-for-boat-owner/boat-profile-for-boat-owner.component';
import {AgmCoreModule} from '@agm/core';
import { AddActionHouseProfileComponent } from './house-owner/add-action-house-profile/add-action-house-profile.component';
import { ModifyHouseProfileComponent } from './house-owner/modify-house-profile/modify-house-profile.component';
import { AddActionBoatProfileComponent } from './boat-owner/add-action-boat-profile/add-action-boat-profile.component';
import { ModifyBoatProfileComponent } from './boat-owner/modify-boat-profile/modify-boat-profile.component';
import { EditHouseActionComponent } from './house-owner/edit-house-action/edit-house-action.component';
import { HomePageHouseOwnerComponent } from './house-owner/home-page-house-owner/home-page-house-owner.component';
import { AddHouseComponent } from './house-owner/add-house/add-house.component';
import { AddImageDialogComponent } from './adventure-profile/add-image-dialog/add-image-dialog.component';
import { CalendarDialogHouseComponent } from './house-owner/house-profile-for-house-owner/calendar-dialog/calendar-dialog-house.component';
import { DefineUnavailablePeriodHouseComponent } from './house-owner/define-unavailable-period-house/define-unavailable-period-house.component';
import {DatePipe} from "@angular/common";
import { CreateReservationForClientComponent } from './house-owner/create-reservation-for-client/create-reservation-for-client.component';
import { GuestProfileComponent } from './house-owner/guest-profile/guest-profile.component';
import { HouseReportComponent } from './house-owner/house-report/house-report.component';
import { HomePageBoatOwnerComponent } from './boat-owner/home-page-boat-owner/home-page-boat-owner.component';
import { DefineUnavailablePeriodBoatComponent } from './boat-owner/define-unavailable-period-boat/define-unavailable-period-boat.component';
import { GuestProfileBoatComponent } from './boat-owner/guest-profile-boat/guest-profile-boat.component';
import { CreateReservationForClientBoatComponent } from './boat-owner/create-reservation-for-client-boat/create-reservation-for-client-boat.component';
import { AddBoatComponent } from './boat-owner/add-boat/add-boat.component';
import { BoatReportComponent } from './boat-owner/boat-report/boat-report.component';
import { AddFishingAdventureActionDialogComponent } from './adventure-profile/add-action-dialog/add-action-dialog.component';
import { EditAdventureProfileDialogComponent } from './adventure-profile/edit-adventure-profile-dialog/edit-adventure-profile-dialog.component';
import { BoatChartComponent } from './boat-owner/boat-chart/boat-chart.component';
import { ChartModule } from 'angular-highcharts';
import { HouseChartComponent } from './house-owner/house-chart/house-chart.component';
import { CalendarDialogBoatComponent } from './boat-owner/boat-profile-for-boat-owner/calendar-dialog-boat/calendar-dialog-boat.component';
import { AdventureReservationsDialogComponent } from './adventure-profile/adventure-reservations-dialog/adventure-reservations-dialog.component';
import { PlainHeaderComponent } from './house-owner/plain-header/plain-header.component';
import {NgbCollapseModule} from "@ng-bootstrap/ng-bootstrap";
import { AddImageHouseComponent } from './house-owner/add-image-house/add-image-house.component';
import { AddImageBoatComponent } from './boat-owner/add-image-boat/add-image-boat.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { DeleteRequestAnswerDialogComponent } from './admin-page/delete-request-answer-dialog/delete-request-answer-dialog.component';
import { AppealAnswerDialogComponent } from './admin-page/appeal-answer-dialog/appeal-answer-dialog.component';
import { ReportAnswerDialogComponent } from './admin-page/report-answer-dialog/report-answer-dialog.component';
import { DeclineNewUserDialogComponent } from './admin-page/decline-new-user-dialog/decline-new-user-dialog.component';
import { AdminRegistrationDialogComponent } from './admin-page/admin-registration-dialog/admin-registration-dialog.component';
import { EditPersonalDescriptionDialogComponent } from './fishing-instructor-profile/edit-personal-description-dialog/edit-personal-description-dialog.component';
import { EditCompanyRulesDialogComponent } from './admin-page/edit-company-rules-dialog/edit-company-rules-dialog.component';
import { InstructorReservationHistoryComponent } from './clientHome/reservation/instructor-reservation-history/instructor-reservation-history.component';
import { MatTableModule } from '@angular/material/table';
import { InstructorChartComponent } from './adventure-profile/instructor-chart/instructor-chart.component';
import { CompanyProfitDialogComponent } from './admin-page/company-profit-dialog/company-profit-dialog.component';
import { NewAdminPasswordDialogComponent } from './admin-page/new-admin-password-dialog/new-admin-password-dialog.component';
import { AdventureGuestProfileComponent } from './adventure-profile/adventure-guest-profile/adventure-guest-profile.component';

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
    EditAdventureProfileDialogComponent,
    BoatProfileForBoatOwnerComponent,
    HomePageBoatOwnerComponent,
    DefineUnavailablePeriodBoatComponent,
    GuestProfileBoatComponent,
    CreateReservationForClientBoatComponent,
    AddBoatComponent,
    BoatReportComponent,
    AddFishingAdventureActionDialogComponent,
    AdventureReservationsDialogComponent,
    AdminPageComponent,
    DeleteRequestAnswerDialogComponent,
    AppealAnswerDialogComponent,
    ReportAnswerDialogComponent,
    DeclineNewUserDialogComponent,
    BoatChartComponent,
    HouseChartComponent,
    CalendarDialogBoatComponent,
    EditPersonalDescriptionDialogComponent,
    AdventureReservationsDialogComponent,
    PlainHeaderComponent,
    AddImageHouseComponent,
    AddImageBoatComponent,
    AdminRegistrationDialogComponent,
    EditCompanyRulesDialogComponent,
    InstructorChartComponent,
    CompanyProfitDialogComponent,
    NewAdminPasswordDialogComponent,
    AdventureGuestProfileComponent
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
        ChartModule,
        NgbCollapseModule,
        MatTableModule,
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
