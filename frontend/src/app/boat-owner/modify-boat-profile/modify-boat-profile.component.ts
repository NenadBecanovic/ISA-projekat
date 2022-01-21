import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService} from "ngx-alerts";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {BoatService} from "../../service/boat.service";
import {Address} from "../../model/address";
import {AdditionalService} from "../../model/additional-service";
import {Boat} from "../../model/boat";
import {NavigationEquipment} from "../../model/navigation-equipment";
import {MatCheckboxChange} from "@angular/material/checkbox";

@Component({
  selector: 'app-modify-boat-profile',
  templateUrl: './modify-boat-profile.component.html',
  styleUrls: ['./modify-boat-profile.component.css']
})
export class ModifyBoatProfileComponent implements OnInit {

  id: number = 0;
  address: Address = new Address(0, '', '','', 0, 0, 0);
  additionalServices: AdditionalService[] = new Array();
  navigationEquipment: NavigationEquipment = new NavigationEquipment(0, false, false, false, false);
  boat: Boat = new Boat(0, '', '', '', 0, 0,'', 0,0,0,0,false,0, '', this.address, this.navigationEquipment, this.additionalServices, 0, 0);
  newAdditionalService: AdditionalService = new AdditionalService(0, '', 0, false);
  showNewService: boolean = false;
  canNotBeEdited: boolean = false;
  min: number = 0;

  constructor(private _route: ActivatedRoute, private _router: Router, private _boatService: BoatService, private _alertService: AlertService,
              private _additionalServices: AdditionalServicesService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.id = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  checkEdit(){
    this._boatService.edit(this.boat).subscribe(
      (boat: Boat) => {
        this.canNotBeEdited = false
        console.log(this.canNotBeEdited)
      },
      (error) => {
        this.canNotBeEdited = true
        console.log(this.canNotBeEdited)
      }
    )
  }

  private loadData() {
    this._boatService.getBoatById(this.id).subscribe(
      (boat: Boat) => {
        this.boat = boat

        this._additionalServices.getAllByBoatId(this.id).subscribe(
          (additionalServices: AdditionalService[]) => {
            this.boat.services = additionalServices
            this.additionalServices = additionalServices
            this.checkEdit()
          }
        )
      }
    )
  }

  deleteAdditionalService(id: number) {
    this._additionalServices.delete(id).subscribe(
      (boolean:boolean) =>{
        this.loadData()
      }
    )
  }

  showAddingNewService() {
    if (this.showNewService == true)
    {
      this.showNewService = false;
    }
    else
    {
      this.showNewService = true;
    }
  }

  addAdditionalService() {
    if (this.newAdditionalService.price > 0 && this.newAdditionalService.name != '') {
      this.newAdditionalService.boatId = this.id;
      this.newAdditionalService.checked = false;

      this._additionalServices.save(this.newAdditionalService).subscribe(
        (additionalService: AdditionalService) => {
          this.loadData();
        },
        (error) => {
          this._alertService.danger('Rezervisana vikendica se ne može izmeniti');
        },
      )
    } else {
      this._alertService.warning('Neispravno uneti podaci za novu dodatnu uslugu');
    }
  }

  checkboxChanged($event: MatCheckboxChange) {
    if (this.boat.cancalletionFree == true)
    {
      this.boat.cancalletionFee = 0
    }
  }

  editProfile() {
    if (this.boat.name != '' && this.boat.promoDescription != '' && this.boat.address.street != '' && this.boat.address.city != '' &&
      this.boat.address.state != '' && this.boat.address.postalCode >= 0 && this.boat.pricePerDay > 0 && this.boat.behaviourRules != '' &&
      this.boat.capacity > 0 && this.boat.type != '' && this.boat.length > 0 && this.boat.enginePower > 0 && this.boat.enginePower > 0 &&
      this.boat.maxSpeed > 0 && this.boat.fishingEquipment != '' && this.boat.address.longitude >=0 && this.boat.address.latitude >= 0) {

      if (!this.boat.cancalletionFree && this.boat.cancalletionFee == 0) {
        this._alertService.warning('Unesite % nadoknade u slucaju otkazivanja');
      }
      else
      {
        if(this.boat.cancalletionFee > 100)
        {
          this._alertService.warning('Uslovi otkazivanja su u vrednostima 0-100');
        }
        else
        {
          this._boatService.edit(this.boat).subscribe(
            (boat: Boat) => {
              this._router.navigate(['boat-profile-for-boat-owner', boat.id])
            },
            (error) => {
              this._alertService.danger('Rezervisani brod se ne može izmeniti');
            }
          )
        }
      }
    }
    else {
      this._alertService.warning('Niste popunili sva polja!');
    }
  }
}
