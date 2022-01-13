import { UserInfo } from "./user-info";

export class DeleteRequest {

  id: number = 0;
  description: string = '';
  userInfo: UserInfo = new UserInfo(0,'','','','');
  isAnswered: boolean = false;
}
