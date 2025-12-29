import {createContext, type ReactNode} from 'react';
import {useNavigationData} from '@/hooks/NavigationHook.ts';

const NavigationContext = createContext<NavigationData>({
  path: '/',
  navigateTo: () => {},
});

type NavigationData = {
  path: string,
  navigateTo: (newPath: string) => void
}

type NavigationProviderProps = {
  children: ReactNode
}

function NavigationProvider({children}: NavigationProviderProps) {
  const navData = useNavigationData();

  return (
    <NavigationContext.Provider value={navData}>
      {children}
    </NavigationContext.Provider>
  );
}

export default NavigationProvider;
export {NavigationContext, type NavigationData};

