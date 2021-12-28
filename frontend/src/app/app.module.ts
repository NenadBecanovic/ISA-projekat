import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {LoginComponent} from "./login/login.component";
import { NgProgressModule } from 'ngx-progressbar';
import { RegistrationComponent } from './registration/registration.component';
import { EmailActivationComponent } from './email-activation/email-activation.component';
import { AlertModule } from 'ngx-alerts';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HomePageModule} from "./home-page/home-page-module/home-page.module";
import { HomeDashboardComponent } from './home-page/home-dashboard/home-dashboard.component';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { AdventureProfileComponent } from './adventure-profile/adventure-profile.component';
//import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
//import { TooltipModule } from 'ngx-bootstrap/tooltip';
//import { ModalModule } from 'ngx-bootstrap/modal';
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
import { HeaderComponent } from './home-page/shared/header/header.component';
import { FooterComponent } from './home-page/shared/footer/footer.component';
import { SharedModule } from './home-page/shared/shared.module';
import { FishingInstructorProfileComponent } from './fishing-instructor-profile/fishing-instructor-profile.component';
import { CalendarDialogComponent } from './fishing-instructor-profile/calendar-dialog/calendar-dialog.component';
import { DemoUtilsModule } from './fishing-instructor-profile/calendar-dialog/demo-utils/calendar.module';
import { DefineAvaibilityPeriodComponent } from './fishing-instructor-profile/define-avaibility-period/define-avaibility-period.component';


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
    AdventureProfileComponent,
    FishingInstructorProfileComponent,
    CalendarDialogComponent,
    DefineAvaibilityPeriodComponent
   ],
  imports: [
    HomePageModule,
    BrowserModule,
    AppRoutingModule,
    SharedModule,
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
    //BsDropdownModule.forRoot(),
    //TooltipModule.forRoot(),
    //ModalModule.forRoot(),
    AlertModule.forRoot({ maxMessages: 5, timeout: 5000,positionX: "right", positionY: "top" }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
