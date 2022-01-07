import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdventureProfileService } from 'src/app/service/adventure-profile.service';
import { ImageService } from 'src/app/service/image.service';

@Component({
  selector: 'app-add-image-dialog',
  templateUrl: './add-image-dialog.component.html',
  styleUrls: ['./add-image-dialog.component.css']
})
export class AddImageDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AddImageDialogComponent>, private _imageService: ImageService) { }

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
    //  this.form.Image=URL.createObjectURL(file);
    }
    
    createBase64Image(file: File){
        const reader= new FileReader();
        reader.onload = (e) =>{
          if (!e.target?.result) return;
          let img = e.target.result;
          //img.replace("data:image\/(png|jpg|jpeg);base64", "");
          console.log(img);
          this.uploadedImage = img;
        }
        reader.readAsDataURL(file);
    }

    ok(){
      //alert(this.uploadedImage);
      this._imageService.uploadImage(this.uploadedImage,this.id).subscribe(
        (res) => {
          //alert("OK");
          this.dialogRef.close();
        },
        (err) => {
  
        })
    }
}
