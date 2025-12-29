import './App.sass'
import MainPage from '@/pages/main-page/MainPage.tsx';
import NavigationProvider from '@/contexts/NavigationProvider.tsx';

function App() {
  return (
    <div className="flex flex-col flex-1 p-4">
      <NavigationProvider>
        <MainPage />
      </NavigationProvider>
    </div>
  )
}

export default App
