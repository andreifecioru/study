import './App.sass'
import MainPage from '@/pages/navbar-page/MainPage.tsx';
import NavigationProvider from '@/contexts/NavigationProvider.tsx';

function App() {
  return (
    <div className="h-full p-4">
      <NavigationProvider>
        <MainPage />
      </NavigationProvider>
    </div>
  )
}

export default App
