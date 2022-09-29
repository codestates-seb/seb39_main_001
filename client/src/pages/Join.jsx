import { useState, useEffect } from 'react';
import styled from 'styled-components';
import TechStack from '../components/TechStack';
import { apis } from '../apis/axios';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { useLocation, useNavigate } from 'react-router-dom';

const Join = () => {
  const [stack, setStack] = useState([]);
  const [nickname, setNickname] = useState('aasdf');
  const [portfolio, setPortfolio] = useState('');
  const [introduction, setIntroduction] = useState('');
  const [isValid, setIsValid] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();

  // OAuth 페이지에서 넘겨준 토큰
  const token = location.state.token;

  //유저 데이터
  const user = {
    nickname,
    portfolio,
    introduction,
    skillStackTags: stack,
  };

  //인풋 핸들러들
  const nicknameHandler = (e) => {
    setNickname(e.target.value);
  };
  const portfolioHandler = (e) => {
    setPortfolio(e.target.value);
  };
  const introductionHandler = (e) => {
    setIntroduction(e.target.value);
  };

  // 회원가입 submit
  const postJoinHandler = () => {
    if (!user.nickname) {
      alert('닉네임을 입력하세요.');
    } else if (!user.introduction) {
      alert('한 줄 소개를 입력하세요.');
    } else {
      apis
        .postJoin(token, user)
        .then(alert('회원가입에 성공하였습니다. 다시 로그인해 주세요.'))
        .then(() => {
          navigate('/');
        });
    }
  };

  // 중복확인
  useEffect(() => {
    apis.getNickname(nickname).then((data) => {
      console.log(data);
      setIsValid(data);
    });
  }, [nickname]);

  return (
    <div>
      <JoinContainer>
        <JoinHeader>
          <h2>회원가입</h2>
        </JoinHeader>
        <ProfileUpload>
          <p>프로필 사진</p>
          <div className='uploader'>
            <div className='image-container'>사진</div>
            <StyledButton>업로드</StyledButton>
          </div>
        </ProfileUpload>
        <JoinInput>
          <p>닉네임</p>
          <input type='text' onChange={nicknameHandler}></input>
          <Validation className={isValid ? 'valid' : 'not-valid'}>
            {isValid
              ? '사용가능한 닉네임입니다.'
              : '중복된 닉네임이 존재합니다.'}
          </Validation>
          <p>포트폴리오 링크 (깃헙, 노션, 블로그...)</p>
          <input type='text' onChange={portfolioHandler}></input>
          <p>기술 스택</p>
          <TechStack selected={stack} setSelected={setStack} />
          <p>한 줄 소개</p>
          <textarea type='text' onChange={introductionHandler}></textarea>
        </JoinInput>
        <StyledButton onClick={postJoinHandler}>회원 가입</StyledButton>
      </JoinContainer>
    </div>
  );
};

const JoinContainer = styled.div`
  max-width: 1300px;
  padding: 0 30px;
  padding-bottom: 50px;
  margin: auto;
`;

const JoinHeader = styled.header`
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
  padding: 50px 0;
  margin-bottom: 20px;
  > h2 {
    font-weight: 700;
    font-size: 26px;
  }
`;

const ProfileUpload = styled.div`
  > .uploader {
    display: flex;
    align-items: end;
    margin: 10px 0;
    > .image-container {
      padding: 50px;
      border: 1px solid ${({ theme }) => theme.colors.grey2};
    }
    > button {
      margin-left: 10px;
    }
  }
`;

const JoinInput = styled.div`
  input,
  textarea {
    border: 1px solid ${({ theme }) => theme.colors.grey3};
    margin: 10px 0;
    padding: 5px;
  }
  textarea {
    min-width: 400px;
    min-height: 100px;
    resize: none;
  }
`;

const Validation = styled.p`
  font-size: 13px;
  margin-bottom: 15px;
  &.valid {
    color: blue;
  }
  &.not-valid {
    color: red;
  }
`;

const StyledButton = styled.button`
  padding: 5px 10px;
  background: #ffffff;
  font-size: 14px;
  color: ${({ theme }) => theme.colors.black1};
  border: 1px solid ${({ theme }) => theme.colors.grey3};
  border-radius: 4px;
  cursor: pointer;
  :hover {
    color: #ffffff;
    border: 1px solid ${({ theme }) => theme.colors.purple1};
    background: ${({ theme }) => theme.colors.purple1};
  }
`;

export default Join;
