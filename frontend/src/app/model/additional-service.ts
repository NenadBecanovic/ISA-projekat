export class AdditionalService {
    name: String;
    price: number;
    checked: boolean;

    constructor(name: String, price: number, checked: boolean){
        this.name = name;
        this.price = price;
        this.checked = checked;
    }

}
