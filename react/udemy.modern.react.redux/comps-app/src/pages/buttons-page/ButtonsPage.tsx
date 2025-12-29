import './ButtonsPage.sass';
import {GoBell} from "react-icons/go";


import Button from '@/components/button/Button';

function ButtonsPage() {
  return (
    <div className="container divide-y-16 divide-white">
      <div className="flex gap-x-16">
        <Button primary> <GoBell/> Click me!</Button>
        <Button primary rounded>Click me!</Button>
        <Button primary outline>Click me!</Button>
        <Button primary rounded outline>Click me!</Button>
      </div>
      <div className="flex gap-x-16">
        <Button secondary>Click me!</Button>
        <Button secondary rounded>Click me!</Button>
        <Button secondary outline>Click me!</Button>
        <Button secondary rounded outline>Click me!</Button>
      </div>
      <div className="flex gap-x-16">
        <Button success>Click me!</Button>
        <Button success rounded>Click me!</Button>
        <Button success outline>Click me!</Button>
        <Button success rounded outline>Click me!</Button>
      </div>
      <div className="flex gap-x-16">
        <Button warning>Click me!</Button>
        <Button warning rounded>Click me!</Button>
        <Button warning outline>Click me!</Button>
        <Button warning rounded outline>Click me!</Button>
      </div>
      <div className="flex gap-x-16">
        <Button danger>Click me!</Button>
        <Button danger rounded>Click me!</Button>
        <Button danger outline>Click me!</Button>
        <Button danger rounded outline>Click me!</Button>
      </div>
    </div>
  );
}

export default ButtonsPage;