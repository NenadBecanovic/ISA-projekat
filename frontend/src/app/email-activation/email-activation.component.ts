import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../service/auth.service";
import { AlertService } from 'ngx-alerts';


@Component({
  selector: 'app-email-activation',
  templateUrl: './email-activation.component.html',
  styleUrls: ['./email-activation.component.css']
})
export class EmailActivationComponent implements OnInit {
  emailConfirmed: Boolean = false;
  urlParams: any ={}


  constructor(private route: ActivatedRoute, private authService: AuthService, private alertService: AlertService) { }



  ngOnInit(): void {
    this.urlParams.token = this.route.snapshot.queryParamMap.get('token');
    this.urlParams.userId = this.route.snapshot.queryParamMap.get('userId');
    

    this.confirmEmail();
  }

  private confirmEmail() {

    this.authService.confirmEmail(this.urlParams).subscribe(
      ()=>{
        this.emailConfirmed = true;
        console.log("uslo")
        this.alertService.success('Email Confirmed');

      },
      (error) => {
        this.emailConfirmed = false;
        this.alertService.danger("Unable to confirm")

      }
    )
  }
}
