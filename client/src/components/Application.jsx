import styled from 'styled-components';

const Application = ({ data }) => {
  // 포지션 별 지원 현황 정리
  // count : 포지션 별 TO , accepted: 확정된 인원 , pending: 보류 중 인원
  const positions = [
    {
      position: '프론트엔드',
      count: data.frontend,
      accepted: data.curFrontend,
      pending: data.applicationList.filter((e) => e.position === 'frontend' && e.accepted === false),
    },
    {
      position: '백엔드',
      count: data.backend,
      accepted: data.curBackend,
      pending: data.applicationList.filter((e) => e.position === 'backend' && e.accepted === false),
    },
    {
      position: '디자이너',
      count: data.designer,
      accepted: data.curDesigner,
      pending: data.applicationList.filter((e) => e.position === 'designer' && e.accepted === false),
    },
    {
      position: '기타',
      count: data.etc,
      accepted: data.curEtc,
      pending: data.applicationList.filter((e) => e.position === 'etc' && e.accepted === false),
    },
  ];

  return (
    <ApplicationContainer>
      <SubTitle>지원 현황</SubTitle>
      <PositionsContainer>
        {positions.map((e, i) =>
          e.count ? (
            <Position key={i}>
              <div className='position-name'>{e.position}</div>
              <div className='count'>{`${e.accepted} / ${e.count}`}</div>
              {e.count === e.accepted.length ? <button disabled>마감</button> : <button>지원</button>}
            </Position>
          ) : (
            ''
          )
        )}
      </PositionsContainer>
    </ApplicationContainer>
  );
};

const SubTitle = styled.h4`
  font-weight: 700;
  font-size: 22px;
  margin: 5px 0 20px 0;
`;

const ApplicationContainer = styled.div`
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

export default Application;
