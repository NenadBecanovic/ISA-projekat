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
import {FormsModule} from "@angular/forms";
import {BoatComponent} from "../boat/boat.component";
import {AdventureComponent} from "../adventure/adventure.component";
import {HouseComponent} from "../house/house.component";
import {AgmCoreModule} from "@agm/core";




@NgModule({
  declarations: [
    HomePageComponent,
    HomeDashboardComponent,
    HomeHouseComponent,
    HomeBoatComponent,
    HomeAdventureComponent,
    AdventureComponent,
    HouseComponent,
    BoatComponent
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
        FormsModule,
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

})

export class HomePageModule { }
