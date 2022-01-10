import {CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ClientHomePageComponent} from "../client-home-page/client-home-page.component";
import {DashboardComponent} from "../dashboard/dashboard.component";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../shared/shared.module";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatDividerModule} from "@angular/material/divider";
import {FlexModule} from "@angular/flex-layout";
import {MatCardModule} from "@angular/material/card";
import {DashboardService} from "../dashboard.service";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {TokenInterceptor} from "../../auth/interceptor/token-interceptor";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatIconModule} from "@angular/material/icon";
import {MatRadioModule} from "@angular/material/radio";
import {ClientProfileComponent} from "../client-profile/client-profile.component";
import {ClientHousesComponent} from "../client-houses/client-houses.component";
import {ClientBoatsComponent} from "../client-boats/client-boats.component";
import {ClientAdventuresComponent} from "../client-adventures/client-adventures.component";
import {ClientHouseReservationsComponent} from "../client-house-reservations/client-house-reservations.component";
import {FormsModule} from "@angular/forms";
import {AlertModule} from "ngx-alerts";
import {DeleteAccountComponent} from "../delete-account/delete-account.component";
import {MatButtonModule} from "@angular/material/button";
import {ClientHouseComponent} from "../client-house/client-house.component";
import {AgmCoreModule} from "@agm/core";
import {MatCarouselModule} from "ng-mat-carousel";
import {YesNoDialogComponent} from "../yes-no-dialog/yes-no-dialog.component";
import {ClientBoatComponent} from "../client-boat/client-boat.component";
import {ClientSubscriptionsComponent} from "../client-subscriptions/client-subscriptions.component";
import {
  HouseReservationHistoryComponent
} from "../reservation/house-reservation-history/house-reservation-history.component";
import {CreateFeedbackComponent} from "../dialog/create-feedback/create-feedback.component";
import {CreateAppealEntityComponent} from "../dialog/create-appeal-entity/create-appeal-entity.component";
import {MatSelectModule} from "@angular/material/select";


@NgModule({
  declarations: [
    ClientHomePageComponent,
    DashboardComponent,
    ClientProfileComponent,
    ClientHousesComponent,
    ClientBoatsComponent,
    ClientAdventuresComponent,
    ClientHouseReservationsComponent,
    DeleteAccountComponent,
    ClientHouseComponent,
    YesNoDialogComponent,
    ClientBoatComponent,
    ClientSubscriptionsComponent,
    HouseReservationHistoryComponent,
    CreateFeedbackComponent,
    CreateAppealEntityComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule,
    MatSidenavModule,
    MatDividerModule,
    FlexModule,
    MatCardModule,
    MatTableModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatGridListModule,
    MatIconModule,
    MatRadioModule,
    FormsModule,
    AlertModule,
    MatButtonModule,
    AgmCoreModule,
    MatCarouselModule,
    MatSelectModule

  ],
  providers:[
    DashboardService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    }
  ],
  schemas: [
    NO_ERRORS_SCHEMA
  ]
})
export class ClientModule { }
