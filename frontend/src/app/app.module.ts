import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
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
import { MatSelectModule} from '@angular/material/select';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatTabsModule} from '@angular/material/tabs';
import { SharedModule } from './home-page/shared/shared.module';
import {ClientModule} from "./clientHome/client-module/client-module";
import { BoatProfileForBoatOwnerComponent } from './boat-profile-for-boat-owner/boat-profile-for-boat-owner.component';
import {AgmCoreModule} from '@agm/core';
import { AddActionHouseProfileComponent } from './add-action-house-profile/add-action-house-profile.component';
import { ModifyHouseProfileComponent } from './modify-house-profile/modify-house-profile.component';
import { AdventureComponent } from './home-page/adventure/adventure.component';
import { HouseComponent } from './home-page/house/house.component';
import { BoatComponent } from './home-page/boat/boat.component';

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
  MatTabsModule
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    EmailActivationComponent,
    HouseProfileForHouseOwnerComponent,
    AdventureProfileComponent,
    BoatProfileForBoatOwnerComponent,
    AddActionHouseProfileComponent,
    ModifyHouseProfileComponent,
    AdventureComponent,
    HouseComponent,
    BoatComponent
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
    AlertModule.forRoot({maxMessages: 5, timeout: 5000, positionX: "right", positionY: "top"}),
    MatCarouselModule,
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    AlertModule.forRoot({maxMessages: 5, timeout: 5000, positionX: "right", positionY: "top"}),
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDhUaf84F4NwNDUjw-feRmJusep1T1EB6s'   // za google maps
    })
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
