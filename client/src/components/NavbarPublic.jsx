import { useState } from 'react';
import styled from 'styled-components';
import LoginModal from './LoginModal';

const NavbarContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border: 1px solid blue;
`;

const LoginButton = styled.button`
  width: 100px;
  cursor: pointer;
`;

const NavbarPublic = () => {
  const [showModal, setShowModal] = useState(false);

  const openModal = () => {
    setShowModal(!showModal);
  };

  return (
    <NavbarContainer>
      <div>JUSE</div>
      <LoginButton onClick={openModal}>로그인</LoginButton>
      <LoginModal showModal={showModal} setShowModal={setShowModal} />
    </NavbarContainer>
  );
};

export default NavbarPublic;
