import './BookGrid.sass';

import {useContext} from 'react';
import {BooksContext} from '@/contexts/BooksContext.tsx';
import BookCard, {type BookCardProps} from '@/components/book-card/BookCard';
import type {BookModel} from '@/api/BooksApiClient';


function BookGrid() {
  const booksCtx = useContext(BooksContext);

  const bookCardProps = (book: BookModel): BookCardProps => {
    return {
      ...book,
      id: book.id ?? 0,
    }
  }

  return (
    <div className="book-grid">
      <div className="book-grid__container">
        {booksCtx.books.map(book =>
          <BookCard {...bookCardProps(book)} key={book.id}/>
        )}
      </div>
    </div>
  );
}

export default BookGrid;