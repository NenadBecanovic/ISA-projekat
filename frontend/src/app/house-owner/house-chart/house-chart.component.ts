import { Component, OnInit } from '@angular/core';
import {Chart} from "angular-highcharts";
import {Address} from "../../model/address";
import {AdditionalService} from "../../model/additional-service";
import {ActivatedRoute, Router} from "@angular/router";
import {Room} from "../../model/room";
import {House} from "../../model/house";
import {HouseReservation} from "../../model/house-reservation";
import {HouseReservationService} from "../../service/house-reservation.service";
import {HouseService} from "../../service/house.service";
import { AuthentificationService } from 'src/app/auth/authentification/authentification.service';
import { CompanyService } from 'src/app/service/company.service';
import { UserCategory } from 'src/app/model/user-category';

@Component({
  selector: 'app-house-chart',
  templateUrl: './house-chart.component.html',
  styleUrls: ['./house-chart.component.css']
})
export class HouseChartComponent implements OnInit {

  chartMonthly: Chart = new Chart();
  chartYearly: Chart = new Chart();
  chartWeekly: Chart = new Chart();
  chartWeeklyIncome: Chart = new Chart();
  address: Address = new Address(0,"","","",0,0,0)
  rooms: Room[] = new Array<Room>();
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  house: House = new House(0,'', this.address, '', '', 0, false, 0, this.rooms, this.additionalServices, 0, 0);
  courses: HouseReservation[] = new Array<HouseReservation>();
  year2021: boolean = false;
  year2022: boolean = true;
  jan: HouseReservation[] = new Array();
  feb: HouseReservation[] = new Array();
  mar: HouseReservation[] = new Array();
  apr: HouseReservation[] = new Array();
  may: HouseReservation[] = new Array();
  jun: HouseReservation[] = new Array();
  jul: HouseReservation[] = new Array();
  aug: HouseReservation[] = new Array();
  sep: HouseReservation[] = new Array();
  oct: HouseReservation[] = new Array();
  nov: HouseReservation[] = new Array();
  dec: HouseReservation[] = new Array();
  year2021Array: HouseReservation[] = new Array();
  year2022Array: HouseReservation[] = new Array();
  date: Date = new Date();
  add: AdditionalService[] = new Array();
  firstBoat: HouseReservation[] = new Array();
  secondBoat: HouseReservation[] = new Array();
  thirdBoat: HouseReservation[] = new Array();
  forthBoat: HouseReservation[] = new Array();
  fifthBoat: HouseReservation[] = new Array();
  sixtBoat: HouseReservation[] = new Array();
  seventhBoat: HouseReservation[] = new Array();
  firstDayIncome: number = 0;
  secondDayIncome: number = 0;
  thirdDayIncome: number = 0;
  forthDayIncome: number = 0;
  fifthDayIncome: number = 0;
  sixthDayIncome: number = 0;
  seventhDayIncome: number = 0;
  companyPercentage: number = 0;
  userCategory: UserCategory = new UserCategory();
  averageGrade: number = 0;

  constructor(private _houseReservationService: HouseReservationService, private _router: Router, private _route: ActivatedRoute,
              private _houseService: HouseService, private _authenticationService: AuthentificationService, private _companyService: CompanyService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.house.id = +this._route.snapshot.paramMap.get('id');
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

  determineMonth(b: HouseReservation){
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
    this._houseService.getHouseById(this.house.id).subscribe(
      (house: House) =>{
        this.house = house

        console.log(this.house)
        if (this.house.numberOfReviews != 0 && this.house.numberOfReviews != null) {
          this.averageGrade = this.house.ava / this.house.numberOfReviews;
        } else {
          this.averageGrade = 0
        }
      }
    )

    this._companyService.getCompanyPercentage().subscribe(
      (percentage: number) =>{
        this.companyPercentage = percentage;
      }
    )

    this._companyService.getUserCategory(this._authenticationService.getUserEmail()).subscribe(
      (userCategory: UserCategory) =>{
        this.userCategory = userCategory;
      }
    )

    this._houseReservationService.getAllByHouseIdPlane(this.house.id).subscribe(
      (houseReservations: HouseReservation[]) =>{
        // this.courses = boatReservations
        this.initializeArrays()

        for(let b of houseReservations) {
          if (!b.availabilityPeriod && !b.available && Number(b.startDate) >= 1640991600000) {
            this.year2022Array.push(b)
          }
        }
        for(let b of houseReservations) {
          if (!b.availabilityPeriod && !b.available && Number(b.startDate) >= 1609455600000 && Number(b.startDate) < 1640991600000) {
            this.year2021Array.push(b)
          }
        }

        if (this.year2022) {
          for(let b of houseReservations) {
            if (!b.availabilityPeriod && !b.available && Number(b.startDate) >= 1640991600000) {
              this.determineMonth(b);
              //this.year2022Array.push(b)
            }
          }
        } else if (this.year2021) {
          for(let b of houseReservations) {
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
              text: 'Izvestaj o rezervacijama vikendice na mesecnom nivou',
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
              text: 'Izvestaj o rezervacijama vikendice na godisnjem nivou',
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

        var weekAgo = Number(this.date) - (86400000 * 6)
        var zero = new Date(weekAgo - 86400000)
        var firstDay = new Date(weekAgo)
        var secondDay = new Date(weekAgo + 86400000)
        var thirdDay = new Date(weekAgo + 2*86400000)
        var forthdDay = new Date(weekAgo + 3*86400000)
        var fifthdDay = new Date(weekAgo + 4*86400000)
        var sixtDay = new Date(weekAgo + 5*86400000)

        for(let r of houseReservations) {
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
              text: 'Izvestaj o rezervacijama vikendice na nedeljnom nivou',
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
              text: 'Izvestaj o prihodima vikendice na nedeljnom nivou',
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

  calculateTotalPrice(r: HouseReservation){
    var additionalPrice = 0;
    for(let a of r.additionalServices) {
      additionalPrice = additionalPrice + a.price;
    }

    if (r.cancelled && !this.house.cancalletionFree){
      var percantage = this.house.cancalletionFee;
      var totalPrice = r.price + additionalPrice;
      totalPrice = percantage * totalPrice * 0.01;
      return totalPrice;
    }
    else if(r.cancelled && this.house.cancalletionFree)
    {
      return 0;
    }
    else {
      var companyProfit = (r.price + additionalPrice) * this.companyPercentage * 0.01;
      return r.price + additionalPrice - companyProfit + (companyProfit * this.userCategory.discountPercentage * 0.01);
    }
  }
}
