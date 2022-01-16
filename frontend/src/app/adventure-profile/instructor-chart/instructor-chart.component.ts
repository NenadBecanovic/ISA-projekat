import { Component, OnInit } from '@angular/core';
import {Chart} from "angular-highcharts";
import {Address} from "../../model/address";
import {NavigationEquipment} from "../../model/navigation-equipment";
import {AdditionalService} from "../../model/additional-service";
import {Boat} from "../../model/boat";
import {BoatReservation} from "../../model/boat-reservation";
import {BoatReservationService} from "../../service/boat-reservation.service";
import {ActivatedRoute, Router} from "@angular/router";
import {BoatService} from "../../service/boat.service";
import {FishingAdventureInstructorDTO} from "../../model/fishing-adventure-instructorDTO";
import {FishingAdventure} from "../../model/fishing-adventure";
import {AdventureReservationService} from "../../service/adventure-reservation.service";
import {AdventureReservation} from "../../model/adventure-reservation";
import {AdventureService} from "../../service/adventure.service";
import {Adventure} from "../../model/adventure";
import {FishingAdventureService} from "../../service/fishing-adventure.service";

@Component({
  selector: 'app-instructor-chart',
  templateUrl: './instructor-chart.component.html',
  styleUrls: ['./instructor-chart.component.css']
})
export class InstructorChartComponent implements OnInit {

  chartMonthly: Chart = new Chart();
  chartYearly: Chart = new Chart();
  chartWeekly: Chart = new Chart();
  chartWeeklyIncome: Chart = new Chart();
  address: Address = new Address(0,"","","",0,0,0)
  instructor: FishingAdventureInstructorDTO = new FishingAdventureInstructorDTO();
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  adventure: FishingAdventure= new FishingAdventure(0,"", this.address, "", 0, "", "", 0,false, 0);
  year2021: boolean = false;
  year2022: boolean = true;
  jan: AdventureReservation[] = new Array();
  feb: AdventureReservation[] = new Array();
  mar: AdventureReservation[] = new Array();
  apr: AdventureReservation[] = new Array();
  may: AdventureReservation[] = new Array();
  jun: AdventureReservation[] = new Array();
  jul: AdventureReservation[] = new Array();
  aug: AdventureReservation[] = new Array();
  sep: AdventureReservation[] = new Array();
  oct: AdventureReservation[] = new Array();
  nov: AdventureReservation[] = new Array();
  dec: AdventureReservation[] = new Array();
  year2021Array: AdventureReservation[] = new Array();
  year2022Array: AdventureReservation[] = new Array();
  date: Date = new Date();
  add: AdditionalService[] = new Array();
  firstAdv: AdventureReservation[] = new Array();
  secondAdv: AdventureReservation[] = new Array();
  thirdAdv: AdventureReservation[] = new Array();
  forthAdv: AdventureReservation[] = new Array();
  fifthAdv: AdventureReservation[] = new Array();
  sixtAdv: AdventureReservation[] = new Array();
  seventhAdv: AdventureReservation[] = new Array();
  firstDayIncome: number = 0;
  secondDayIncome: number = 0;
  thirdDayIncome: number = 0;
  forthDayIncome: number = 0;
  fifthDayIncome: number = 0;
  sixthDayIncome: number = 0;
  seventhDayIncome: number = 0;

  constructor(private _fishingAdventureReservationService: AdventureReservationService, private _router: Router, private _route: ActivatedRoute,
              private _fishingAdventureService: FishingAdventureService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.adventure.id = +this._route.snapshot.paramMap.get('id');
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
    this.firstDayIncome = 0;
    this.secondDayIncome = 0;
    this.thirdDayIncome = 0;
    this.forthDayIncome = 0;
    this.fifthDayIncome = 0;
    this.sixthDayIncome = 0;
    this.seventhDayIncome = 0;
    this.firstAdv = new Array();
    this.secondAdv = new Array();
    this.thirdAdv = new Array();
    this.forthAdv = new Array();
    this.fifthAdv = new Array();
    this.sixtAdv = new Array();
    this.seventhAdv = new Array();
  }

