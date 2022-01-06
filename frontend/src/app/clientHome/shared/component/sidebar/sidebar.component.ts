import {Component, Input, OnInit} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {MyUser} from "../../../../model/my-user";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  // @ts-ignore
  @Input() currentUser:MyUser = new MyUser();

  constructor(private sanitizer: DomSanitizer) {

  }

  ngOnInit(): void {
    console.log(this.currentUser)
  }

}
