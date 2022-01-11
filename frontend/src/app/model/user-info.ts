
export class UserInfo {

    id: number;
    firstName: String;
    lastName: String;
    email: String;
    authority: String;

    constructor(id: number, firstName: string, lastName: string, email:string, authority: string){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.authority = authority;
    }
}
