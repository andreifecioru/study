import BaseApiClient, {type BaseModel} from '@/api/BaseApiClient';

interface BookModel extends BaseModel {
  title: string,
  image: string,
}

const ENDPOINT_URL = "http://localhost:3000/books";

class BooksApiClient extends BaseApiClient<BookModel> {
  constructor() {
    super(ENDPOINT_URL);
  }
}

export default BooksApiClient;
export {type BookModel};