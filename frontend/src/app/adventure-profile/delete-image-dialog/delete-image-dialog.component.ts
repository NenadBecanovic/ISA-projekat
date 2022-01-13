import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { ImageService } from 'src/app/service/image.service';

@Component({
  selector: 'app-delete-image-dialog',
  templateUrl: './delete-image-dialog.component.html',
  styleUrls: ['./delete-image-dialog.component.css']
})
export class DeleteImageDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DeleteImageDialogComponent>, private _imageService: ImageService) { }

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
