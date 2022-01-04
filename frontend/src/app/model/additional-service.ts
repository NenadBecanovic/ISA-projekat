export class AdditionalService {

  id: number;
  name: String;
  price: number;
  checked: boolean;

  constructor(id: number, name: String, price: number, checked: boolean){
    this.id = id;
    this.name = name;
    this.price = price;
    this.checked = checked;
  }

}
