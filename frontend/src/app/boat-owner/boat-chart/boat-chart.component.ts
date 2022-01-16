import { Component, OnInit } from '@angular/core';
import { Chart } from 'angular-highcharts';
import {BoatReservationService} from "../../service/boat-reservation.service";
import {Address} from "../../model/address";
import {NavigationEquipment} from "../../model/navigation-equipment";
import {AdditionalService} from "../../model/additional-service";
import {Boat} from "../../model/boat";
import {BoatReservation} from "../../model/boat-reservation";
import {ActivatedRoute, Router} from "@angular/router";
import {BoatService} from "../../service/boat.service";

@Component({
  selector: 'app-boat-chart',
  templateUrl: './boat-chart.component.html',
  styleUrls: ['./boat-chart.component.css']
})
export class BoatChartComponent implements OnInit {

  chartMonthly: Chart = new Chart();
  chartYearly: Chart = new Chart();
  chartWeekly: Chart = new Chart();
  chartWeeklyIncome: Chart = new Chart();
  address: Address = new Address(0,"Luka 11","Novi Sad","Srbija",0,0,21000)
  navigationEquipment: NavigationEquipment = new NavigationEquipment(0,true, true, true, true);
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  boat: Boat = new Boat(0, '', '', '', 0, 0, '', 0, 0, 0, 0, false, 0, '', this.address, this.navigationEquipment, this.additionalServices, 0, 0);
  courses: BoatReservation[] = new Array<BoatReservation>();
  year2021: boolean = false;
  year2022: boolean = true;
  jan: BoatReservation[] = new Array();
  feb: BoatReservation[] = new Array();
  mar: BoatReservation[] = new Array();
  apr: BoatReservation[] = new Array();
  may: BoatReservation[] = new Array();
  jun: BoatReservation[] = new Array();
  jul: BoatReservation[] = new Array();
  aug: BoatReservation[] = new Array();
  sep: BoatReservation[] = new Array();
  oct: BoatReservation[] = new Array();
  nov: BoatReservation[] = new Array();
  dec: BoatReservation[] = new Array();
  year2021Array: BoatReservation[] = new Array();
  year2022Array: BoatReservation[] = new Array();
  date: Date = new Date();
  add: AdditionalService[] = new Array();
  firstBoat: BoatReservation[] = new Array();
  secondBoat: BoatReservation[] = new Array();
  thirdBoat: BoatReservation[] = new Array();
  forthBoat: BoatReservation[] = new Array();
  fifthBoat: BoatReservation[] = new Array();
  sixtBoat: BoatReservation[] = new Array();
  seventhBoat: BoatReservation[] = new Array();
  firstDayIncome: number = 0;
  secondDayIncome: number = 0;
  thirdDayIncome: number = 0;
  forthDayIncome: number = 0;
  fifthDayIncome: number = 0;
  sixthDayIncome: number = 0;
  seventhDayIncome: number = 0;

  constructor(private _boatReservationService: BoatReservationService, private _router: Router, private _route: ActivatedRoute,
              private _boatService: BoatService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.boat.id = +this._route.snapshot.paramMap.get('id');
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
    this.firstBoat = new Array();
    this.secondBoat = new Array();
    this.thirdBoat = new Array();
    this.forthBoat = new Array();
    this.fifthBoat = new Array();
    this.sixtBoat = new Array();
    this.seventhBoat = new Array();
  }

  determineMonth(b: BoatReservation){
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
    this._boatService.getBoatById(this.boat.id).subscribe(
      (boat: Boat) =>{
        this.boat = boat
      }
    )

    this._boatReservationService.getAllByBoatIdPlane(this.boat.id).subscribe(
      (boatReservations: BoatReservation[]) =>{
        // this.courses = boatReservations
        this.initializeArrays()

        for(let b of boatReservations) {
          if (!b.availabilityPeriod && !b.available && Number(b.startDate) >= 1640991600000) {
            this.year2022Array.push(b)
          }
        }
        for(let b of boatReservations) {
          if (!b.availabilityPeriod && !b.available && Number(b.startDate) >= 1609455600000 && Number(b.startDate) < 1640991600000) {
            this.year2021Array.push(b)
          }
        }

        if (this.year2022) {
          for(let b of boatReservations) {
            if (!b.availabilityPeriod && !b.available && Number(b.startDate) >= 1640991600000) {
              this.determineMonth(b);
              //this.year2022Array.push(b)
            }
          }
        } else if (this.year2021) {
          for(let b of boatReservations) {
            if (!b.availabilityPeriod && !b.available && Number(b.startDate) >= 1609455600000 && Number(b.startDate) < 1640991600000) {
              this.determineMonth(b);
              //this.year2021Array.push(b)
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
              text: 'Izvestaj o rezervacijama broda na mesecnom nivou',
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
                //#506ef9
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
              text: 'Izvestaj o rezervacijama broda na godisnjem nivou',
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
                //#506ef9
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

        for(let r of boatReservations) {
          if (!r.available && !r.availabilityPeriod) {
            if (Number(r.startDate) >= Number(zero) && Number(r.startDate) < Number(firstDay)) {
              this.firstBoat.push(r)
              this.firstDayIncome = this.firstDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(firstDay) && Number(r.startDate) < Number(secondDay)) {
              this.secondBoat.push(r);
              this.secondDayIncome = this.secondDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(secondDay) && Number(r.startDate) < Number(thirdDay)) {
              this.thirdBoat.push(r);
              this.thirdDayIncome = this.thirdDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(thirdDay) && Number(r.startDate) < Number(forthdDay)) {
              this.forthBoat.push(r);
              this.forthDayIncome = this.forthDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(forthdDay) && Number(r.startDate) < Number(fifthdDay)) {
              this.fifthBoat.push(r);
              this.fifthDayIncome = this.fifthDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(fifthdDay) && Number(r.startDate) < Number(sixtDay)) {
              this.sixtBoat.push(r);
              this.sixthDayIncome = this.sixthDayIncome + this.calculateTotalPrice(r);
            } else if (Number(r.startDate) >= Number(sixtDay) && Number(r.startDate) < Number(weekAgo)) {
              this.seventhBoat.push(r);
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
              text: 'Izvestaj o rezervacijama broda na nedeljnom nivou',
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
                  {y: this.seventhBoat.length},
                  {y: this.sixtBoat.length},
                  {y: this.fifthBoat.length},
                  {y: this.forthBoat.length},
                  {y: this.thirdBoat.length},
                  {y: this.secondBoat.length},
                  {y: this.firstBoat.length},
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
              text: 'Izvestaj o prihodima broda na nedeljnom nivou',
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

  calculateTotalPrice(r: BoatReservation){
    var additionalPrice = 0;
    for(let a of r.additionalServices) {
      additionalPrice = additionalPrice + a.price;
    }

    if (r.cancelled && !this.boat.cancalletionFree){
      var percantage = this.boat.cancalletionFee;
      var totalPrice = r.price + additionalPrice;
      totalPrice = percantage * totalPrice * 0.01;
      return totalPrice;
    }
    else if(r.cancelled && this.boat.cancalletionFree)
    {
      return 0;
    }
    else {
      return r.price + additionalPrice;
    }
  }

}
