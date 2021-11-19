export class Address {

  street: String;
  city : String;
  state: String;
  longitude: number;
  latitude: number;


  constructor(street: String, city: String, state: String, longitude: number, latitude: number) {
    this.street = street;
    this.city = city;
    this.state = state;
    this.longitude = longitude;
    this.latitude = latitude;
  }



}
