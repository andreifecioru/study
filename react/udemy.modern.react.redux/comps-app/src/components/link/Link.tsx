import {type HTMLAttributes, type MouseEvent} from 'react';
import {useNavigation} from '@/hooks/NavigationHook.ts';

type LinkProps = HTMLAttributes<HTMLAnchorElement> & {
  to: string
}

function Link({children, to, ...rest}: LinkProps) {
  const {navigateTo} = useNavigation();

  const handleClick = (event: MouseEvent<HTMLAnchorElement>) => {
    // If the user is holding Ctrl or Meta (Cmd), let the browser handle it (open in new tab)
    if (event.metaKey || event.ctrlKey) {
      return;
    }
    event.preventDefault();

    // Perform the actual navigation
    navigateTo(to);
  }

  return (
    <a {...rest} href={to} onClick={handleClick}>
      {children}
    </a>
  );
}

export default Link;