import { Routes, Route } from 'react-router-dom';
import NavbarPrivate from './components/NavbarPrivate';
import NavbarPublic from './components/NavbarPublic';
import Home from './pages/Home';
import Join from './pages/Join';
import NewMeeting from './pages/NewMeeting';

function App() {
  return (
    <>
      <NavbarPublic />
      {/* <NavbarPrivate /> */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/oauth2/redirect" element={<Home />} />
        <Route path="/join" element={<Join />}></Route>
        <Route path="/boards" element={<NewMeeting />} />
      </Routes>
    </>
  );
}

export default App;
