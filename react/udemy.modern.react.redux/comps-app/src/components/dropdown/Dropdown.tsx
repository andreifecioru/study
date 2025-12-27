import {useState, useEffect, useRef} from 'react';
import {GoChevronDown, GoChevronLeft} from "react-icons/go";
import DropDownItem, {type DropDownItemProps} from '@/components/dropdown/DropDownItem.tsx';
import Panel from '@/components/panel/Panel.tsx';

interface DropdownProps {
  options: DropDownItemProps[],
  value: DropDownItemProps | null,
  onChange: (option: DropDownItemProps) => void,
}

function Dropdown(props: DropdownProps) {
  const [isExpanded, setIsExpanded] = useState(false);

  const dropdownRef= useRef<HTMLDivElement>(null);


  useEffect(() => {
    const abortCtrl = new AbortController();

    const handleClick = (event: MouseEvent): void => {
      if (!dropdownRef.current) return;

      if (!dropdownRef.current.contains(event.target as Node)) {
        setIsExpanded(false);
      }
    }

    document.addEventListener('click', handleClick, {
      signal: abortCtrl.signal,
      capture: true
    });

    return () => abortCtrl.abort();
  }, []);

  const renderSelectionBox = () => {
    const selectedOption = !props.value
      ? (
        <div className="italic text-gray-700">
          Select...
        </div>
      )
      : <DropDownItem {...props.value} />

    return (
      <Panel
        className="flex justify-between items-center cursor-pointer"
        onClick={() => setIsExpanded(current => !current)}
      >
        {selectedOption}
        <span className="text-2xl">
          {isExpanded ? <GoChevronDown/> : <GoChevronLeft/>}
        </span>
      </Panel>
    );
  }

  const renderDropdownBox = () => {
    const handleClick = (option: DropDownItemProps): void => {
      setIsExpanded(false);
      props.onChange(option);
    }

    return (
      <Panel className="absolute top-full mt-0.5 z-10 cursor-pointer">
        {props.options.map((option) => {
          return (
            <div
              className="py-1 hover:bg-gray-100 cursor-pointer"
              key={option.value}
              onClick={() => handleClick(option)}
            >
              <DropDownItem {...option} />
            </div>
          );
        })}
      </Panel>
    );
  }

  return (
    <div ref={dropdownRef} className="dropdown-container relative">
      {renderSelectionBox()}
      {isExpanded && renderDropdownBox()}
    </div>
  );
}

export default Dropdown;
export {type DropdownProps, type DropDownItemProps};