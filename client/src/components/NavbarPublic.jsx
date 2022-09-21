import { Link } from 'react-router-dom';
import { useState } from 'react';
import styled from 'styled-components';
import LoginModal from './LoginModal';

const NavbarPublic = () => {
  const [showModal, setShowModal] = useState(false);

  const openModal = () => {
    setShowModal(!showModal);
  };

  return (
    <NavbarContainer>
      <Logo />
      <LoginButton onClick={openModal}>로그인</LoginButton>
      <LoginModal showModal={showModal} setShowModal={setShowModal} />
    </NavbarContainer>
  );
};

export const NavbarContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  box-shadow: 0 0 5px 3px rgba(0, 0, 0, 0.1);
`;

export const Logo = () => {
  return (
    <LogoLink to="/">
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

const LoginButton = styled.button`
  width: 80px;
  padding: 10px;
  background-color: #ffffff;
  border: 1px solid ${({ theme }) => theme.colors.purple1};
  color: ${({ theme }) => theme.colors.purple1};
  border-radius: 4px;
  cursor: pointer;
  :hover {
    border: 1px solid ${({ theme }) => theme.colors.purple2};
    color: ${({ theme }) => theme.colors.purple2};
  }
`;

export default NavbarPublic;