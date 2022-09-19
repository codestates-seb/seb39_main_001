import { Routes, Route } from 'react-router-dom';
import NavbarPublic from './components/NavbarPublic';
import NavbarPrivate from './components/NavbarPrivate';
import Home from './pages/Home';

function App() {
  return (
    <>
      {/* <NavbarPublic /> */}
      <NavbarPrivate />
      <Routes>
        <Route path="/" element={<Home />} />
        {/* OAuth 로그인 후 redirection */}
        <Route path="/oauth2/redirect" element={<Home />} />
      </Routes>
    </>
  );
}

export default App;
