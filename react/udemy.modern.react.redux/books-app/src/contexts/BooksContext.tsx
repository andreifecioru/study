import {createContext, type ReactNode} from 'react';
import {useBooks} from '@/hooks/BooksHook.ts';

import {type BookModel} from '@/api/BooksApiClient.ts';

const BooksContext = createContext<BooksContextValue>({
  books: [],
  fetchBooks: async (_abortController) => { },
  createBook: async (_title) => { },
  updateBook: async (_id, _title) => { },
  deleteBook: async (_id) => { }
});

interface BooksContextProviderProps {
  children: ReactNode
}

interface BooksContextValue {
  books: BookModel[]
  fetchBooks: (abortController: AbortController | null) => Promise<void>
  createBook: (title: string) => Promise<void>
  updateBook: (id: number, title: string) => Promise<void>
  deleteBook: (id: number) => Promise<void>
}

function BooksContextProvider({children}: BooksContextProviderProps) {
  const booksCtx: BooksContextValue = useBooks();

  return (
    <BooksContext.Provider value={booksCtx}>
      {children}
    </BooksContext.Provider>
  );
}

export default BooksContextProvider;
export {BooksContext};