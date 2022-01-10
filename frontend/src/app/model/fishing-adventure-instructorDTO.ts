import { Address } from "./address";

export class FishingAdventureInstructorDTO {
  id: number;
  firstName: String;
  lastName: String;
  email: String;
  username: String;
  addressDTO: Address;
  phoneNumber: String;
  personalDescription: String;

  constructor(id: number, firstName: String, lastName: String, email: String, username: String, addressDTO: Address, phoneNumber: String, personalDescription: String){
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.username = username;
    this.addressDTO = addressDTO;
    this.phoneNumber = phoneNumber;
    this.personalDescription = personalDescription;
  }
}
