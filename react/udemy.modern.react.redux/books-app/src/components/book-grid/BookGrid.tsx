import './BookGrid.sass';

import BookCard, {type BookCardProps} from '@/components/book-card/BookCard';
import type {BookModel} from '@/api/BooksApiClient';

type BookGridProps = {
  books: BookModel[]
  onBookTitleChanged: (id: string|number, title: string) => void
  onBookDeleted: (id: number) => void
}

function BookGrid(props: BookGridProps) {

  const bookCardProps = (book: BookModel): BookCardProps => {
    return {
      ...book,
      id: book.id ?? 0,
      onBookTitleChanged: props.onBookTitleChanged,
      onBookDeleted: props.onBookDeleted
    }
  }

  return (
    <div className="book-grid">
      <div className="book-grid__container">
        {props.books.map(book =>
            <BookCard {...bookCardProps(book)} key={book.id} />
        )}
      </div>
    </div>
  );
}

export default BookGrid;