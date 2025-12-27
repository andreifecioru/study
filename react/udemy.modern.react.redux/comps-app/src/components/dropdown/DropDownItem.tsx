import {type JSX} from 'react';

interface DropDownItemProps {
  label: string,
  value: string,
  icon: JSX.Element
}


function DropDownItem(props: DropDownItemProps) {
  return (
    <div className="flex items-center">
      <span className="pr-2 text-2xl">{props.icon}</span>
      {props.label}
    </div>
  );
}

export default DropDownItem;
export {type DropDownItemProps};