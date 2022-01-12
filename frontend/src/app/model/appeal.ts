import { UserInfo } from "./user-info";

export class Appeal {

  reservationId: number = 0;
  review: string = '';
  hasHouse: boolean = false;
  hasHouseOwner: boolean =  false;
  hasBoat: boolean =  false;
  hasBoatOwner: boolean =  false;
  hasInstructor: boolean = false;
  ownerId: number = 0;
  senderId: number = 0;
  isAnswered: boolean = false;
  owner: UserInfo = new UserInfo(0,'','','','');
  guest: UserInfo = new UserInfo(0,'','','','');
}
