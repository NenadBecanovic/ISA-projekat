import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdventureProfileService } from 'src/app/service/adventure-profile.service';

@Component({
  selector: 'app-add-image-dialog',
  templateUrl: './add-image-dialog.component.html',
  styleUrls: ['./add-image-dialog.component.css']
})
export class AddImageDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AddImageDialogComponent>, private _adventureService: AdventureProfileService) { }

  uploadedImage!: File;
  
  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  public onImageUpload(event: any) {
    this.uploadedImage = event.target.files[0];
  }


  imageUploadAction() {
    /*
    this._adventureService.uploadImage(imageFormData).subscribe(
      (res) => {
        alert("OK");
      },
      (err) => {

      })

    this.httpClient.post('http://localhost:8080/upload/image/', imageFormData, { observe: 'response' })
      .subscribe((response: { status: number; }) => {
        if (response.status === 200) {
        } else {

        }
      }
      );*/
    }
  
    ok(){
      alert("OK")
    }
}
