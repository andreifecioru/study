import './DropdownPage.sass';
import {useState} from 'react';
import Dropdown, {type DropDownItemProps} from '@/components/dropdown/Dropdown.tsx';
import {GoClock, GoCloud, GoCode, GoDatabase} from "react-icons/go";


function DropdownPage() {

  const options: DropDownItemProps[] = [
    {
      label: "1st element",
      value: "1st",
      icon: <GoClock />,
    },
    {
      label: "2nd element",
      value: "2nd",
      icon: <GoCloud />,
    },
    {
      label: "3rd element",
      value: "3rd",
      icon: <GoDatabase />,
    },
    {
      label: "4th element",
      value: "4th",
      icon: <GoCode />,
    },
  ]

  const [selectedOption, setSelectedOption] = useState(null as DropDownItemProps|null);

  const handleOptionSelected = (option: DropDownItemProps): void => {
    setSelectedOption(option);
  }

  return (
    <div className="max-w-100 mx-auto mt-5 dropdown-container">
      <div className="text-2xl mb-4">
        Selected item: {selectedOption ? selectedOption.label : 'none'}
      </div>
      <Dropdown
        options={options}

        // Make the dropdown a "controlled" input
        //   - we use the naming conventions for _form controls_
        value={selectedOption}
        onChange={handleOptionSelected}>
      </Dropdown>
    </div>
  );
}

export default DropdownPage;