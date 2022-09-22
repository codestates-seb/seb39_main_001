import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { user } from '../mocks/db';

const UserInfo = () => {
  const { nickname, liked, myUserList, skillStackTags, introduction, email, portfolio, isAuth = false } = user.data;

  return (
    <UserInfoContainer>
      <BasicInfo>
        <UserImg></UserImg>
        <RoundButton>
          <i className='fi fi-rr-heart'></i>
        </RoundButton>
        <MiniBox>{nickname}</MiniBox>
        <MiniBox>
          <i className='fi fi-sr-heart'></i>
          {liked}
        </MiniBox>
      </BasicInfo>
      <MainInfo>
        {isAuth ? (
          <div>
            <InfoLabel>내가 좋아하는 사용자</InfoLabel>
            <LikedUsers>
              {myUserList.map((e, i) => (
                <Link to={`/users/${e.id}`}>
                  <User key={i}>
                    <div className='img'></div>
                    <span>{e.nickname}</span>
                  </User>
                </Link>
              ))}
            </LikedUsers>
          </div>
        ) : (
          ''
        )}
        <InfoLabel>기술 스택</InfoLabel>
        <StacksContainer>
          {skillStackTags.map((e, i) => (
            <Stack key={i} src={`/icons/stacks/${e}.png`} alt={`${e}`} />
          ))}
        </StacksContainer>
        <InfoLabel>연락처</InfoLabel>
        <a href={'mailto:' + email}>{email}</a>
        <InfoLabel>포트폴리오</InfoLabel>
        <a href={'http://' + portfolio}>{portfolio}</a>
        <InfoLabel>한 줄 소개</InfoLabel>
        <p>{introduction}</p>
      </MainInfo>
      {isAuth ? (
        <ButtonContainer>
          <button>정보 수정</button>
          <button>회원 탈퇴</button>
        </ButtonContainer>
      ) : (
        ''
      )}
    </UserInfoContainer>
  );
};

const UserInfoContainer = styled.div`
  max-width: 1300px;
  margin: auto;
  padding: 50px 0;
`;

const BasicInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 150px;
  margin: auto;
  margin-bottom: 20px;
`;

const UserImg = styled.div`
  width: 150px;
  height: 150px;
  background-color: ${({ theme }) => theme.colors.grey3};
  border-radius: 50%;
`;

const RoundButton = styled.button`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: 1px solid ${({ theme }) => theme.colors.grey3};
  background-color: #fff;
  margin-left: auto;
  font-size: 20px;
  padding-top: 5px;
  transform: translateY(-100%);
  cursor: pointer;
  :hover {
    border: 1px solid ${({ theme }) => theme.colors.grey4};
  }
`;

const MiniBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 5px;
  padding: 10px;
  margin-bottom: 10px;
  width: 100%;
  border: 1px solid ${({ theme }) => theme.colors.grey2};
  > i {
    color: #ff0000;
    transform: translateY(1px);
  }
`;

const MainInfo = styled.div`
  padding-bottom: 30px;
  > a {
    text-decoration: underline;
  }
`;

const InfoLabel = styled.div`
  font-size: 20px;
  font-weight: 700;
  color: ${({ theme }) => theme.colors.grey5};
  padding: 40px 0 30px 0;
`;

const LikedUsers = styled.div`
  display: flex;
  gap: 20px;
`;

const User = styled.div`
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 5px;
  > .img {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.colors.grey2};
  }
`;

const StacksContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
`;

const Stack = styled.img`
  border: 1px solid ${({ theme }) => theme.colors.grey2};
  border-radius: 50%;
  width: 60px;
  height: 60px;
  padding: 5px;
  margin-right: 20px;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 15px;
  margin-top: 30px;
  > button {
    padding: 10px;
  }
`;

export default UserInfo;
