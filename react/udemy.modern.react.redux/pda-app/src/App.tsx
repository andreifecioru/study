import PdaCard, {type PdaCardProps} from './components/PdaCard.tsx';
import Banner from './components/Banner.tsx';
import './App.sass'

function App() {
  const alexaProps: PdaCardProps = {
    text: 'A helpful assistant from Amazon',
    imgSource: '/images/alexa.png'
  };

  const cortanaProps: PdaCardProps = {
    text: 'A helpful assistant from Microsoft',
    imgSource: '/images/cortana.png'
  };

  const siriProps: PdaCardProps = {
    text: 'A helpful assistant from Apple',
    imgSource: '/images/siri.png'
  };

  return (
      <div>
        <Banner title="A selection of digital assistants" />
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
