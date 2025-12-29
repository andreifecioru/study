import type {ReactNode} from 'react';
import {twMerge} from 'tailwind-merge';
import {useNavigation} from '@/hooks/NavigationHook.ts';
import Panel from '@/components/panel/Panel.tsx';
import Link from '@/components/link/Link';

interface NavBarOption {
  label: string,
  link: string,
  page: ReactNode
}

type NavBarProps = {
  options: NavBarOption[]
}

function NavBar({options}: NavBarProps) {
  const {path} = useNavigation();
  return (
    <Panel className="w-56 h-full shrink-0">
      {options.map(option => {
        const classes = twMerge(
          'p-1 m-2 text-2xl border-l-3 border-white ',
          option.link === path && 'border-gray-500');

        return <div key={option.label}
                    className={classes}>
          <Link to={option.link}>
            {option.label}
          </Link>
        </div>
      })}
    </Panel>
  );
}

export default NavBar;
export {type NavBarOption, type NavBarProps};