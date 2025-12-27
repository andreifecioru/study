import {useState, type ReactNode} from 'react';
import { GoChevronRight, GoChevronDown} from "react-icons/go";

interface AccordionSection {
  id: string,
  title: string,
  text: string,
}

interface AccordionProps {
  sections: AccordionSection[]
}

function Accordion(props: AccordionProps) {
  const [activeSectionId, setActiveSectionId] = useState(undefined as string|undefined);

  const handleClick = (sectionId: string) => {
    setActiveSectionId(activeSectionId === sectionId ? undefined : sectionId);
  };

  const renderSection = (section: AccordionSection): ReactNode => {
    const isActive = section.id === activeSectionId;

    return (
      <div key={section.id}>
        <div
          className="flex justify-between p-3 bg-gray-100 border-b items-center cursor-pointer"
          onClick={() => handleClick(section.id)}
        >
          {section.title}
          <span className="text-2xl">
            {isActive ? <GoChevronDown /> : <GoChevronRight/>}
          </span >
        </div>
        {isActive && (
          <div className="p-1 border-b">
            {section.text}
          </div>
        )}
      </div>
    );
  }

  return (
    <div className="border-x border-t rounded">
      {props.sections.map(renderSection)}
    </div>
  );
}

export default Accordion;
export {type AccordionProps, type AccordionSection};