import { Component, OnInit } from '@angular/core';
import {Chart} from "angular-highcharts";
import {Address} from "../../model/address";
import {Room} from "../../model/room";
import {AdditionalService} from "../../model/additional-service";
import {House} from "../../model/house";
import {HouseReservation} from "../../model/house-reservation";
import {FishingAdventure} from "../../model/fishing-adventure";
import {HouseReservationService} from "../../service/house-reservation.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HouseService} from "../../service/house.service";
import {AdventureReservationService} from "../../service/adventure-reservation.service";
import {AdventureService} from "../../service/adventure.service";

@Component({
  selector: 'app-adventure-chart',
  templateUrl: './adventure-chart.component.html',
  styleUrls: ['./adventure-chart.component.css']
})
export class AdventureChartComponent implements OnInit {

  chartMonthly: Chart = new Chart();
  chartYearly: Chart = new Chart();
  chartWeekly: Chart = new Chart();
  chartWeeklyIncome: Chart = new Chart();
  address: Address = new Address(0,"","","",0,0,0)
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  fishingAdventure: FishingAdventure= new FishingAdventure(0,"", this.address, "", 0, "", "", 0,true, 0);
  courses: FishingAdventure[] = new Array<FishingAdventure>();
  year2021: boolean = false;
  year2022: boolean = true;
  jan: FishingAdventure[] = new Array();
  feb: FishingAdventure[] = new Array();
  mar: FishingAdventure[] = new Array();
  apr: FishingAdventure[] = new Array();
  may: FishingAdventure[] = new Array();
  jun: FishingAdventure[] = new Array();
  jul: FishingAdventure[] = new Array();
  aug: FishingAdventure[] = new Array();
  sep: FishingAdventure[] = new Array();
  oct: FishingAdventure[] = new Array();
  nov: FishingAdventure[] = new Array();
  dec: FishingAdventure[] = new Array();
  year2021Array: FishingAdventure[] = new Array();
  year2022Array: FishingAdventure[] = new Array();
  date: Date = new Date();
  add: AdditionalService[] = new Array();
  firstBoat: FishingAdventure[] = new Array();
  secondBoat: FishingAdventure[] = new Array();
  thirdBoat: FishingAdventure[] = new Array();
  forthBoat: FishingAdventure[] = new Array();
  fifthBoat: FishingAdventure[] = new Array();
  sixtBoat: FishingAdventure[] = new Array();
  seventhBoat: FishingAdventure[] = new Array();
  firstDayIncome: number = 0;
  secondDayIncome: number = 0;
  thirdDayIncome: number = 0;
  forthDayIncome: number = 0;
  fifthDayIncome: number = 0;
  sixthDayIncome: number = 0;
  seventhDayIncome: number = 0;

