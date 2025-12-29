import {useContext} from 'react';
import type {ReactNode} from 'react';
import {NavigationContext} from '@/contexts/NavigationProvider.tsx';

type RouteProps = {
  path: string
  children: ReactNode
}

function Route({path, children}: RouteProps) {
  const { path: currentPath } = useContext(NavigationContext);
  return (currentPath === path) && children;
}

export default Route;
export {type RouteProps};