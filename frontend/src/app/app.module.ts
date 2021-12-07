import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

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





@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    EmailActivationComponent

  ],
  imports: [
    HomePageModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgProgressModule,
    BrowserAnimationsModule,
    AlertModule.forRoot({ maxMessages: 5, timeout: 5000,positionX: "right", positionY: "top" }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
