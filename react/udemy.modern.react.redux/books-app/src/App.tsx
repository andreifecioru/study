import './App.sass'

import MainPage from '@/pages/main-page/MainPage';
import BooksContextProvider from '@/contexts/BooksContext.tsx';

function App() {
  return (
    <BooksContextProvider>
      <MainPage />
    </BooksContextProvider>
  );
}

export default App
