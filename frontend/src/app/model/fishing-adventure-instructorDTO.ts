import { Address } from "./address";

export class FishingAdventureInstructorDTO {
  id: number = 0;
  firstName: String = '';
  lastName: String = '';
  email: String = '';
  phone: String = ''; 
  personalDescription: String = '';
  username: String = '';
  addressDTO: Address = new Address(0,'','','',0,0,0);
}
