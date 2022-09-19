import { Routes, Route } from 'react-router-dom';
import NavbarPublic from './components/NavbarPublic';
import Home from './pages/Home';
import Join from './pages/Join';

function App() {
  return (
    <>
      <NavbarPublic />
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/join' element={<Join />}></Route>
      </Routes>
    </>
  );
}

export default App;
