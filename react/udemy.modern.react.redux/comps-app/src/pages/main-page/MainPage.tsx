import NavBar from '@/components/navbar/NavBar.tsx';
import Panel from '@/components/panel/Panel.tsx';
import Route from '@/components/route/Route.tsx';
import HomePage from '@/pages/home-page/HomePage.tsx';
import ButtonsPage from '@/pages/buttons-page/ButtonsPage.tsx';
import AccordionPage from '@/pages/accordion-page/AccordionPage.tsx';
import DropdownPage from '@/pages/dropdown-page/DropdownPage.tsx';
import ModalPage from '@/pages/modal-page/ModalPage.tsx';
import TablePage from '@/pages/table-page/TablePage.tsx';


const NAV_OPTIONS = [
  {label: 'Home', link: '/home', page: <HomePage/>},
  {label: 'Buttons', link: '/buttons', page: <ButtonsPage/>},
  {label: 'Accordion', link: '/accordion', page: <AccordionPage/>},
  {label: 'Dropdown', link: '/dropdown', page: <DropdownPage/>},
  {label: 'Modal', link: '/modal', page: <ModalPage/>},
  {label: 'Table', link: '/table', page: <TablePage/>},
]

function MainPage() {
  return (
    <div className="flex h-full gap-2">
      <NavBar options={NAV_OPTIONS}/>
      <Panel className="grow">
        <div className="m-4">
          {NAV_OPTIONS.map(option => {
            return (
              <Route key={option.label} path={option.link}>{option.page}</Route>
            );
          })}
        </div>
      </Panel>
    </div>
  );
}

export default MainPage;

