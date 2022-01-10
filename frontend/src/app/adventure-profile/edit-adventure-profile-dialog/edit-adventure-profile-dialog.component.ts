import { Component, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'ngx-alerts';
import { AdditionalService } from 'src/app/model/additional-service';
import { FishingAdventure } from 'src/app/model/fishing-adventure';
import { AdditionalServicesService } from 'src/app/service/additional-services.service';
import { AdventureProfileService } from 'src/app/service/adventure-profile.service';

@Component({
  selector: 'app-edit-adventure-profile-dialog',
  templateUrl: './edit-adventure-profile-dialog.component.html',
  styleUrls: ['./edit-adventure-profile-dialog.component.css']
})
export class EditAdventureProfileDialogComponent implements OnInit {

  adventure!: FishingAdventure;
  additionalServices!: AdditionalService[];
  additionalService: AdditionalService = new AdditionalService(0,"",0,false);

  constructor(public dialogRef: MatDialogRef<EditAdventureProfileDialogComponent>,private _adventureService: AdventureProfileService, private _alertService: AlertService,
          private _additionalServicesService: AdditionalServicesService) { }

  ngOnInit() {
  }

  addService(){
    if(this.additionalService.name != '' && this.additionalService.price != 0){
      this.additionalService.adventureId = this.adventure.id;
      this.additionalService.checked = false;

      this._additionalServicesService.save(this.additionalService).subscribe(   // subscribe - da bismo dobili odgovor beka
        (additionalService: AdditionalService) => {
          this.reloadServices();
          this.additionalService.name = '';
          this.additionalService.price = 0;
        },
        (error) => {
          this._alertService.danger('Doslo je do greske');
        },
      )
    }
  }

  checkboxChanged($event: MatCheckboxChange) {
    if (this.adventure.isCancellationFree == true)
    {
      this.adventure.cancellationFee = 0
    }
  }

  deleteAdditionalService(id: number) {
    this._additionalServicesService.delete(id).subscribe(   // OBAVEZNO SE MORA SUBSCRIBE-OVATI !!!
      (boolean:boolean) =>{
        this.reloadServices()
      }
    )
  }

  reloadServices(){
    this._additionalServicesService.getAllByFishingAdventureId(this.adventure.id).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
      }
    )
  }

  editProfile() {
    this._adventureService.edit(this.adventure).subscribe(   // subscribe - da bismo dobili odgovor beka
      (adventure: FishingAdventure) => {
        this.dialogRef.close();
      },
      (error) => {
        // console.log(error)
        this._alertService.danger('Doslo je do greske');
      },
    )

  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
