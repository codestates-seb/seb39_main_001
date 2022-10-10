import { useEffect, useState } from 'react';
// import styled from 'styled-components';
import TechStack from '../components/TechStack';
import { apis } from '../apis/axios';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import { useRef } from 'react';
import {
  JoinContainer,
  JoinHeader,
  ProfileUpload,
  Label,
  ImageContainer,
  JoinInput,
  StyledButton,
  Validation,
} from './Join';

const EditUser = () => {
  const [imageSrc, setImageSrc] = useState('/icons/img/user-default.png');
  const [imageFile, setImageFile] = useState(null);
  const [stack, setStack] = useState([]);
  const [nickname, setNickname] = useState('');
  const [origin, setOrigin] = useState('');
  const [isValid, setIsValid] = useState(true);
  const [isValid2, setIsValid2] = useState(false);
  const [validMsg, setValidMsg] = useState('닉네임을 입력해주세요.');
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
  // const imageChangeHandler = (e) => {
  // 	if (e.target.files[0]) {
  // 		setImageFile(e.target.files[0]);
  // 	}
  // 이미지 용량 체크
  const imageChangeHandler = (e) => {
    if (e.target.files[0].size <= 1024 * 1024) {
      setImageFile(e.target.files[0]);
    } else {
      alert('1MB 이하의 이미지만 업로드가 가능합니다.');
      throw Error('1MB 이하의 이미지만 업로드가 가능합니다.');
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

  // 닉네임 유효성 검사
  const validationMessage = (nickname) => {
    const regex = /^[가-힣|a-z|A-Z|0-9|]+$/;
    if (nickname.length === 0) {
      setIsValid2(false);
      setValidMsg('닉네임을 입력해주세요.');
    } else if (nickname.length < 2) {
      setIsValid2(false);
      setValidMsg('닉네임은 2자 이상 입력해주세요.');
    } else if (nickname.length > 10) {
      setIsValid2(false);
      setValidMsg('닉네임은 10자 이내로 입력해주세요.');
    } else if (!regex.test(nickname)) {
      setIsValid2(false);
      setValidMsg('닉네임은 한글,영어,숫자만 가능합니다.');
    } else {
      setIsValid2(true);
      setValidMsg('사용가능한 닉네임입니다.');
    }
    return;
  };

  // 중복확인
  useEffect(() => {
    apis.getNickname(nickname).then((data) => {
      nickname === origin ? setIsValid(true) : setIsValid(data);
    });
    validationMessage(nickname);
  }, [nickname, origin]);

  // 유저 정보 불러오기
  useEffect(() => {
    apis.getUsers(token).then((data) => {
      setImageSrc(data.img);
      setImageFile(data.img);
      setNickname(data.nickname);
      setOrigin(data.nickname);
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
    if (!isValid || !isValid2) {
      alert('닉네임을 확인해주세요.');
    } else if (!user.introduction) {
      alert('한 줄 소개를 입력하세요.');
    } else {
      apis
        .patchUser(token, user, imageFile)
        .then(alert('정보 수정이 완료되었습니다.'))
        .then(navigate('/users'));
    }
  };

  return (
    <div>
      <JoinContainer>
        <JoinHeader>
          <h2>회원 정보 수정</h2>
        </JoinHeader>
        <ProfileUpload>
          <Label>프로필 사진</Label>
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
              // accept='image/*'
              accept='image/png, image/jpeg, image/jpg'
              onChange={imageChangeHandler}
              ref={fileInput}
            />
            <StyledButton className='upload-btn' onClick={imageButtonHandler}>
              업로드
            </StyledButton>
            <StyledButton
              className='upload-cancel-btn'
              onClick={imageDeleteHandler}>
              업로드 삭제
            </StyledButton>
          </div>
        </ProfileUpload>
        <JoinInput>
          <Label>닉네임</Label>
          <input
            type='text'
            value={nickname}
            onChange={nicknameHandler}></input>
          <Validation className={isValid && isValid2 ? 'valid' : 'not-valid'}>
            {isValid ? validMsg : '중복된 닉네임이 존재합니다.'}
          </Validation>
          <Label>
            이메일
            <span className='email-edit-msg'>
              (이메일은 수정할 수 없습니다.)
            </span>
          </Label>
          <input
            type='text'
            value={email}
            className='disabled-input'
            disabled></input>
          <Label>포트폴리오 링크</Label>
          <input
            type='text'
            value={portfolio}
            className='portfolio-input'
            placeholder='GitHub, Notion, Blog...'
            onChange={portfolioHandler}></input>
          <Label className='tech-stack'>기술 스택</Label>
          <TechStack selected={stack} setSelected={setStack} />
          <Label className='introduction-label'>한 줄 소개</Label>
          <textarea
            type='text'
            value={introduction}
            onChange={introductionHandler}></textarea>
        </JoinInput>
        <StyledButton className='submit-btn' onClick={submitHandler}>
          정보 수정
        </StyledButton>
      </JoinContainer>
    </div>
  );
};

export default EditUser;
