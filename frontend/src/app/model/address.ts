export class Address {

  id: number;
  street: String;
  city : String;
  state: String;
  longitude: number;
  latitude: number;
  postalCode: number;

  constructor(id: number, street: String, city: String, state: String, longitude: number, latitude: number, postalCode: number) {
    this.id = id;
    this.street = street;
    this.city = city;
    this.state = state;
    this.longitude = longitude;
    this.latitude = latitude;
    this.postalCode = postalCode;
  }
}
