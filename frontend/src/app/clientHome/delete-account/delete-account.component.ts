import { Component, OnInit } from '@angular/core';
import {DeleteRequest} from "../../model/delete-request";
import {AuthentificationService} from "../../auth/authentification/authentification.service";
import {MyUserService} from "../../service/my-user.service";
import {MyUser} from "../../model/my-user";
import {AlertService} from "ngx-alerts";

@Component({
  selector: 'app-delete-account',
  templateUrl: './delete-account.component.html',
  styleUrls: ['./delete-account.component.css']
})
export class DeleteAccountComponent implements OnInit {

  request: DeleteRequest = new DeleteRequest();

  constructor(private _authentification: AuthentificationService, private _myUserService: MyUserService, private alertService: AlertService) { }

  ngOnInit(): void {
  }

  ok(): void {
    this.request.email = this._authentification.getCurrentUser().email;
    this._myUserService.createDeleteRequest(this.request).subscribe(   // subscribe - da bismo dobili odgovor beka
      (request: DeleteRequest) => {
        this.alertService.success('Uspjesno poslat zahtev');
      },
      (error) => {
        this.alertService.danger('Pokusajte ponovo');
      },
    )
  }

  doTextareaValueChange($event: Event) {
    try {
      // @ts-ignore
      this.request.description = $event.target.value;
    } catch(e) {
      console.info('could not set textarea-value');
    }
  }
}
