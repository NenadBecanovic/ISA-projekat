import { Address } from "./address";

export class FishingAdventureInstructorDTO {
  id: number;
  firstName: String;
  lastName: String;
  email: String;
  username: String;
  addressDTO: Address;
  phoneNumber: String;
  description: String;

  constructor(id: number, firstName: String, lastName: String, email: String, username: String, addressDTO: Address, phoneNumber: String, description: String){
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.username = username;
    this.addressDTO = addressDTO;
    this.phoneNumber = phoneNumber;
    this.description = description;
  }
}
