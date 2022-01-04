export class UserTokenState {

  public accessToken: String;
  public expiresIn: number;


  constructor(accessToken: String, expiresIn: number) {
    this.accessToken = accessToken;
    this.expiresIn = expiresIn;
  }
}
