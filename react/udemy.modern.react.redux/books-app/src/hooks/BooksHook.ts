import {v4 as uuid4} from 'uuid';
import {useCallback, useState} from 'react';
import BooksApiClient, {type BookModel} from '@/api/BooksApiClient.ts';

const apiClient = new BooksApiClient();

function useBooks() {
  const [books, setBooks] = useState([] as BookModel[]);

  const fetchBooks = useCallback(async (abortController: AbortController | null): Promise<void> => {
    const fetchedBooks = await apiClient.fetchAll(abortController);
    setBooks(fetchedBooks);
  }, []);

  const createBook = useCallback(async (title: string): Promise<void> => {
    const response = await apiClient.save({
      title: title,
      image: `https://picsum.photos/seed/${uuid4()}/300/200`,
    });
    setBooks(prev => [...prev, response]);
  }, []);

  const updateBook = useCallback(async (id: number, title: string): Promise<void> => {
    setBooks(prev => {
      const targetBook = prev.find(book => book.id === id);
      if (targetBook) {
        // we do the API call async (the setBooks() requires a sync function)
        apiClient.save({...targetBook, title})
          .then(updatedBook => {
            setBooks(prev => prev.map(book => (book.id === id) ? updatedBook : book));
          });
      }
      return prev;
    });
  }, []);

  const deleteBook = useCallback(async (id: number): Promise<void> => {
    void await apiClient.delete(id);
    setBooks(prev => prev.filter((book) => book.id !== id));
  }, []);

  return {
    books,
    fetchBooks,
    createBook,
    updateBook,
    deleteBook
  };
}

export {useBooks};