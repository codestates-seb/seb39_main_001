import styled from 'styled-components';
import { board1 } from '../mocks/db';
import { Link } from 'react-router-dom';

const Board = () => {
  const data = board1.data;

  // 포지션 별 지원 현황 정리
  // count : 포지션 별 TO , accepted: 확정된 인원 , pending: 보류 중 인원
  const positions = [
    {
      position: '프론트엔드',
      count: data.frontend,
      accepted: data.applicationList.filter((e) => e.position === 'frontend' && e.accepted === true),
      pending: data.applicationList.filter((e) => e.position === 'frontend' && e.accepted === false),
    },
    {
      position: '백엔드',
      count: data.backend,
      accepted: data.applicationList.filter((e) => e.position === 'backend' && e.accepted === true),
      pending: data.applicationList.filter((e) => e.position === 'backend' && e.accepted === false),
    },
    {
      position: '디자이너',
      count: data.designer,
      accepted: data.applicationList.filter((e) => e.position === 'designer' && e.accepted === true),
      pending: data.applicationList.filter((e) => e.position === 'designer' && e.accepted === false),
    },
    {
      position: '기타',
      count: data.etc,
      accepted: data.applicationList.filter((e) => e.position === 'etc' && e.accepted === true),
      pending: data.applicationList.filter((e) => e.position === 'etc' && e.accepted === false),
    },
  ];

  return (
    <BoardContainer>
      <HeaderInfo>
        <StatusType>
          <div className='type'>{data.type}</div>
          <div className='status'>{data.status === 'OPENING' ? '모집 중' : '모집 완료'}</div>
        </StatusType>
        <FlexContainer>
          <EditDelete>
            <Link to=''>수정</Link>
            <Link to=''>삭제</Link>
          </EditDelete>
          <ViewBookmark>
            <i className='fi fi-rr-eye'></i>
            {data.views}
            <i className='fi fi-rr-bookmark'></i>
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
          {data.user.stacks.map((e, i) => (
            <Stack key={i} src={`/icons/stacks/${e}.png`} alt={`${e}`} />
          ))}
        </FlexContainer>
      </LeaderInfo>
      <Application>
        <SubTitle>지원 현황</SubTitle>
        <PositionsContainer>
          {positions.map((e, i) =>
            e.count ? (
              <Position key={i}>
                <div className='position-name'>{e.position}</div>
                <div className='count'>{`${e.accepted.length} / ${e.count}`}</div>
                {e.count === e.accepted.length ? <button disabled>마감</button> : <button>지원</button>}
              </Position>
            ) : (
              ''
            )
          )}
        </PositionsContainer>
      </Application>
      <TopTemplate>
        <LeftInfo>
          <FlexContainer>
            <Category>연락 방법</Category>
            {data.contact}
          </FlexContainer>
          <FlexContainer>
            <Category>모집 마감일</Category>
            {data.dueDate}
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
      <QuestionContainer>
        <SubTitle>문의 사항</SubTitle>
      </QuestionContainer>
    </BoardContainer>
  );
};

const BoardContainer = styled.div`
  max-width: 1300px;
  padding: 20px 0;
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
  font-size: 18px;
  margin: 5px 0 15px 0;
`;

const Application = styled.div`
  padding: 15px 0;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
`;

const PositionsContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

const Position = styled.div`
  display: flex;
  font-size: 18px;
  > .position-name {
    width: 150px;
  }
  > .count {
    width: 60px;
  }
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

const QuestionContainer = styled.div`
  padding: 15px 0;
`;

export default Board;
