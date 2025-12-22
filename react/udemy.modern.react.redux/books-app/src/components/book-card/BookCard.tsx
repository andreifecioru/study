import './BookCard.sass';

import {type FormEvent, useState} from "react";

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faPencil, faTrash} from '@fortawesome/free-solid-svg-icons';

type BookCardProps = {
  id: number,
  title: string,
  image: string,
  onBookTitleChanged: (id: number, title: string) => void
  onBookDeleted: (id: number) => void
}

function BookCard(props: BookCardProps) {
  const [editMode, setEditMode] = useState(false);
  const [bookTitle, setBookTitle] = useState(props.title);

  const handleEditClick = () => {
    console.log(`Editing card: ${props.title}`);
    setEditMode(true)
  };

  const handleDeleteClick = () => {
    console.log(`Deleting card: ${props.title}`);
    props.onBookDeleted(props.id);
  }

  const handleTitleFormSubmit = (event: FormEvent) => {
    event.preventDefault();
    setEditMode(false);
    props.onBookTitleChanged(props.id, bookTitle)
  }

  const handleTitleFormBlur = () => {
    setEditMode(false);
  }

  const renderTitleEditForm = () => (
    <form className="book-card__form-title" onSubmit={handleTitleFormSubmit}>
      <input
        value={bookTitle}
        onChange={(ev) => setBookTitle(ev.target.value)}
        onBlur={handleTitleFormBlur}
      />
    </form>
  );

  return (
    <div className="book-card">
      <div className="book-card__image">
        <img src={props.image} alt="Book Card Image"/>
      </div>

      <div className="book-card__title">
        {editMode ? (
          renderTitleEditForm()
        ) : (
          <p>{props.title}</p>
        )}
      </div>

      <div className="book-card__content">
        <div className="book-card__actions">
          <button className="book-card__action" onClick={handleEditClick}>
            <FontAwesomeIcon icon={faPencil}/>
          </button>

          <button className="book-card__action" onClick={handleDeleteClick}>
            <FontAwesomeIcon icon={faTrash}/>
          </button>
        </div>
      </div>
    </div>
  );
}

export default BookCard;
export {type BookCardProps};