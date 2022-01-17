import { Component, OnInit } from '@angular/core';
import { AlertService } from 'ngx-alerts';
import { Address } from 'src/app/model/address';
import { MyUser } from 'src/app/model/my-user';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-admin-registration-dialog',
  templateUrl: './admin-registration-dialog.component.html',
  styleUrls: ['./admin-registration-dialog.component.css']
})
export class AdminRegistrationDialogComponent implements OnInit {

  address: Address = new Address(0,"","","",0,0, 0)
  user: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  passwordCheck: String = "";

  constructor(private authService: AuthService, private alertService: AlertService) {
  }

  ngOnInit(): void {  // tu pozivam metode iz servisa
    this.user.authority = "ROLE_ADMINISTRATOR";
    this.user.password = "admin";
  }

  registerUser() {
      this.authService.register(this.user).subscribe(   // subscribe - da bismo dobili odgovor beka
        (isCreated: Boolean) => {
          this.alertService.success('User created');
        },
        (error) => {
          this.alertService.danger('Email nije validan');
        },
      )
  }

}
