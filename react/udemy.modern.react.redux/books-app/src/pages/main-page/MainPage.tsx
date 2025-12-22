import './MainPage.sass';

import {v4 as uuid4} from 'uuid';
import axios from "axios";
import {useEffect, useState, useRef} from "react";
import BooksApiClient, {type BookModel} from "../../api/BooksApiClient.ts";

import BookForm from '@/components/book-form/BookForm';
import BookGrid from '@/components/book-grid/BookGrid';

const booksApiClient = new BooksApiClient();


function MainPage() {
  const [bookList, setBookList] = useState([] as BookModel[]);
  const [loading, setLoading] = useState(false)
  const abortController = useRef<AbortController|null>(null);

  useEffect(() => {
    abortController.current = new AbortController();

    const fetchBooks = async () => {
      setLoading(true);

      try {
        const bookModels = await booksApiClient.fetchAll(abortController.current);
        setBookList(bookModels);
      } catch (error) {
        if (!axios.isCancel(error)) {
          console.error('ERROR: failed to fetch book list:', error);
        }
      } finally {
        setLoading(false)
      }
    }

    void fetchBooks();

    return () => {
      abortController.current?.abort();
    }
  }, []);

  const handleBookTitleChanged = async (id: string | number, title: string): Promise<void> => {
    console.log(`Book title changed to ${title} for book with ID: ${id}`);
    const updatedBooks = [...bookList];
    const targetBook = updatedBooks.find(book => book.id === id);
    if (targetBook) {
      targetBook.title = title;
      try {
        void await booksApiClient.save(targetBook);
        setBookList(updatedBooks);
      } catch (error) {
        console.error(`ERROR: failed to update book: ${targetBook}`, error);
      }
    }

  }

  const handleBookDeleted = async (id: number): Promise<void> => {
    console.log(`Book with ID: ${id} was deleted.`)
    try {
      void await booksApiClient.delete(id);
      const updatedBooks = bookList.filter(book => book.id !== id);
      setBookList(updatedBooks);
    } catch (error) {
      console.error(`ERROR: failed to delete book with ID: ${id}`, error);
    }
  }

  const handleNewBookAdded = async (title: string): Promise<void> => {
    if (title !== '') {
      try {
        const newBook = await booksApiClient.save({
          title: title,
          image: `https://picsum.photos/seed/${uuid4()}/300/200`
        });
        setBookList([...bookList, newBook])
      } catch (error) {
        console.error(`ERROR: failed to add new book with title ${title}`, error);
      }
    }
  }

  return (
    <div className="main-page">
      {loading ? (
        <div className="main-page__loading-container">
          Loading...
        </div>
      ) : (
        <div className="main-page__grid-container">
          <BookGrid
            books={bookList}
            onBookTitleChanged={handleBookTitleChanged}
            onBookDeleted={handleBookDeleted}
          />
        </div>
      )}
      <div className="main-page__form-container">
        <BookForm onBookAdded={handleNewBookAdded}/>
      </div>
    </div>
  );
}

export default MainPage;