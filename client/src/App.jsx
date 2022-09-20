import { useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import NavbarPublic from './components/NavbarPublic';
import NavbarPrivate from './components/NavbarPrivate';
import Home from './pages/Home';
import Join from './pages/Join';
import Board from './pages/Board';
import OAuth from './pages/OAuth';

function App() {
  // TODO: 서버로 부터 token 받으면 클라이언트에 cookie에 담기
  return (
    <>
      <NavbarPublic />
      {/* <NavbarPrivate /> */}
      <Routes>
        <Route path='/' element={<Home />} />
        {/* OAuth 로그인 후 redirection */}
        {/* <Route path='/oauth2/redirect' element={<Home />} /> */}
        <Route path='/join' element={<Join />} />
        <Route path='/boards/:boardId' element={<Board />} />
        <Route path='/oauth2/redirect' element={<OAuth />} />
      </Routes>
    </>
  );
}

export default App;
