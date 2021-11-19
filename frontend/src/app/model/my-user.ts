import {Address} from "./address";

export class MyUser {

  firstName: String;
  lastName: String;
  email: String;
  password: String;
  username: String;
  authority: String;
  addressDTO: Address


  constructor(firstName: String, lastName: String, email: String, password: String, username: String, authority: String, addressDTO: Address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.username = username;
    this.authority = authority;
    this.addressDTO = addressDTO;
  }
}
