import { Link } from 'react-router-dom';
import { useState } from 'react';
import styled from 'styled-components';
import LoginModal from './LoginModal';
import { ReactComponent as Sun } from '../assets/icons/sun.svg';
import { ReactComponent as Moon } from '../assets/icons/moon.svg';

const NavbarPublic = ({ theme, toggleTheme }) => {
  const [showModal, setShowModal] = useState(false);

  const openModal = () => {
    setShowModal(!showModal);
  };

  return (
    <NavbarContainer>
      <NavbarSubContainer>
        <Logo />
        <NavButtons>
          {theme === 'light' ? (
            <DarkThemeBtn className='theme-btn' onClick={toggleTheme}>
              <Moon fill='#ffea00' />
            </DarkThemeBtn>
          ) : (
            <LightThemeBtn className='theme-btn' onClick={toggleTheme}>
              <Sun fill='#00e676' />
            </LightThemeBtn>
          )}
          <LoginButton onClick={openModal}>로그인</LoginButton>
        </NavButtons>
        <LoginModal showModal={showModal} setShowModal={setShowModal} />
      </NavbarSubContainer>
    </NavbarContainer>
  );
};

export const NavButtons = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 130px;
`;

export const NavbarContainer = styled.div`
  display: flex;
  justify-content: center;
  padding: 10px 0px;
  box-shadow: 0 0 5px 3px rgba(0, 0, 0, 0.1);
`;

export const NavbarSubContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  max-width: 1300px;
  padding: 0px 30px;
`;

export const Logo = () => {
  return (
    <LogoLink to='/'>
      JU:SE
      <p>Junior to Senior</p>
    </LogoLink>
  );
};

const LogoLink = styled(Link)`
  font-size: 30px;
  font-weight: 900;
  color: ${({ theme }) => theme.colors.purple1};
  > p {
    font-size: 10px;
  }
`;

export const LoginButton = styled.button`
  width: 80px;
  padding: 10px;
  background-color: ${({ theme }) => theme.background};
  border: 1px solid ${({ theme }) => theme.colors.purple1};
  color: ${({ theme }) => theme.colors.purple1};
  border-radius: 4px;
  cursor: pointer;
  :hover {
    background: ${({ theme }) => theme.colors.purple1};
    color: #ffffff;
  }
`;

export const DarkThemeBtn = styled.button`
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 30px;
  height: 30px;
  border: none;
  border-radius: 999px;
  background: #515151;
  z-index: 9999;
  cursor: pointer;
  :hover {
    background-color: #efefef;
  }
`;

export const LightThemeBtn = styled.button`
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 30px;
  height: 30px;
  border: none;
  border-radius: 999px;
  background: #efefef;
  cursor: pointer;
  :hover {
    background-color: #515151;
  }
`;

export default NavbarPublic;
