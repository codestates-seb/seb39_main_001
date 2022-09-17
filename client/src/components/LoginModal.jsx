import axios from 'axios';
import styled from 'styled-components';
import google from '../assets/images/google.svg';

const LoginModal = ({ showModal, setShowModal }) => {
  const LoginGoogle = axios
    .post('/oauth2/authorization/google')
    .then((res) => res.json())
    .then((data) => console.log(data));

  const closeModal = () => {
    setShowModal(!showModal);
  };

  return (
    <>
      {showModal ? (
        <Background onClick={closeModal}>
          <LoginModalContainer showModal={showModal}>
            <CloseBtn onClick={closeModal}>
              <i class="fi fi-sr-cross-circle"></i>
            </CloseBtn>
            <p>환영합니다</p>
            <GoogleLoginBtn onClick={LoginGoogle}>
              <GoogleLogo />
              <p>Google 계정으로 로그인</p>
            </GoogleLoginBtn>
          </LoginModalContainer>
        </Background>
      ) : null}
    </>
  );
};
// Login Modal이 열렸을 때 뒷 배경
const Background = styled.div`
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.1);
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const LoginModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  width: 500px;
  height: 300px;
  border-radius: 4px;
  box-shadow: 0 0 10px 3px rgba(0, 0, 0, 0.1);
  position: relative;
  background: white;
  > p {
    font-size: 20px;
    font-weight: 600;
    color: ${({ theme }) => theme.colors.grey4};
  }
`;

const CloseBtn = styled.div`
  position: absolute;
  top: 15px;
  right: 15px;
  color: ${({ theme }) => theme.colors.grey2};
  cursor: pointer;
  :hover {
    color: ${({ theme }) => theme.colors.grey3};
  }
`;

const GoogleLogo = styled.img.attrs({
  src: `${google}`,
})`
  width: 18px;
  height: 18px;
  margin-right: 10px;
`;

const GoogleLoginBtn = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px 20px;
  width: 300px;
  border: 1px solid ${({ theme }) => theme.colors.grey2};
  border-radius: 4px;
  cursor: pointer;
  :hover {
    border: 1px solid ${({ theme }) => theme.colors.grey3};
  }
  > p {
    color: ${({ theme }) => theme.colors.black1};
    font-size: 14px;
  }
`;

export default LoginModal;
