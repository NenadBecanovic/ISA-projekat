import {Address} from "./address";

export class MyUser {

  id: number;
  firstName: String;
  lastName: String;
  email: String;
  password: string;
  username: String;
  authority: String;
  addressDTO: Address;
  phoneNumber: String;
  reasonForRegistration: String;
  passwordChange: boolean = false;
  penalties: number = 0;

  constructor(id: number, firstName: String, lastName: String, email: String, password: string, username: String, authority: String, addressDTO: Address, phoneNumber: String, reasonForRegistration: String) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.username = username;
    this.authority = authority;
    this.addressDTO = addressDTO;
    this.phoneNumber = phoneNumber;
    this.reasonForRegistration = reasonForRegistration;
  }
}
