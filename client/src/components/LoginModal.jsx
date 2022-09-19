import axios from 'axios';
import styled from 'styled-components';

const Background = styled.div`
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
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
  height: 600px;
  border: 1px solid black;
  position: relative;
  background: white;
`;

const LoginModal = ({ showModal, setShowModal }) => {
  const LoginGoogle = () => {
    axios
      .post(`https://cors-jwy.herokuapp.com//oauth2/authorization/google`)
      .then((res) => res.json())
      .then((data) => console.log(data));
  };

  return (
    <>
      {showModal ? (
        <Background>
          <LoginModalContainer showModal={showModal}>
            <div>로그인 하세용</div>
            <button onClick={LoginGoogle}>Google 로그인</button>
            <button onClick={() => setShowModal(!showModal)}>닫기</button>
          </LoginModalContainer>
        </Background>
      ) : null}
    </>
  );
};

export default LoginModal;
