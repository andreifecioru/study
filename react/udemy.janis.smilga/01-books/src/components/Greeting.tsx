type GreetingProps = {
  message: string;
};

function Greeting(props: GreetingProps) {
  return <p>{props.message}</p>;
}

export default Greeting;
export { type GreetingProps };
