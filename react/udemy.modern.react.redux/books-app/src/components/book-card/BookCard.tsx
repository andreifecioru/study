import './BookCard.sass';

import {type FormEvent, type MouseEvent, useEffect, useState} from "react";

import {useContext} from 'react';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faPencil, faTrash} from '@fortawesome/free-solid-svg-icons';
import {BooksContext} from '@/contexts/BooksContext.tsx';

type BookCardProps = {
  id: number,
  title: string,
  image: string,
}

function BookCard(props: BookCardProps) {
  const {updateBook, deleteBook} = useContext(BooksContext);
  const [editMode, setEditMode] = useState(false);
  const [bookTitle, setBookTitle] = useState(props.title);

  useEffect(() => {
    const handleBodyClick = async () => {
      if (editMode) {
        void await updateBook(props.id, bookTitle);
        setEditMode(false);
      }
    }
    document.body.addEventListener('click', handleBodyClick);

    return () => {
      document.body.removeEventListener('click', handleBodyClick);
    }

  }, [editMode, bookTitle, props.id, updateBook]);

  const handleEditClick = (event: MouseEvent<HTMLButtonElement>) => {
    event.stopPropagation();
    console.log(`Editing card: ${props.title}`);
    setEditMode(!editMode);
  };

  const handleDeleteClick = async () => {
    console.log(`Deleting card: ${props.title}`);
    void await deleteBook(props.id);
  }

  const handleTitleFormSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setEditMode(false);
    void await updateBook(props.id, bookTitle);
  }

  const renderTitleEditForm = () => (
    <form className="book-card__form-title" onSubmit={handleTitleFormSubmit}>
      <input
        value={bookTitle}
        onClick={(ev) => ev.stopPropagation()}
        onChange={(ev) => setBookTitle(ev.target.value)}
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