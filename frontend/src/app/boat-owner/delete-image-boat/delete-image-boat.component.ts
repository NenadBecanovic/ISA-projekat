import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {ImageService} from "../../service/image.service";

@Component({
  selector: 'app-delete-image-boat',
  templateUrl: './delete-image-boat.component.html',
  styleUrls: ['./delete-image-boat.component.css']
})
export class DeleteImageBoatComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DeleteImageBoatComponent>, private _imageService: ImageService) { }

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
