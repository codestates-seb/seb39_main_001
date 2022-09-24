import { useState } from 'react';
import styled from 'styled-components';
import TechStack from '../components/TechStack';
import { joinSubmit } from '../apis/axios';

const EditUser = () => {
  const [stack, setStack] = useState([]);
  const [nickname, setNickname] = useState('');
  const [portfolio, setPortfolio] = useState('');
  const [introduction, setIntroduction] = useState('');

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

  return (
    <div>
      <JoinContainer>
        <JoinHeader>
          <h2>회원 정보 수정</h2>
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
          <p>이메일</p>
          <input type='text' value='milk@gmail.com'></input>
          <p>포트폴리오 링크 (깃헙, 노션, 블로그...)</p>
          <input type='text' onChange={portfolioHandler}></input>
          <p>기술 스택</p>
          <TechStack selected={stack} setSelected={setStack} />
          <p>한 줄 소개</p>
          <textarea type='text' onChange={introductionHandler}></textarea>
        </JoinInput>
        <StyledButton>정보 수정</StyledButton>
      </JoinContainer>
    </div>
  );
};

const JoinContainer = styled.div`
  max-width: 1300px;
  margin: auto;
  padding: 0 30px;
  padding-bottom: 30px;
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

export default EditUser;
