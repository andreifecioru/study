type TranslatorProps = {
  language: string;
  text: string;
};

function Translator({ language, text }: TranslatorProps) {
  const inputProps = {
    type: 'number',
    placeholder: '5',
    min: 5,
    max: 10,
    autoFocus: true,
    style: {
      border: '3px solid red',
    },
  };

  /* 
    Rules for JSX attrs:
      1. use camelCase for prop names
      2. use {} for numbers
      3. true is implied just by prop name being present; use {false} for false
      4. use className={...} to set the class attr
      5. inline styles are objects; dashes in prop-names are replaced by camelCase
          (padding-top becomes paddingTop)
  */

  return (
    <div>
      <h1>Translator</h1>
      <p>Language: {language}</p>
      <p>Text: {text}</p>

      <input {...inputProps} />
    </div>
  );
}

export default Translator;
