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
import { MatCarouselModule } from 'ng-mat-carousel';
import { FlexLayoutModule } from '@angular/flex-layout';
import {HomeHouseComponent} from "../home-house/home-house.component";
import {RouterModule} from "@angular/router";
import {ScrollingModule} from "@angular/cdk/scrolling";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatIconModule} from "@angular/material/icon";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {TokenInterceptor} from "../../interceptor/token-interceptor";
import {MatRadioModule} from "@angular/material/radio";
import {HomeBoatComponent} from "../home-boat/home-boat.component";
import {HomeAdventureComponent} from "../home-adventure/home-adventure.component";




@NgModule({
  declarations: [
    HomePageComponent,
    HomeDashboardComponent,
    HomeHouseComponent,
    HomeBoatComponent,
    HomeAdventureComponent
  ],
    imports: [
        RouterModule,
        CommonModule,
        SharedModule,
        BrowserModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatDividerModule,
        MatCardModule,
        MatCarouselModule,
        FlexLayoutModule,
        ScrollingModule,
        MatGridListModule,
        MatIconModule,
        MatRadioModule,
    ],

  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    }
  ],

})

export class HomePageModule { }
