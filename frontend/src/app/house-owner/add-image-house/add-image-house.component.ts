import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {ImageService} from "../../service/image.service";

@Component({
  selector: 'app-add-image-house',
  templateUrl: './add-image-house.component.html',
  styleUrls: ['./add-image-house.component.css']
})
export class AddImageHouseComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AddImageHouseComponent>, private _imageService: ImageService) { }

  uploadedImage!: string | ArrayBuffer;
  id!: number;

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  imageAdded(e: any){
    const file = e.target.files[0];
    this.createBase64Image(file);
  }

  createBase64Image(file: File){
    const reader= new FileReader();
    reader.onload = (e) =>{
      if (!e.target?.result) return;
      let img = e.target.result;
      // console.log(img);
      this.uploadedImage = img;
    }
    reader.readAsDataURL(file);
  }

  ok(){
    this._imageService.uploadHouseImage(this.uploadedImage, this.id).subscribe(
      (res) => {
        this.dialogRef.close();
      },
      (err) => {

      })
  }
}
