export class Image {

  id: number;
  imageUrl: String = '';

  constructor(imageUrl: String, id: number) {
    this.id = id;
    this.imageUrl = imageUrl;
  }
}
