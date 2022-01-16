import { Component, OnInit } from '@angular/core';
import {Address} from "../../../model/address";
import {NavigationEquipment} from "../../../model/navigation-equipment";
import {AdditionalService} from "../../../model/additional-service";
import {Boat} from "../../../model/boat";
import {BoatReservation} from "../../../model/boat-reservation";
import {BoatReservationSlide} from "../../../model/boat-reservation-slide";
import {Image} from "../../../model/image";
import {BoatService} from "../../../service/boat.service";
import {AdditionalServicesService} from "../../../service/additional-services.service";
import {ImageService} from "../../../service/image.service";
import {BoatReservationService} from "../../../service/boat-reservation.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MyUser} from "../../../model/my-user";
import {Subscription} from "../../../model/subscription";
import {MyUserService} from "../../../service/my-user.service";
import {AuthentificationService} from "../../../auth/authentification/authentification.service";
import {CreateReservationBoatComponent} from "../../dialog/create-reservation-boat/create-reservation-boat.component";
import {MatDialog} from "@angular/material/dialog";
import {ActionDTO} from "../../../model/action-dto";
import {ClientReservationService} from "../../../service/client-reservation-service";
import {AlertService} from "ngx-alerts";
import {HouseReservationSlide} from "../../../model/house-reservation-slide";

@Component({
  selector: 'app-client-boat',
  templateUrl: './client-boat.component.html',
  styleUrls: ['./client-boat.component.css']
})
export class ClientBoatComponent implements OnInit {

  lat = 0;
  lng = 0;
  address: Address = new Address(0,"Luka 11","Novi Sad","Srbija",0,0,21000)
  user: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  currentUser: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  subscription: Subscription = new Subscription(0,this.user,this.user)
  navigationEquipment: NavigationEquipment = new NavigationEquipment(0,true, true, true, true);
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  boat: Boat = new Boat(0, '', '', '', 0, 0, '', 0, 0, 0, 0, false, 0, '', this.address, this.navigationEquipment, this.additionalServices, 0, 0);
  courses: BoatReservation[] = new Array<BoatReservation>();
  courses_slides: BoatReservationSlide[] = new Array<BoatReservationSlide>();
  isSlideLoaded: boolean = false;
  images: Image[] = new Array<Image>();
  isLoaded: boolean = false;
  freeCancelation: boolean = false;
  id: number = 0;
  isSubscribed: Boolean = false;
  action: ActionDTO = new ActionDTO();


  constructor(private _boatService: BoatService, private _additionalServices: AdditionalServicesService, private _imageService: ImageService,
              private _boatReservationService: BoatReservationService, private _router: Router, private _route: ActivatedRoute,
              private _myUserService: MyUserService, private _authentification: AuthentificationService, public dialog: MatDialog,
              private _clientResrvationService: ClientReservationService, private _alertService: AlertService) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.id =  +this._route.snapshot.paramMap.get('id');
    this.loadData(this.id);
  }

  loadData(id: number) { // ucitavanje iz baze
    this._boatService.getBoatById(id).subscribe(
      (boat: Boat) => {
        this.boat = boat
        this.address = this.boat.address;
        this.lat = this.address.latitude;
        this.lng = this.address.longitude;
        this.freeCancelation = this.boat.cancalletionFree;

        this._additionalServices.getAllByBoatId(this.boat.id).subscribe(
          (additionalServices: AdditionalService[]) => {
            this.additionalServices = additionalServices
          }
        )

        this._imageService.getAllByBoatId(this.boat.id).subscribe(
          (images: Image[]) => {
            this.images = images
            this.isLoaded = true;
          }
        )


        this._boatReservationService.getAllActionsByBoatId(this.boat.id).subscribe(
          (courses_slides: BoatReservationSlide[]) => {
            this.courses_slides = courses_slides

            this.getSavingsForAction(this.courses_slides)
            this.isSlideLoaded = true
          }
        )

        this._myUserService.findUserByBoatId(this.boat.id).subscribe(
          (myUser: MyUser) => {
            this.user = myUser
            this._authentification.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
              (user: MyUser) => {
                this.currentUser = user;
                this._myUserService.checkIfUserIsSubscribes(this.currentUser.id, this.user.id).subscribe(
                  (isSubscribed: Boolean) => {
                    this.isSubscribed = isSubscribed

                  }
                )
              },
              (error) => {
              },
            )
          }
        )

      }
    )
  }

  subscribe() {
    console.log('uslo')
    if(confirm("Da li sigurne zelite da se pretplatite")) {

      this.subscription.owner = this.user;
      this.subscription.subscribedUser = this.currentUser
      this._myUserService.saveSubscription(this.subscription).subscribe(
        (sub: Subscription) => {
          this.subscription = sub;
          this.loadData(this.id)
        }
      )
    }
  }

  reservate() {
    const dialogRef = this.dialog.open(CreateReservationBoatComponent,{
      width: '600px',
      data: {
        boatId: this.id,
      },
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }


  getSavingsForAction(courses: BoatReservationSlide[] ){
    for(let h of courses){
      for(let h1 of h.boatReservationDTOList){
        var duration = Number.parseInt(((Number.parseInt(h1.endDate) -  Number.parseInt(h1.startDate))/1000/60/60/24).toFixed(0))
        var actionPrice = h1.price * duration
        var housePrice = this.boat.pricePerDay * duration
        h1.savings = housePrice - actionPrice
      }
    }
  }

  setActionGuest(course: BoatReservation) {
    this.action.entityId = course.id
    this.action.userId = this.currentUser.id

    if (confirm("Da li ste sigurni da zelite da rezervisete akciju. Usteda je " + course.savings + " dinara" )) {
      this._clientResrvationService.editBoatAction(this.action).subscribe((bool: boolean)=>{
        if(bool){
          this._alertService.success("Rezervacija je uspjesna, pogledajte mejl");

        }else{
          this._alertService.warning("Rezervacija je vec zauzeta");
        }
      }, (error => {
        this._alertService.danger("Doslo je do greske pokusajte opet");
      }))
    }
  }
}
