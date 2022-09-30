import { useEffect, useState } from 'react';
import styled from 'styled-components';
import TechStack from '../components/TechStack';
import { apis } from '../apis/axios';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import { useRef } from 'react';

const EditUser = () => {
  const [imageSrc, setImageSrc] = useState('/icons/img/user-default.png');
  const [imageFile, setImageFile] = useState(null);
  const [stack, setStack] = useState([]);
  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const [portfolio, setPortfolio] = useState('');
  const [introduction, setIntroduction] = useState('');
  const [cookies] = useCookies();
  const token = cookies.user;
  const navigate = useNavigate();

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

  // 프로필 이미지 인풋
  const fileInput = useRef(null);
  const imageButtonHandler = () => {
    fileInput.current.click();
  };
  const imageChangeHandler = (e) => {
    if (e.target.files[0]) {
      setImageFile(e.target.files[0]);
    }
    //바뀐 이미지 렌더
    const reader = new FileReader();
    reader.onload = () => {
      if (reader.readyState === 2) {
        setImageSrc(reader.result);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };
  const imageDeleteHandler = () => {
    setImageFile(null);
    setImageSrc('/icons/img/user-default.png');
  };

  // 유저 정보 불러오기
  useEffect(() => {
    apis.getUsers(token).then((data) => {
      setImageSrc(data.img);
      setImageFile(data.img);
      setNickname(data.nickname);
      setEmail(data.email);
      setPortfolio(data.portfolio);
      setStack(data.skillStackTags);
      setIntroduction(data.introduction);
    });
  }, [token]);

  // 수정 submit
  const submitHandler = () => {
    const user = {
      nickname,
      portfolio,
      skillStackTags: stack,
      introduction,
      img: imageFile ? null : '/icons/img/user-default.png',
    };
    apis
      .patchUser(token, user, imageFile)
      .then(alert('정보 수정이 완료되었습니다.'))
      .then(navigate('/users'));
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
            <ImageContainer>
              <img
                src={
                  imageSrc === 'default.jpg'
                    ? '/icons/img/user-default.png'
                    : imageSrc
                }
                alt='프로필'
              />
            </ImageContainer>
            <input
              type='file'
              style={{ display: 'none' }}
              accept='image/*'
              onChange={imageChangeHandler}
              ref={fileInput}
            />
            <StyledButton onClick={imageButtonHandler}>업로드</StyledButton>
            <StyledButton onClick={imageDeleteHandler}>
              업로드 삭제
            </StyledButton>
          </div>
        </ProfileUpload>
        <JoinInput>
          <p>닉네임</p>
          <input
            type='text'
            value={nickname}
            onChange={nicknameHandler}></input>
          <p>이메일</p>
          <input type='text' value={email} disabled></input>
          <p>포트폴리오 링크 (깃헙, 노션, 블로그...)</p>
          <input
            type='text'
            value={portfolio}
            onChange={portfolioHandler}></input>
          <p>기술 스택</p>
          <TechStack selected={stack} setSelected={setStack} />
          <p>한 줄 소개</p>
          <textarea
            type='text'
            value={introduction}
            onChange={introductionHandler}></textarea>
        </JoinInput>
        <StyledButton onClick={submitHandler}>정보 수정</StyledButton>
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
    > button {
      margin-left: 10px;
    }
  }
`;

const ImageContainer = styled.div`
  position: relative;
  width: 150px;
  height: 150px;
  border: 1px solid ${({ theme }) => theme.colors.grey3};
  border-radius: 999px;
  > img {
    position: absolute;
    top: 0;
    left: 0;
    transform: translate(50, 50);
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 999px;
    margin: auto;
    padding: 5px;
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

export default EditUser;
