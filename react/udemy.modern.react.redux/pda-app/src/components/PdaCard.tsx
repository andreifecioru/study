import './PdaCard.sass';

type PdaCardProps = {
  text: string,
  imgSource: string,
};

function PdaCard({text, imgSource}: PdaCardProps) {
  return (
      <div className="pda-card">
        <img className="pda-card__image" src={imgSource} alt="PDA Card Image"/>
        <div className="pda-card__content">
          <p>{text}</p>
        </div>
      </div>
  );
}

export default PdaCard;
export type {PdaCardProps};