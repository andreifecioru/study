import { useState } from 'react';
import './App.css';
import Translator from './components/Translator';

function App() {
  const [language, setLanguage] = useState('es');
  const [text, setText] = useState('');

  return (
    <>
      <Translator language={language} text={text}/>
    </>
  );
}

export default App;
