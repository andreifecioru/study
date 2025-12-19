import PdaCard, {type PdaCardProps} from './components/PdaCard.tsx';
import Banner from './components/Banner.tsx';

import AlexaImage from '../public/images/alexa.png'
import CortanaImage from '../public/images/cortana.png';
import SiriImage from '../public/images/siri.png';

function App() {
  const alexaProps: PdaCardProps = {
    text: 'A helpful assistant from Amazon',
    imgSource: AlexaImage
  };

  const cortanaProps: PdaCardProps = {
    text: 'A helpful assistant from Microsoft',
    imgSource: CortanaImage
  };

  const siriProps: PdaCardProps = {
    text: 'A helpful assistant from Apple',
    imgSource: SiriImage
  };

  return (
      <div>
        <Banner title="A selection of digital assistants"/>
        <div className="pda-grid-container">
          <div className="pda-grid">
            <PdaCard {...alexaProps}/>
            <PdaCard {...cortanaProps}/>
            <PdaCard {...siriProps}/>
          </div>
        </div>
      </div>
  )
}

export default App
