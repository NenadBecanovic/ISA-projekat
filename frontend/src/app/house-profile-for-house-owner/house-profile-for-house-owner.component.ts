import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-house-profile-for-house-owner',
  templateUrl: './house-profile-for-house-owner.component.html',
  styleUrls: ['./house-profile-for-house-owner.component.css']
})
export class HouseProfileForHouseOwnerComponent implements OnInit {
  slides = [{'image': 'assets/cottage.jpg'}, {'image': 'assets/cottage.jpg'}]
  constructor() { }

  ngOnInit(): void {
    // this.slides.append("assets/cottage.jpg")
    // this.slides.append("assets/cottage.jpg")
    // this.slides.append("assets/cottage.jpg")
    // this.slides.append("assets/cottage.jpg")
    // this.slides.append("assets/cottage.jpg")
  }

}
