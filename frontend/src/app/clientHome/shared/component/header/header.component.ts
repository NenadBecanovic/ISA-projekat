import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Router} from "@angular/router";
import {AuthentificationService} from "../../../../authentification/authentification.service";
import {MatDialog} from "@angular/material/dialog";
import {ClientProfileComponent} from "../../../client-profile/client-profile.component";
import {DeleteAccountComponent} from "../../../delete-account/delete-account.component";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Output() toggleSideBarForMe: EventEmitter<any> = new EventEmitter()

  constructor(public dialog: MatDialog, private _router: Router, private _authentification: AuthentificationService) { }

  ngOnInit(): void {
  }

  toggleSideBar(){
    this.toggleSideBarForMe.emit();
    setTimeout(() => {
      window.dispatchEvent(
        new Event('resize')
      );
    }, 300);
  }


  logout() {
    this._authentification.logout();
    this._router.navigate([''])
  }

  profil() {
    const dialogRef = this.dialog.open(ClientProfileComponent, {
      width: '600px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  delete() {
    const dialogRef = this.dialog.open(DeleteAccountComponent,{
      width: '400px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }
}
