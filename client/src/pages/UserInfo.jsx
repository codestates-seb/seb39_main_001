import { useState } from 'react';
import { useCookies } from 'react-cookie';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { apis } from '../apis/axios';
import { ReactComponent as Heart } from '../assets/icons/heart.svg';
import { ReactComponent as HeartFill } from '../assets/icons/heart-fill.svg';
import { useQuery, useMutation, useQueryClient } from 'react-query';

const UserInfo = () => {
  const [cookies, setCookies, removeCookie] = useCookies();
  const token = cookies.user;
  const myId = +cookies.userId;
  const param = useParams();
  const userId = param.userId;
  const queryClient = useQueryClient();

  const navigate = useNavigate();

  const [userData, setUserData] = useState({
    img: '',
    id: '',
    nickname: '',
    like: 0,
    myUserList: [],
    skillStackTags: [],
    introduction: '',
    email: '',
    portfolio: '',
    likedByMe: false,
  });

  const {
    img,
    nickname,
    liked,
    myUserList,
    skillStackTags,
    introduction,
    email,
    portfolio,
    likedByMe,
  } = userData;

  const location = useLocation().pathname;
  const isMe = location === '/users' ? true : false;

  // 나의 마이페이지인지, 남의 마이페이지인지 구분하여 api 호출
  const { data, isLoading, isError, error } = useQuery(
    'userInfo',
    isMe ? () => apis.getUsers(token) : () => apis.getOtherUsers(token, userId),
    {
      onSuccess: (data) => {
        setUserData(data);
      },
      onError: (error) => {
        console.log(error);
      },
    }
  );

  // 좋아요
  const postLikeMutation = useMutation(() => apis.postLike(token, userId), {
    onSuccess: (data, variable, context) => {
      queryClient.invalidateQueries('userInfo');
    },
    onError: (error) => {
      alert('자신에게 좋아요 할 수 없습니다.');
    },
  });

  // 좋아요 취소
  const deleteLikeMutation = useMutation(() => apis.deleteLike(token, userId), {
    onSuccess: (data, variable, context) => {
      queryClient.invalidateQueries('userInfo');
    },
    onError: (error) => {
      alert('문제가 발생하였습니다. 다시 한 번 시도해 주세요.');
    },
  });

  // 회원 탈퇴
  const deleteHandler = () => {
    if (window.confirm('정말 탈퇴하시겠습니까?')) {
      apis
        .deleteUser(token)
        .then(() => {
          removeCookie('user', { path: '/' });
          removeCookie('userId', { path: '/' });
        })
        .then(navigate('/'));
    } else {
      return;
    }
  };

  return (
    <UserInfoContainer>
      <BasicInfo>
        <UserImg width={'150px'}>
          <img src={img} alt={'프로필'}></img>
        </UserImg>
        <RoundButton>
          {likedByMe ? (
            <HeartFill
              fill='tomato'
              onClick={() => deleteLikeMutation.mutate()}
            />
          ) : (
            <Heart onClick={() => postLikeMutation.mutate()} />
          )}
        </RoundButton>
        <MiniBox>{nickname}</MiniBox>
        <MiniBox>
          <HeartFill fill='tomato' />
          {liked}
        </MiniBox>
      </BasicInfo>
      <MainInfo>
        {isMe ? (
          <div>
            <InfoLabel>내가 좋아하는 사용자</InfoLabel>
            {myUserList.length ? (
              <LikedUsers>
                {myUserList.map((e, i) => (
                  <Link key={i} to={`/users/${e.id}`}>
                    <User>
                      <UserImg width='40px'>
                        <img src={e.img} alt='img' />
                      </UserImg>
                      <span>{e.nickname}</span>
                    </User>
                  </Link>
                ))}
              </LikedUsers>
            ) : (
              <NullMessage>마음에 드는 사용자를 추가해보세요!</NullMessage>
            )}
          </div>
        ) : (
          ''
        )}
        <InfoLabel>기술 스택</InfoLabel>
        {skillStackTags.length ? (
          <StacksContainer>
            {skillStackTags.map((e, i) => (
              <Stack key={i} src={`/icons/stacks/${e}.png`} alt={`${e}`} />
            ))}
          </StacksContainer>
        ) : (
          <NullMessage>사용 가능한 기술 스택을 추가해보세요!</NullMessage>
        )}
        <InfoLabel>연락처</InfoLabel>
        <a href={'mailto:' + email}>{email}</a>
        <InfoLabel>포트폴리오 링크</InfoLabel>
        {portfolio ? (
          <a href={'http://' + portfolio}>{portfolio}</a>
        ) : (
          <NullMessage>
            GitHub, 노션, 블로그 등 나를 표현할 수 있는 링크를 추가해보세요!
          </NullMessage>
        )}
        <InfoLabel>한 줄 소개</InfoLabel>
        <p>{introduction}</p>
      </MainInfo>
      {isMe ? (
        <ButtonContainer>
          <Link to='/users/edit'>
            <StyledEditBtn>정보 수정</StyledEditBtn>
          </Link>
          <StyledUnsubscribeBtn onClick={deleteHandler}>
            회원 탈퇴
          </StyledUnsubscribeBtn>
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
  padding: 50px 30px;
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
  position: relative;
  width: ${({ width }) => width};
  height: ${({ width }) => width};
  border: 1px solid ${({ theme }) => theme.colors.grey3};
  border-radius: 50%;
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
    padding: 2px;
  }
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
  border-radius: 4px;
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
  align-items: center;
  gap: 15px;
  margin-top: 30px;
  button {
    padding: 10px;
  }
`;

const StyledEditBtn = styled.button`
  padding: 5px 10px;
  background: ${({ theme }) => theme.background};
  font-size: 14px;
  color: ${({ theme }) => theme.colors.purple1};
  border: 1px solid ${({ theme }) => theme.colors.purple1};
  border-radius: 4px;
  cursor: pointer;
  :hover {
    color: #fafafa;
    border: 1px solid ${({ theme }) => theme.colors.purple1};
    background: ${({ theme }) => theme.colors.purple1};
  }
`;

const StyledUnsubscribeBtn = styled.button`
  padding: 5px 10px;
  background: ${({ theme }) => theme.background};
  font-size: 14px;
  color: ${({ theme }) => theme.colors.grey3};
  border: 1px solid ${({ theme }) => theme.colors.grey3};
  border-radius: 4px;
  cursor: pointer;
  :hover {
    color: ${({ theme }) => theme.colors.grey4};
    background: ${({ theme }) => theme.background};
    border: 1px solid ${({ theme }) => theme.colors.grey4};
  }
`;

const NullMessage = styled.p`
  color: ${({ theme }) => theme.colors.grey3};
  font-size: 20px;
`;

export default UserInfo;
