import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {ImageService} from "../../service/image.service";

@Component({
  selector: 'app-delete-image-house',
  templateUrl: './delete-image-house.component.html',
  styleUrls: ['./delete-image-house.component.css']
})
export class DeleteImageHouseComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DeleteImageHouseComponent>, private _imageService: ImageService) { }

  id: number = 0;

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  deleteImage(){
    this._imageService.deleteImage(this.id).subscribe(
      (res) => {
        //alert("OK");
        this.dialogRef.close();
      },
      (err) => {

      })
  }

}
