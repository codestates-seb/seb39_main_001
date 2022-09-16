import { Routes, Route } from 'react-router-dom';
import NavbarPublic from './components/NavbarPublic';
import Home from './pages/Home';

function App() {
  return (
    <>
      <NavbarPublic />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/oauth2/redirect" element={<Home />} />
      </Routes>
    </>
  );
}

export default App;
