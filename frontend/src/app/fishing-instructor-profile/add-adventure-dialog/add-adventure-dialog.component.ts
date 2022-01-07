import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Address } from 'src/app/model/address';
import { NewFishingAdventure } from 'src/app/model/new-fishing-adventure';
import { AdventureProfileService } from 'src/app/service/adventure-profile.service';

class ImageSnippet {
  constructor(public src: string, public file: File) {}
}
@Component({
  selector: 'app-add-adventure-dialog',
  templateUrl: './add-adventure-dialog.component.html',
  styleUrls: ['./add-adventure-dialog.component.css']
})
export class AddAdventureDialogComponent implements OnInit {

  selectedFile!: ImageSnippet;
  address: Address = new Address(0,"","","",0,0,0);
  fishingAdventure: NewFishingAdventure = new NewFishingAdventure("",this.address,"",0,"","",0,true,0);

  constructor(
    public dialogRef: MatDialogRef<AddAdventureDialogComponent>,private adventureService: AdventureProfileService
  ) { }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ok(){
    
  }

  imageAdded(e: any){
    /* const file = e.target.files[0];
     this.createBase64Image(file);
     this.newImage=URL.createObjectURL(file);
     const file: File = e.files[0];
   const reader = new FileReader();

   reader.addEventListener('load', (event: any) => {

     this.selectedFile = new ImageSnippet(event.target.result, file);

     this.adventureService.uploadImage(this.selectedFile.file).subscribe(
       (res) => {
         alert("OK");
       },
       (err) => {
       
       })
   });

   reader.readAsDataURL(file);*/
 }
 
 createBase64Image(file: Blob){
   /*  const reader= new FileReader();
     reader.onload = (e) =>{
       let img = e.target.result;
       img.replace("data:image\/(png|jpg|jpeg);base64", "");
     //  console.log(img);
       this.form.backendImage = img;
     }
     reader.readAsDataURL(file);*/
 }
}