  constructor(private _adventureReservationService: AdventureReservationService, private _router: Router, private _route: ActivatedRoute,
              private _adventureService: AdventureService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.fishingAdventure.id = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  initializeArrays(){
    this.jan = new Array();
    this.feb = new Array();
    this.mar = new Array();
    this.apr = new Array();
    this.may = new Array();
    this.jun = new Array();
    this.jul = new Array();
    this.aug = new Array();
    this.sep = new Array();
    this.oct = new Array();
    this.nov = new Array();
    this.dec = new Array();
    this.year2021Array = new Array();
    this.year2022Array = new Array();
  }

  // determineMonth(b: FishingAdventure){
  //   var start =  new Date(Number(b.))
  //
  //   if(start.getMonth() == 0) {
  //     this.jan.push(b)
  //   }else if(start.getMonth() == 1){
  //     this.feb.push(b)
  //   }else if(start.getMonth() == 2){
  //     this.mar.push(b)
  //   }else if(start.getMonth() == 3){
  //     this.apr.push(b)
  //   }else if(start.getMonth() == 4){
  //     this.may.push(b)
  //   }else if(start.getMonth() == 5){
  //     this.jun.push(b)
  //   }else if(start.getMonth() == 6){
  //     this.jul.push(b)
  //   }else if(start.getMonth() == 7){
  //     this.aug.push(b)
  //   }else if(start.getMonth() == 8){
  //     this.sep.push(b)
  //   }else if(start.getMonth() == 9){
  //     this.oct.push(b)
  //   }else if(start.getMonth() == 10){
  //     this.nov.push(b)
  //   }else if(start.getMonth() == 11){
  //     this.dec.push(b)
  //   }
  // }
  //
  // year2021func(){
  //   this.year2022 = false
  //   this.year2021 = true
  //   this.loadData()
  // }
  //
  // year2022func(){
  //   this.year2022 = true
  //   this.year2021 = false
  //   this.loadData()
  // }
  //
  // loadData() {
  //   this._houseService.getHouseById(this.house.id).subscribe(
  //     (house: House) =>{
  //       this.house = house
  //     }
  //   )
  //
  //   this._houseReservationService.getAllByHouseIdPlane(this.house.id).subscribe(
  //     (houseReservations: HouseReservation[]) =>{
  //       // this.courses = boatReservations
  //       this.initializeArrays()
  //
  //       for(let b of houseReservations) {
  //         if (!b.availabilityPeriod && !b.available && Number(b.startDate) >= 1640991600000) {
  //           this.year2022Array.push(b)
  //         }
  //       }
  //       for(let b of houseReservations) {
  //         if (!b.availabilityPeriod && !b.available && Number(b.startDate) >= 1609455600000 && Number(b.startDate) < 1640991600000) {
  //           this.year2021Array.push(b)
  //         }
  //       }
  //
  //       if (this.year2022) {
  //         for(let b of houseReservations) {
  //           if (!b.availabilityPeriod && !b.available && Number(b.startDate) >= 1640991600000) {
  //             this.determineMonth(b);
  //             //this.year2022Array.push(b)
  //           }
  //         }
  //       } else if (this.year2021) {
  //         for(let b of houseReservations) {
  //           if (!b.availabilityPeriod && !b.available && Number(b.startDate) >= 1609455600000 && Number(b.startDate) < 1640991600000) {
  //             this.determineMonth(b);
  //             this.year2021Array.push(b)
  //           }
  //         }
  //       }
  //
  //       this.chartMonthly = new Chart({
  //           chart: {
  //             type: 'bar',
  //           },
  //           credits: {
  //             enabled: false,
  //           },
  //           title: {
  //             text: 'Izvestaj o rezervacijama vikendice na mesecnom nivou',
  //           },
  //           yAxis: {
  //             visible: true,
  //             gridLineColor: '#fff',
  //           },
  //           legend: {
  //             enabled: false,
  //           },
  //           xAxis: {
  //             lineColor: '#fff',
  //             categories: [
  //               'Jan',
  //               'Feb',
  //               'Mar',
  //               'Apr',
  //               'Maj',
  //               'Jun',
  //               'Jul',
  //               'Aug',
  //               'Sep',
  //               'Okt',
  //               'Nov',
  //               'Dec',
  //             ],
  //           },
  //           plotOptions: {
  //             series: {
  //               borderRadius: 5,
  //             } as any,
  //           },
  //           series: [
  //             {
  //               type: 'bar',
  //               color: '#fc5185',
  //               //#506ef9
  //               data: [
  //                 {y: this.jan.length},
  //                 {y: this.feb.length},
  //                 {y: this.mar.length},
  //                 {y: this.apr.length},
  //                 {y: this.may.length},
  //                 {y: this.jun.length},
  //                 {y: this.jul.length},
  //                 {y: this.aug.length},
  //                 {y: this.sep.length},
  //                 {y: this.oct.length},
  //                 {y: this.nov.length},
  //                 {y: this.dec.length},
  //               ],
  //             },
  //           ],
  //         }
  //       );
  //
  //       this.chartYearly = new Chart({
  //           chart: {
  //             type: 'bar',
  //           },
  //           credits: {
  //             enabled: false,
  //           },
  //           title: {
  //             text: 'Izvestaj o rezervacijama vikendice na godisnjem nivou',
  //           },
  //           yAxis: {
  //             visible: true,
  //             gridLineColor: '#fff',
  //           },
  //           legend: {
  //             enabled: false,
  //           },
  //           xAxis: {
  //             lineColor: '#fff',
  //             categories: [
  //               '2021',
  //               '2022',
  //               //'2023',
  //             ],
  //           },
  //           plotOptions: {
  //             series: {
  //               borderRadius: 5,
  //             } as any,
  //           },
  //           series: [
  //             {
  //               type: 'bar',
  //               color: '#8DC3A7',
  //               //#506ef9
  //               data: [
  //                 {y:  this.year2021Array.length},
  //                 {y:  this.year2022Array.length},
  //               ],
  //             },
  //           ],
  //         }
  //       );
  //
  //       var danas = new Date().getMilliseconds()
  //       console.log(danas)
  //
  //       var weekAgo = Number(this.date) - (86400000 * 6)
  //       var zero = new Date(weekAgo - 86400000)
  //
  //       console.log(zero)
  //
  //       var firstDay = new Date(weekAgo)
  //       var secondDay = new Date(weekAgo + 86400000)
  //       var thirdDay = new Date(weekAgo + 2*86400000)
  //       var forthdDay = new Date(weekAgo + 3*86400000)
  //       var fifthdDay = new Date(weekAgo + 4*86400000)
  //       var sixtDay = new Date(weekAgo + 5*86400000)
  //
  //
  //       for(let r of houseReservations) {
  //         if (Number(r.startDate) >= Number(zero) && Number(r.startDate) < Number(firstDay)){
  //           this.firstBoat.push(r)
  //           this.firstDayIncome = r.price;
  //         }else if (Number(r.startDate) >= Number(firstDay) && Number(r.startDate) < Number(secondDay)) {
  //           this.secondBoat.push(r);
  //           this.secondDayIncome = r.price;
  //         } else if (Number(r.startDate) >= Number(secondDay) && Number(r.startDate) < Number(thirdDay)) {
  //           this.thirdBoat.push(r);
  //           this.thirdDayIncome = r.price;
  //         } else if (Number(r.startDate) >= Number(thirdDay) && Number(r.startDate) < Number(forthdDay)) {
  //           this.forthBoat.push(r);
  //           this.forthDayIncome = r.price;
  //         } else if (Number(r.startDate) >= Number(forthdDay) && Number(r.startDate) < Number(fifthdDay)) {
  //           this.fifthBoat.push(r);
  //           this.fifthDayIncome = r.price;
  //         } else if (Number(r.startDate) >= Number(fifthdDay) && Number(r.startDate) < Number(sixtDay)) {
  //           this.sixtBoat.push(r);
  //           this.sixthDayIncome = r.price;
  //         } else if (Number(r.startDate) >= Number(sixtDay) && Number(r.startDate) < Number(weekAgo)) {
  //           this.seventhBoat.push(r);
  //           this.seventhDayIncome = r.price;
  //         }
  //       }
  //
  //       this.chartWeekly = new Chart({
  //           chart: {
  //             type: 'bar',
  //           },
  //           credits: {
  //             enabled: false,
  //           },
  //           title: {
  //             text: 'Izvestaj o rezervacijama vikendice na nedeljnom nivou',
  //           },
  //           yAxis: {
  //             visible: true,
  //             gridLineColor: '#fff',
  //           },
  //           legend: {
  //             enabled: false,
  //           },
  //           xAxis: {
  //             lineColor: '#fff',
  //             categories: [
  //               this.date.toString(),
  //               sixtDay.toString(),
  //               fifthdDay.toString(),
  //               forthdDay.toString(),
  //               thirdDay.toString(),
  //               secondDay.toString(),
  //               firstDay.toString(),
  //             ],
  //           },
  //           plotOptions: {
  //             series: {
  //               borderRadius: 5,
  //             } as any,
  //           },
  //           series: [
  //             {
  //               type: 'bar',
  //               color: '#E090DF',
  //               //#506ef9
  //               data: [
  //                 {y: this.seventhBoat.length},
  //                 {y: this.sixtBoat.length},
  //                 {y: this.fifthBoat.length},
  //                 {y: this.forthBoat.length},
  //                 {y: this.thirdBoat.length},
  //                 {y: this.secondBoat.length},
  //                 {y: this.firstBoat.length},
  //               ],
  //             },
  //           ],
  //         }
  //       );
  //
  //
  //       this.chartWeeklyIncome = new Chart({
  //           chart: {
  //             type: 'bar',
  //           },
  //           credits: {
  //             enabled: false,
  //           },
  //           title: {
  //             text: 'Izvestaj o prihodima vikendice na nedeljnom nivou',
  //           },
  //           yAxis: {
  //             visible: true,
  //             gridLineColor: '#fff',
  //           },
  //           legend: {
  //             enabled: false,
  //           },
  //           xAxis: {
  //             lineColor: '#fff',
  //             categories: [
  //               this.date.toString(),
  //               sixtDay.toString(),
  //               fifthdDay.toString(),
  //               forthdDay.toString(),
  //               thirdDay.toString(),
  //               secondDay.toString(),
  //               firstDay.toString(),
  //             ],
  //           },
  //           plotOptions: {
  //             series: {
  //               borderRadius: 5,
  //             } as any,
  //           },
  //           series: [
  //             {
  //               type: 'bar',
  //               color: '#FFBF00',
  //               //#506ef9
  //               data: [
  //                 {y: this.seventhDayIncome},
  //                 {y: this.sixthDayIncome},
  //                 {y: this.fifthDayIncome},
  //                 {y: this.forthDayIncome},
  //                 {y: this.thirdDayIncome},
  //                 {y: this.secondDayIncome},
  //                 {y: this.firstDayIncome},
  //               ],
  //             },
  //           ],
  //         }
  //       );
  //     }
  //   )
  // }

  private loadData() {

  }

  year2021func() {

  }

  year2022func() {

  }
}
