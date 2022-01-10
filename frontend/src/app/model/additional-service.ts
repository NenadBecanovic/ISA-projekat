export class AdditionalService {

  public houseId: number = 0;
  public boatId: number = 0;
  public adventureId: number = 0;
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
