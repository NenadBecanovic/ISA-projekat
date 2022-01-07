import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HeaderComponent} from "./header/header.component";
import { NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';
import { FooterComponent } from './footer/footer.component';
import {MatDividerModule} from "@angular/material/divider";
import {MatMenuModule} from '@angular/material/menu';


@NgModule({
    declarations: [
        HeaderComponent,
        FooterComponent
    ],
    exports: [
        FooterComponent,
        HeaderComponent
    ],
  imports: [
    NgbCollapseModule,
    CommonModule,
    MatDividerModule,
    MatMenuModule
  ]
})
export class SharedModule { }
