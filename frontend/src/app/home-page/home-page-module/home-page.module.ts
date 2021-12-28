import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SharedModule} from "../shared/shared.module";
import {HomePageComponent} from "../home-page-component/home-page.component";
import {HomeDashboardComponent} from "../home-dashboard/home-dashboard.component";
import {BrowserModule} from "@angular/platform-browser";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatCardModule } from '@angular/material/card';
import { MatCarouselModule } from '@ngbmodule/material-carousel';
import { FlexLayoutModule } from '@angular/flex-layout';


@NgModule({
  declarations: [ // deklaracije komponenti koje ce se koristiti (prikazivati) u nasem glavnom prozoru
    HomePageComponent,
    HomeDashboardComponent
  ],
  imports: [
    CommonModule,
    SharedModule,  // importujemo ceo modul u kome se nalaze header i footer komponente
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDividerModule,
    MatCardModule,
    MatCarouselModule,
    FlexLayoutModule,
  ],
  // providers:[
  //   DashboardService,
  //   {
  //     provide: HTTP_INTERCEPTORS,
  //     useClass: TokenInterceptor,
  //     multi: true,
  //   }
  // ],
  // schemas: [
  //   NO_ERRORS_SCHEMA
  // ]
})
export class HomePageModule { }
