import { useCookies } from 'react-cookie';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { ReactComponent as BookmarkIcon } from '../assets/icons/bookmark.svg';
import { ReactComponent as BookmarkCheckedIcon } from '../assets/icons/bookmark-check-fill.svg';
import { ReactComponent as Eye } from '../assets/icons/eye.svg';
import { apis } from '../apis/axios';
import { useMutation } from 'react-query';
import { useState } from 'react';

const Card = ({ data, refetch }) => {
  const [cookies] = useCookies();
  const token = cookies.user;
  const [bookmarked, setBookmarked] = useState(data.bookmarked);
  const [bookCount, setBookCount] = useState(data.bookmarks);

  // 프로젝트 기간 text 변환
  const periodNaming = (e) => {
    if (e === 'short') {
      return '1개월 미만';
    } else if (e === 'long') {
      return '장기';
    } else {
      return e + '개월';
    }
  };

  const postBookmarkMutation = useMutation(
    () => apis.postBookmark(token, data.id),
    {
      onSuccess: (data, variables, context) => {
        if (refetch) refetch();
        setBookmarked(true);
        setBookCount((prev) => prev + 1);
      },
    }
  );

  const deleteBookmarkMutation = useMutation(
    () => apis.deleteBookmark(token, data.id),
    {
      onSuccess: (data, variables, context) => {
        if (refetch) refetch();
        setBookmarked(false);
        setBookCount((prev) => prev - 1);
      },
    }
  );

  // 로그인 여부
  const isLogin = (e) => {
    if (!token) {
      e.preventDefault();
      alert('로그인이 필요한 기능입니다.');
    }
    return;
  };

  return (
    <CardContainer status={data.status} type={data.type}>
      {data.status === 'CLOSED' ? <Closed>모집 완료</Closed> : ''}
      <CardHeader>
        <CardType
          className={data.type === 'PROJECT' ? 'project-card' : 'study-card'}>
          {data.type}
        </CardType>
        <Bookmark>
          {bookmarked ? (
            <BookmarkCheckedIcon
              width={'24px'}
              height={'24px'}
              className='bookmark-checked-icon'
              onClick={() => deleteBookmarkMutation.mutate()}
            />
          ) : (
            <BookmarkIcon
              width={'24px'}
              height={'24px'}
              className='bookmark-icon'
              onClick={() => {
                token
                  ? postBookmarkMutation.mutate()
                  : alert('로그인이 필요한 기능입니다.');
              }}
            />
          )}
          {bookCount}
        </Bookmark>
      </CardHeader>
      <Link to={`/boards/${data.id}`}>
        <CardSummary>
          <div className='date'>{`${data.startingDate} (${periodNaming(
            data.period
          )})`}</div>
          <p className='title'>{data.title}</p>
          <div className='tags-container'>
            {/* 스택 리스트는 5개 초과 시 첫 5개만 잘라서 보여줌 */}
            {data.tagList.slice(0, 5).map((e, i) => (
              <img
                className='tag'
                key={i}
                src={`/icons/stacks/${e}.png`}
                alt={`${e}`}
              />
            ))}
          </div>
        </CardSummary>
      </Link>
      <CardInfo>
        <Link to={`/users/${data.user.id}`} onClick={isLogin}>
          <AuthorInfo>
            <ImgContainer>
              <img src={data.user.img} alt='프로필' />
            </ImgContainer>
            <div className='name'>{data.user.nickname}</div>
          </AuthorInfo>
        </Link>
        <Views>
          <Eye />
          {data.views}
        </Views>
      </CardInfo>
    </CardContainer>
  );
};

const CardContainer = styled.div`
  position: relative;
  border: 2px solid ${({ theme }) => theme.colors.grey1};
  border-radius: 8px;
  width: 370px;
  padding: 20px 30px;
  opacity: ${({ status }) => (status === 'CLOSED' ? 0.5 : 1)};
  :hover {
    transition: all 0.5s linear;
    border: 2px solid
      ${({ theme, type }) =>
        type === 'PROJECT' ? theme.colors.purple1 : theme.colors.tiffanyblue};
  }
`;

const CardHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const CardType = styled.div`
  background-color: ${({ theme }) => theme.colors.purple1};
  color: white;
  font-size: 13px;
  padding: 7px 10px;
  border-radius: 4px;
  &.project-card {
    background-color: ${({ theme }) => theme.colors.purple1};
    color: white;
    font-size: 13px;
    padding: 7px 10px;
    border-radius: 4px;
  }
  &.study-card {
    background-color: ${({ theme }) => theme.colors.tiffanyblue};
    color: white;
    font-size: 13px;
    padding: 7px 10px;
    border-radius: 4px;
  }
`;

const Bookmark = styled.div`
  display: flex;
  flex-direction: column;
  text-align: center;
  > .bookmark-icon {
    cursor: pointer;
    color: ${({ theme }) => theme.text};
  }
  > .bookmark-checked-icon {
    cursor: pointer;
  }
`;

const CardSummary = styled.div`
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
  padding: 10px 0;
  margin-bottom: 10px;
  min-height: 256px;
  .date {
    color: ${({ theme }) => theme.colors.grey4};
    padding-top: 5px;
  }
  .title {
    display: -webkit-box;
    font-size: 24px;
    font-weight: 700;
    line-height: 1.3em;
    overflow: hidden;
    -webkit-line-clamp: 3; /* 라인수 */
    -webkit-box-orient: vertical;
    margin: 10px 0;
    width: auto;
    height: 3.9em;
  }
  .tags-container {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin: 30px 0;
    > .tag {
      border: 1px solid ${({ theme }) => theme.colors.grey3};
      border-radius: 50%;
      width: 50px;
      height: 50px;
      padding: 5px;
    }
  }
`;

const CardInfo = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const AuthorInfo = styled.div`
  display: flex;
  align-items: center;
  padding: 15px 0;
  > .name {
    font-size: 18px;
    padding-left: 10px;
  }
`;

const ImgContainer = styled.div`
  position: relative;
  border: 1px solid ${({ theme }) => theme.colors.grey3};
  border-radius: 50%;
  width: 35px;
  height: 35px;
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

const Views = styled.div`
  display: flex;
  align-items: center;
  gap: 4px;
  color: ${({ theme }) => theme.colors.grey4};
  > i {
    transform: translateY(1px);
  }
`;

const Closed = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 16px;
  background-color: #000;
  color: #fff;
`;

export default Card;
