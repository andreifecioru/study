import './SearchBar.sass';

import {useState} from "react";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faMagnifyingGlass} from '@fortawesome/free-solid-svg-icons';

import {type FormEvent} from "react";

type SearchBarProps = {
  onSearchSubmit: (term: string) => void
}

function SearchBar(props: SearchBarProps) {
  const [term, setTerm] = useState('');

  const onFormSubmit = (event: FormEvent) => {
    event.preventDefault();
    props.onSearchSubmit(term);
  }

  return (
    <form className="search-bar" onSubmit={onFormSubmit}>
      <div className="search-bar__input-container">
        <FontAwesomeIcon icon={faMagnifyingGlass} className="search-bar__icon"/>
      </div>
      <input
        className="search-bar__input"
        placeholder="Search images..."
        value={term}
        onChange={(ev) => setTerm(ev.target.value)}
      />
    </form>
  );
}

export default SearchBar;
export {type SearchBarProps};