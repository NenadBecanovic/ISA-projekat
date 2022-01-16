import {CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ClientHomePageComponent} from "../client-home-page/client-home-page.component";
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
import {ClientProfileComponent} from "../dialog/client-profile/client-profile.component";
import {ClientHousesComponent} from "../client-house-page/client-houses/client-houses.component";
import {ClientBoatsComponent} from "../client-boat-page/client-boats/client-boats.component";
import {FormsModule} from "@angular/forms";
import {AlertModule} from "ngx-alerts";
import {DeleteAccountComponent} from "../dialog/delete-account/delete-account.component";
import {MatButtonModule} from "@angular/material/button";
import {ClientHouseComponent} from "../client-house-page/client-house/client-house.component";
import {AgmCoreModule} from "@agm/core";
import {MatCarouselModule} from "ng-mat-carousel";
import {ClientBoatComponent} from "../client-boat-page/client-boat/client-boat.component";
import {ClientSubscriptionsComponent} from "../client-subscriptions/client-subscriptions.component";
import {
  HouseReservationHistoryComponent
} from "../reservation/house-reservation-history/house-reservation-history.component";
import {CreateFeedbackComponent} from "../dialog/create-feedback/create-feedback.component";
import {CreateAppealEntityComponent} from "../dialog/create-appeal-entity/create-appeal-entity.component";
import {MatSelectModule} from "@angular/material/select";
import {
  BoatReservationHistoryComponent
} from "../reservation/boat-reservation-history/boat-reservation-history.component";
import {FutureReservationComponent} from "../reservation/future-reservation/future-reservation.component";
import {ClientInstructorsComponent} from "../client-instructors/client-instructors.component";
import {
  InstructorReservationHistoryComponent
} from "../reservation/instructor-reservation-history/instructor-reservation-history.component";
import {CreateReservationBoatComponent} from "../dialog/create-reservation-boat/create-reservation-boat.component";
import {CreateReservationHouseComponent} from "../dialog/create-reservation-house/create-reservation-house.component";
import {
  CreateReservationInstructorComponent
} from "../dialog/create-reservation-instructor/create-reservation-instructor.component";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {ClientInstructorComponent} from "../client-instructor/client-instructor.component";
import {ClientAdventureComponent} from "../client-adventure/client-adventure.component";


@NgModule({
  declarations: [
    ClientHomePageComponent,
    ClientProfileComponent,
    ClientHousesComponent,
    ClientBoatsComponent,
    DeleteAccountComponent,
    ClientHouseComponent,
    ClientBoatComponent,
    ClientSubscriptionsComponent,
    HouseReservationHistoryComponent,
    CreateFeedbackComponent,
    CreateAppealEntityComponent,
    BoatReservationHistoryComponent,
    FutureReservationComponent,
    ClientInstructorsComponent,
    InstructorReservationHistoryComponent,
    CreateReservationBoatComponent,
    CreateReservationHouseComponent,
    CreateReservationInstructorComponent,
    ClientInstructorComponent,
    ClientAdventureComponent,
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
    MatSelectModule,
    MatCheckboxModule

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
