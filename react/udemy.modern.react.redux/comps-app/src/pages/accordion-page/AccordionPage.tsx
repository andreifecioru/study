import Accordion, {type AccordionSection} from '@/components/accordion/Accordion.tsx';
import {v4 as uuid4} from 'uuid';


function AccordionPage() {
  const sections: AccordionSection[] = [
    {
      id: uuid4(),
      title: "Product offering",
      text: "We are offering lots of online coursed about many hot technologies.",
    },
    {
      id: uuid4(),
      title: "Pricing",
      text: "Pricing is very affordable. Please consult one of our sales officers.",
    },
    {
      id: uuid4(),
      title: "Contact us",
      text: "We are available on mail, social media and our website at https://company.com",
    },
  ];


  return (
    <div className="max-w-200 mx-auto m-2">
      <Accordion sections={sections}/>
    </div>
  );
}

export default AccordionPage;