  determineMonth(b: AdventureReservation){
    var start =  new Date(Number(b.startDate))

    if(start.getMonth() == 0) {
      this.jan.push(b)
    }else if(start.getMonth() == 1){
      this.feb.push(b)
    }else if(start.getMonth() == 2){
      this.mar.push(b)
    }else if(start.getMonth() == 3){
      this.apr.push(b)
    }else if(start.getMonth() == 4){
      this.may.push(b)
    }else if(start.getMonth() == 5){
      this.jun.push(b)
    }else if(start.getMonth() == 6){
      this.jul.push(b)
    }else if(start.getMonth() == 7){
      this.aug.push(b)
    }else if(start.getMonth() == 8){
      this.sep.push(b)
    }else if(start.getMonth() == 9){
      this.oct.push(b)
    }else if(start.getMonth() == 10){
      this.nov.push(b)
    }else if(start.getMonth() == 11){
      this.dec.push(b)
    }
  }

  year2021func(){
    this.year2022 = false
    this.year2021 = true
    this.loadData()
  }

  year2022func(){
    this.year2022 = true
    this.year2021 = false
    this.loadData()
  }

  loadData() {
    this._fishingAdventureService.getFishingAdventureById(this.adventure.id).subscribe(
      (adventure: FishingAdventure) =>{
        this.adventure = adventure
        console.log(this.adventure)
      }
    )

    this._fishingAdventureReservationService.getAllByFishingAdventureId(this.adventure.id).subscribe(
      (reservations: AdventureReservation[]) =>{
        this.initializeArrays()

        for(let b of reservations) {
          if (!b.availabilityPeriod && !b.isAvailable && Number(b.startDate) >= 1640991600000) {
            this.year2022Array.push(b)
          }
        }
        for(let b of reservations) {
          if (!b.availabilityPeriod && !b.isAvailable && Number(b.startDate) >= 1609455600000 && Number(b.startDate) < 1640991600000) {
            this.year2021Array.push(b)
          }
        }

        if (this.year2022) {
          for(let b of reservations) {
            if (!b.availabilityPeriod && !b.isAvailable && Number(b.startDate) >= 1640991600000) {
              this.determineMonth(b);
            }
          }
        } else if (this.year2021) {
          for(let b of reservations) {
            if (!b.availabilityPeriod && !b.isAvailable && Number(b.startDate) >= 1609455600000 && Number(b.startDate) < 1640991600000) {
              this.determineMonth(b);
            }
          }
        }

        this.chartMonthly = new Chart({
            chart: {
              type: 'bar',
            },
            credits: {
              enabled: false,
            },
            title: {
              text: 'Izvestaj o rezervacijama usluge na mesecnom nivou',
            },
            yAxis: {
              visible: true,
              gridLineColor: '#fff',
            },
            legend: {
              enabled: false,
            },
            xAxis: {
              lineColor: '#fff',
              categories: [
                'Jan',
                'Feb',
                'Mar',
                'Apr',
                'Maj',
                'Jun',
                'Jul',
                'Aug',
                'Sep',
                'Okt',
                'Nov',
                'Dec',
              ],
            },
            plotOptions: {
              series: {
                borderRadius: 5,
              } as any,
            },
            series: [
              {
                type: 'bar',
                color: '#fc5185',
                data: [
                  {y: this.jan.length},
                  {y: this.feb.length},
                  {y: this.mar.length},
                  {y: this.apr.length},
                  {y: this.may.length},
                  {y: this.jun.length},
                  {y: this.jul.length},
                  {y: this.aug.length},
                  {y: this.sep.length},
                  {y: this.oct.length},
                  {y: this.nov.length},
                  {y: this.dec.length},
                ],
              },
            ],
          }
        );

        this.chartYearly = new Chart({
            chart: {
              type: 'bar',
            },
            credits: {
              enabled: false,
            },
            title: {
              text: 'Izvestaj o rezervacijama usluge na godisnjem nivou',
            },
            yAxis: {
              visible: true,
              gridLineColor: '#fff',
            },
            legend: {
              enabled: false,
            },
            xAxis: {
              lineColor: '#fff',
              categories: [
                '2021',
                '2022',
                //'2023',
              ],
            },
            plotOptions: {
              series: {
                borderRadius: 5,
              } as any,
            },
            series: [
              {
                type: 'bar',
                color: '#8DC3A7',
                data: [
                  {y:  this.year2021Array.length},
                  {y:  this.year2022Array.length},
                ],
              },
            ],
          }
        );

        var danas = new Date().getMilliseconds()
        var weekAgo = Number(this.date) - (86400000 * 6)
        var zero = new Date(weekAgo - 86400000)

        var firstDay = new Date(weekAgo)
        var secondDay = new Date(weekAgo + 86400000)
        var thirdDay = new Date(weekAgo + 2*86400000)
        var forthdDay = new Date(weekAgo + 3*86400000)
        var fifthdDay = new Date(weekAgo + 4*86400000)
        var sixtDay = new Date(weekAgo + 5*86400000)

        for(let r of reservations) {
          console.log(r)
          // console.log(this.adventure)
          if (!r.isAvailable && !r.availabilityPeriod) {
            if (Number(r.startDate) >= Number(zero) && Number(r.startDate) < Number(firstDay)) {
              this.firstAdv.push(r)
              this.firstDayIncome = this.firstDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(firstDay) && Number(r.startDate) < Number(secondDay)) {
              this.secondAdv.push(r);
              this.secondDayIncome = this.secondDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(secondDay) && Number(r.startDate) < Number(thirdDay)) {
              this.thirdAdv.push(r);
              this.thirdDayIncome = this.thirdDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(thirdDay) && Number(r.startDate) < Number(forthdDay)) {
              this.forthAdv.push(r);
              this.forthDayIncome = this.forthDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(forthdDay) && Number(r.startDate) < Number(fifthdDay)) {
              this.fifthAdv.push(r);
              this.fifthDayIncome = this.fifthDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(fifthdDay) && Number(r.startDate) < Number(sixtDay)) {
              this.sixtAdv.push(r);
              this.sixthDayIncome = this.sixthDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(sixtDay) && Number(r.startDate) < Number(weekAgo)) {
              this.seventhAdv.push(r);
              this.seventhDayIncome = this.seventhDayIncome + this.calculateTotalPrice(r);
            }
          }
        }

        this.chartWeekly = new Chart({
            chart: {
              type: 'bar',
            },
            credits: {
              enabled: false,
            },
            title: {
              text: 'Izvestaj o rezervacijama usluge na nedeljnom nivou',
            },
            yAxis: {
              visible: true,
              gridLineColor: '#fff',
            },
            legend: {
              enabled: false,
            },
            xAxis: {
              lineColor: '#fff',
              categories: [
                this.date.toString(),
                sixtDay.toString(),
                fifthdDay.toString(),
                forthdDay.toString(),
                thirdDay.toString(),
                secondDay.toString(),
                firstDay.toString(),
              ],
            },
            plotOptions: {
              series: {
                borderRadius: 5,
              } as any,
            },
            series: [
              {
                type: 'bar',
                color: '#E090DF',
                //#506ef9
                data: [
                  {y: this.seventhAdv.length},
                  {y: this.sixtAdv.length},
                  {y: this.fifthAdv.length},
                  {y: this.forthAdv.length},
                  {y: this.thirdAdv.length},
                  {y: this.secondAdv.length},
                  {y: this.firstAdv.length},
                ],
              },
            ],
          }
        );


        this.chartWeeklyIncome = new Chart({
            chart: {
              type: 'bar',
            },
            credits: {
              enabled: false,
            },
            title: {
              text: 'Izvestaj o prihodima usluge na nedeljnom nivou',
            },
            yAxis: {
              visible: true,
              gridLineColor: '#fff',
            },
            legend: {
              enabled: false,
            },
            xAxis: {
              lineColor: '#fff',
              categories: [
                this.date.toString(),
                sixtDay.toString(),
                fifthdDay.toString(),
                forthdDay.toString(),
                thirdDay.toString(),
                secondDay.toString(),
                firstDay.toString(),
              ],
            },
            plotOptions: {
              series: {
                borderRadius: 5,
              } as any,
            },
            series: [
              {
                type: 'bar',
                color: '#FFBF00',
                //#506ef9
                data: [
                  {y: this.seventhDayIncome},
                  {y: this.sixthDayIncome},
                  {y: this.fifthDayIncome},
                  {y: this.forthDayIncome},
                  {y: this.thirdDayIncome},
                  {y: this.secondDayIncome},
                  {y: this.firstDayIncome},
                ],
              },
            ],
          }
        );

      }
    )
  }

  calculateTotalPrice(r: AdventureReservation){
    var additionalPrice = 0;
    for(let a of r.additionalServices) {
      additionalPrice = additionalPrice + a.price;
    }
    console.log(this.adventure)
    if (r.cancelled && !this.adventure.cancellationFree){
      var percantage = this.adventure.cancellationFee;
      console.log(percantage)
      console.log(this.adventure.cancellationFee)
      var totalPrice = r.price + additionalPrice;
      console.log(totalPrice)
      totalPrice = percantage * totalPrice * 0.01;
      console.log(1111)
      return totalPrice;
    }
    else if(r.cancelled && this.adventure.cancellationFree)
    {
      console.log(2222)
      return 0;
    }
    else {
      console.log(3333)
      console.log(this.adventure.cancellationFree)
      return r.price + additionalPrice;
    }
   }

}
