import {MyUser} from "./my-user";

export class Subscription {

  id: number;
  subscribedUser: MyUser;
  owner: MyUser;


  constructor(id: number, subscribedUser: MyUser, owner: MyUser) {
    this.id = id;
    this.subscribedUser = subscribedUser;
    this.owner = owner;
  }
}

