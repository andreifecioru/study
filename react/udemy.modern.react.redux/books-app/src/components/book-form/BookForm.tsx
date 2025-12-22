import './BookForm.sass';
import {type FormEvent, useState} from "react";

type BookFormProps = {
  onBookAdded: (title: string) => void
}

function BookForm(props: BookFormProps) {
  const [bookTitle, setBookTitle] = useState('');
  const handleFormSubmit = (event: FormEvent) => {
    event.preventDefault();
    console.log('Form was submitted.');
    props.onBookAdded(bookTitle);
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
export {type BookFormProps};