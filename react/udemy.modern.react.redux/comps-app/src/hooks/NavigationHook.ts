import {useCallback, useContext, useEffect, useState} from 'react';
import {NavigationContext, type NavigationData} from '@/contexts/NavigationProvider.tsx';

function useNavigationData(): NavigationData {
  const [currentPath, setCurrentPath] = useState(window.location.pathname);

  useEffect(() => {
    const abortCtrl = new AbortController();

    const handlePopState = () => {
      setCurrentPath(window.location.pathname);
    };

    window.addEventListener('popstate', handlePopState, {
      signal: abortCtrl.signal
    })

    return () => abortCtrl.abort();
  }, []);

  const navigateTo = useCallback((newPath: string) => {
    window.history.pushState({}, '', newPath);
    setCurrentPath(newPath);
  }, []);

  return {
    path: currentPath,
    navigateTo: navigateTo
  };
}

function useNavigation(): NavigationData {
  return useContext(NavigationContext);
}

export {useNavigationData, useNavigation};