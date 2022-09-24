import styled from 'styled-components';
import Card from '../components/Card';
import { myjuse } from '../mocks/db';

const MyJuse = () => {
  const { myBookmarkList, myParticipationList, myApplicationList, myBoards } = myjuse.data;

  return (
    <MyJuseContainer>
      <JuseLabel>나의 북마크</JuseLabel>
      <CardContainer>
        {myBookmarkList ? (
          myBookmarkList.map((e, i) => <Card data={e} key={i} />)
        ) : (
          <NullMessage>마음에 드는 모임을 북마크해 보세요!</NullMessage>
        )}
      </CardContainer>
      <JuseLabel>참여중인 모임</JuseLabel>
      <CardContainer>
        {myParticipationList ? (
          myParticipationList.map((e, i) => <Card data={e} key={i} />)
        ) : (
          <NullMessage>지원을 통해 모임에 참여해보세요!</NullMessage>
        )}
      </CardContainer>
      <JuseLabel>작성한 글</JuseLabel>
      <CardContainer>
        {myBoards ? (
          myBoards.map((e, i) => <Card data={e} key={i} />)
        ) : (
          <NullMessage>직접 팀장이 되어 사람들을 모아 보세요!</NullMessage>
        )}
      </CardContainer>
      <JuseLabel>지원한 모임</JuseLabel>
      <CardContainer>
        {myApplicationList ? (
          myApplicationList.map((e, i) => <Card data={e} key={i} />)
        ) : (
          <NullMessage>마음에 드는 모임에 지원해 보세요!</NullMessage>
        )}
      </CardContainer>
    </MyJuseContainer>
  );
};

const MyJuseContainer = styled.div`
  max-width: 1300px;
  padding: 0 30px;
  padding-bottom: 50px;
  margin: auto;
`;

const JuseLabel = styled.div`
  font-size: 20px;
  font-weight: 700;
  color: ${({ theme }) => theme.colors.grey5};
  padding: 40px 0 30px 0;
`;

const CardContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 30px;
`;

const NullMessage = styled.p`
  color: ${({ theme }) => theme.colors.grey4};
`;

export default MyJuse;
