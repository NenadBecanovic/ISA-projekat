import { Component, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'ngx-alerts';
import { AdditionalService } from 'src/app/model/additional-service';
import { Address } from 'src/app/model/address';
import { FishingAdventure } from 'src/app/model/fishing-adventure';
import { Image } from 'src/app/model/image';
import { NewFishingAdventure } from 'src/app/model/new-fishing-adventure';
import { AdventureProfileService } from 'src/app/service/adventure-profile.service';
import { ImageService } from 'src/app/service/image.service';

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
  newFishingAdventure: NewFishingAdventure = new NewFishingAdventure("",this.address,"",0,"","",0,true,0,"");
  additionalService: AdditionalService = new AdditionalService(0,'',0,false);

  constructor(public dialogRef: MatDialogRef<AddAdventureDialogComponent>,private _adventureService: AdventureProfileService, private _alertService: AlertService,
    private _imageService: ImageService) { }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  addService(){
    if(this.additionalService.name != '' && this.additionalService.price != 0){
      this.newFishingAdventure.additionalServices.push(new AdditionalService(0,this.additionalService.name,this.additionalService.price,false));
      this.additionalService.name = '';
      this.additionalService.price = 0;
    }
  }

  ok(){}
  imageAdded(e: any){
    const file = e.target.files[0];
    this.createBase64Image(file);
  }
  
  createBase64Image(file: File){
      const reader= new FileReader();
      reader.onload = (e) =>{
        if (!e.target?.result) return;
        let img = e.target.result;
        //img.replace("data:image\/(png|jpg|jpeg);base64", "");
        console.log(img);
        this.newFishingAdventure.image = img;
      }
      reader.readAsDataURL(file);
  }

  checkboxChanged($event: MatCheckboxChange) {
    if (this.newFishingAdventure.isCancellationFree == true)
    {
      this.newFishingAdventure.cancellationFee = 0
    }
  }

  save(){
    this._adventureService.save(this.newFishingAdventure).subscribe(   // subscribe - da bismo dobili odgovor beka
      (id: number) => {
        this._imageService.uploadImage(this.newFishingAdventure.image, id).subscribe(   // subscribe - da bismo dobili odgovor beka
          () => {
            this.dialogRef.close();
          },
          (error) => {
            this._alertService.danger('Doslo je do greske');
          },
        )
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      },
    )

    
  }
}
