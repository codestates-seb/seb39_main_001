import styled from 'styled-components';
import { board1 } from '../mocks/db';
import { Link, useLocation } from 'react-router-dom';
import QuestionAnswer from '../components/QuestionAnswer';
import Application from '../components/Application';
import { ReactComponent as Eye } from '../assets/icons/eye.svg';
import { ReactComponent as BookmarkIcon } from '../assets/icons/bookmark.svg';
import { ReactComponent as BookmarkCheckedIcon } from '../assets/icons/bookmark-check-fill.svg';
import { useState, useEffect } from 'react';
import { apis } from '../apis/axios';
import { useCookies } from 'react-cookie';

const Board = () => {
  const [cookies] = useCookies();
  const token = cookies.user;
  const [data, setData] = useState(board1.data);
  // const [bookmark, setBookmark] = useState(false);

  const boardId = useLocation().pathname.slice(-1);

  useEffect(() => {
    apis.getBoardDetail(token, boardId).then((data) => {
      if (data) {
        setData(data);
      } else return;
    });
  }, []);

  const addBookmarkHandler = () => {
    // 로그인 여부 확인
    if (!token) {
      alert('로그인이 필요한 기능입니다.');
    } else {
      // setBookmark(!bookmark);
      console.log('북마크 추가됨');
      // 북마크 post 요청
      apis.postBookmark(token, data.id).then((res) => console.log(res));
    }
  };

  const deleteBookmarkHandler = () => {
    // setBookmark(!bookmark);
    console.log('북마크 삭제됨');
    // 북마크 delete 요청
    apis.deleteBookmark(token, data.id).then((res) => console.log(res));
  };

  return (
    <BoardContainer>
      <HeaderInfo>
        <StatusType>
          <div className='type'>{data.type}</div>
          <div className='status'>
            {data.status === 'OPENING' ? '모집 중' : '모집 완료'}
          </div>
        </StatusType>
        <FlexContainer>
          <EditDelete>
            <Link to='/boards/edit' state={{ boardId }}>
              수정
            </Link>
            <Link to=''>삭제</Link>
          </EditDelete>
          <ViewBookmark>
            <Eye />
            {data.views}
            {/* <BookmarkIcon /> */}
            {data.bookmarked ? (
              <BookmarkCheckedIcon
                width={'24px'}
                height={'24px'}
                className='bookmark-checked-icon'
                onClick={deleteBookmarkHandler}
              />
            ) : (
              <BookmarkIcon
                width={'24px'}
                height={'24px'}
                className='bookmark-icon'
                onClick={addBookmarkHandler}
              />
            )}
            {data.bookmarks}
          </ViewBookmark>
        </FlexContainer>
      </HeaderInfo>
      <Title>{data.title}</Title>
      <LeaderInfo>
        <SubTitle>팀장 정보</SubTitle>
        <FlexContainer>
          <div className='img'>프사</div>
          <div className='name'>{data.user.nickname}</div>
          {data.user.skillStackTags.map((e, i) => (
            <Stack key={i} src={`/icons/stacks/${e}.png`} alt={`${e}`} />
          ))}
        </FlexContainer>
      </LeaderInfo>
      <Application data={data} />
      <TopTemplate>
        <LeftInfo>
          <FlexContainer>
            <Category>모집 마감일</Category>
            {data.dueDate}
          </FlexContainer>
          <FlexContainer>
            <Category>연락 방법</Category>
            {data.contact}
          </FlexContainer>
          <FlexContainer>
            <Category>진행 방식</Category>
            {data.onOffline === 'online' ? '온라인' : '오프라인'}
          </FlexContainer>
        </LeftInfo>
        <RightInfo>
          <FlexContainer>
            <Category>예상 시작일</Category>
            {data.startingDate}
          </FlexContainer>
          <Category>기술 스택</Category>
          <TagsContainer>
            {data.tagList.map((e, i) => (
              <Stack key={i} src={`/icons/stacks/${e}.png`} alt={`${e}`} />
            ))}
          </TagsContainer>
        </RightInfo>
      </TopTemplate>
      <Main>{data.content}</Main>
      <QuestionAnswer data={data} />
    </BoardContainer>
  );
};

const BoardContainer = styled.div`
  max-width: 1300px;
  padding: 30px;
  margin: auto;
`;

const HeaderInfo = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
`;

const StatusType = styled.div`
  display: flex;
  gap: 15px;
  > div {
    background-color: ${({ theme }) => theme.colors.grey5};
    color: #fff;
    padding: 10px;
  }
`;

const FlexContainer = styled.div`
  display: flex;
  align-items: center;
`;

const EditDelete = styled.div`
  display: flex;
  gap: 10px;
  align-items: center;
  color: ${({ theme }) => theme.colors.grey4};
  margin: 0 30px;
`;

const ViewBookmark = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 24px;
  > .bookmark-icon {
    cursor: pointer;
  }
  > .bookmark-checked-icon {
    cursor: pointer;
  }
`;

const Title = styled.h2`
  font-size: 36px;
  font-weight: 700;
  line-height: 1.3em;
  padding-bottom: 20px;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
`;

const LeaderInfo = styled.div`
  padding: 15px 0;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
  .img {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.colors.grey3};
  }
  .name {
    width: 150px;
    margin-left: 15px;
    font-weight: 700;
  }
`;

const Stack = styled.img`
  border: 1px solid ${({ theme }) => theme.colors.grey2};
  border-radius: 50%;
  width: 50px;
  height: 50px;
  padding: 5px;
  margin-right: 20px;
`;

const SubTitle = styled.h4`
  font-weight: 700;
  font-size: 22px;
  margin: 5px 0 20px 0;
`;

const TopTemplate = styled.div`
  display: flex;
  padding: 15px 0;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
`;

const LeftInfo = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

const RightInfo = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

const Category = styled.div`
  color: ${({ theme }) => theme.colors.grey4};
  font-size: 18px;
  width: 150px;
`;

const TagsContainer = styled.div`
  display: flex;
  align-items: center;
  margin-top: -10px;
  > img {
    width: 40px;
    height: 40px;
    padding: 3px;
  }
`;

const Main = styled.div`
  padding: 15px 0;
  min-height: 200px;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
`;

export default Board;
