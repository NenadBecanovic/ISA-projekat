import {Address} from "./address";

export class MyUser {

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

  constructor(firstName: String, lastName: String, email: String, password: string, username: String, authority: String, addressDTO: Address, phoneNumber: String, reasonForRegistration: String) {
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
