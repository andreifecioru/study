import "./App.css";
import Greeting, { type GreetingProps } from "@/components/Greeting";

function App() {
  console.log("I want to code!");

  const greetProps: GreetingProps = {
    message: "Hello World!",
  };

  return <Greeting {...greetProps} />;
}

export default App;
