import './AnimalCard.sass';

import ThumbsUpImg from './assets/thumbs-up.svg';
import ThumbsDownImg from './assets/thumbs-down.svg';

type AnimalCardProps = {
  id: number,
  kind: string,
  image: string,
  likes: number,
  onLikesIncrement: (kind: string) => void,
  onLikesDecrement: (kind: string) => void,
};


function AnimalCard(props: AnimalCardProps) {
  return (
      <div className="animal-card">
        <div className="animal-card__image">
          <img src={props.image} alt="Animal Card Image"/>
        </div>

        <div className="animal-card__kind">
          <p>{props.kind}</p>
        </div>

        <div className="animal-card__content">
          <div className="animal-card__like_count">
            <p>Likes: {props.likes}</p>
          </div>
          <div className="animal-card__actions">
            <button className="animal-card__action" onClick={() => props.onLikesIncrement(props.kind)}>
              <img src={ThumbsUpImg} alt="like action"/>
            </button>

            <button className="animal-card__action" onClick={() => props.onLikesDecrement(props.kind)}>
              <img src={ThumbsDownImg} alt="dislike action"/>
            </button>
          </div>
        </div>
      </div>
  );
}

export default AnimalCard;
export {type AnimalCardProps};