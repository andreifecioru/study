import './MainPage.sass';

import axios from 'axios';
import {useContext, useEffect, useRef, useState} from 'react';
import {BooksContext} from '@/contexts/BooksContext.tsx';

import BookForm from '@/components/book-form/BookForm';
import BookGrid from '@/components/book-grid/BookGrid';


function MainPage() {
  const {fetchBooks} = useContext(BooksContext);
  const [loading, setLoading] = useState(false)
  const abortController = useRef<AbortController | null>(null);

  useEffect(() => {
    abortController.current = new AbortController();

    const loadBooks = async () => {
      setLoading(true);

      try {
        void await fetchBooks(abortController.current);
        setLoading(false);
      } catch (error) {
        if (!axios.isCancel(error)) {
          console.error('ERROR: failed to fetch book list:', error);
          setLoading(false);
        }
      }
    }

    void loadBooks();

    return () => {
      abortController.current?.abort();
    }
  }, [fetchBooks]);

  return (
    <div className="main-page">
      {loading ? (
        <div className="main-page__loading-container">
          Loading...
        </div>
      ) : (
        <div className="main-page__grid-container">
          <BookGrid/>
        </div>
      )}
      <div className="main-page__form-container">
        <BookForm/>
      </div>
    </div>
  );
}

export default MainPage;