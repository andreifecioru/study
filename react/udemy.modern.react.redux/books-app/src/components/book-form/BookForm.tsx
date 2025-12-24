import './BookForm.sass';
import {type FormEvent, useState, useContext} from "react";
import {BooksContext} from '@/contexts/BooksContext.tsx';

function BookForm() {
  const booksCtx = useContext(BooksContext)
  const [bookTitle, setBookTitle] = useState('');
  const handleFormSubmit = async (event: FormEvent) => {
    event.preventDefault();
    console.log('Form was submitted.');
    void await booksCtx.createBook(bookTitle);
    setBookTitle('');
  }

  return (
    <div className="book-form__container">
      <h2>Add a new book</h2>
      <form className="book-form__form" onSubmit={handleFormSubmit}>
        <div className="book-form__form__inputs-container">
          <label>Book title</label>
          <input name="title"
                 placeholder="Title for a new book"
                 value={bookTitle}
                 onChange={(ev) => setBookTitle(ev.target.value)}/>
        </div>

        <div className="book-form__form__button-container">
          <button type="submit">Add book</button>
        </div>
      </form>
    </div>
  );
}

export default BookForm;