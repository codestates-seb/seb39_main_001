import { useState, useEffect } from 'react';
import styled from 'styled-components';
import TechStack from '../components/TechStack';
import { joinSubmit } from '../apis/axios';
import axios from 'axios';

const Join = () => {
  const [stack, setStack] = useState([]);
  const [nickname, setNickname] = useState('aasdf');
  const [portfolio, setPortfolio] = useState('');
  const [introduction, setIntroduction] = useState('');
  const [nick, setNick] = useState(false);

  //유저 데이터
  const user = {
    nickname,
    portfolio,
    introduction,
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

  const getNickname = async (nickname) => {
    await axios
      .get(`http://juse.iptime.org:8080/users/nicknames?q=${nickname}`)
      .then((res) => {
        setNick(res.data.data);
        console.log(nick);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getNickname(nickname);
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
          <p>{nick ? '사용가능한 닉네임입니다.' : '중복된 닉네임이 존재합니다.'}</p>
          <p>이메일</p>
          <input type='text' value='milk@gmail.com'></input>
          <p>포트폴리오 링크 (깃헙, 노션, 블로그...)</p>
          <input type='text' onChange={portfolioHandler}></input>
          <p>기술 스택</p>
          <TechStack selected={stack} setSelected={setStack} />
          <p>한 줄 소개</p>
          <textarea type='text' onChange={introductionHandler}></textarea>
        </JoinInput>
        <StyledButton>회원 가입</StyledButton>
      </JoinContainer>
    </div>
  );
};

const JoinContainer = styled.div`
  max-width: 1300px;
  padding: 0 30px;
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

const StyledButton = styled.button`
  background-color: #fff;
  border: 1px solid ${({ theme }) => theme.colors.grey3};
  border-radius: 3px;
  padding: 5px 10px;
  cursor: pointer;
  :hover {
    border: 1px solid ${({ theme }) => theme.colors.grey4};
  }
`;

export default Join;
