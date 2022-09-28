import { useCookies } from 'react-cookie';
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { apis } from '../apis/axios';
import { ReactComponent as Accept } from '../assets/icons/check-circle.svg';
import { ReactComponent as Deny } from '../assets/icons/x-circle.svg';
import theme from '../assets/styles/Theme';

const Application = ({ data }) => {
  const [cookies] = useCookies();
  const token = cookies.user;
  const navigate = useNavigate();

  // 포지션 별 지원 현황 정리
  // count : 포지션 별 TO , accepted: 확정된 인원 , pending: 보류 중 인원
  const positions = [
    {
      position: '프론트엔드',
      value: 'frontend',
      count: data.frontend,
      accepted: data.applicationList.filter(
        (e) => e.position === 'frontend' && e.accepted === true
      ),
      pending: data.applicationList.filter(
        (e) => e.position === 'frontend' && e.accepted === false
      ),
    },
    {
      position: '백엔드',
      value: 'backend',
      count: data.backend,
      accepted: data.applicationList.filter(
        (e) => e.position === 'backend' && e.accepted === true
      ),
      pending: data.applicationList.filter(
        (e) => e.position === 'backend' && e.accepted === false
      ),
    },
    {
      position: '디자이너',
      value: 'designer',
      count: data.designer,
      accepted: data.applicationList.filter(
        (e) => e.position === 'designer' && e.accepted === true
      ),
      pending: data.applicationList.filter(
        (e) => e.position === 'designer' && e.accepted === false
      ),
    },
    {
      position: '기타',
      value: 'etc',
      count: data.etc,
      accepted: data.applicationList.filter(
        (e) => e.position === 'etc' && e.accepted === true
      ),
      pending: data.applicationList.filter(
        (e) => e.position === 'etc' && e.accepted === false
      ),
    },
    {
      position: '스터디원',
      value: 'people',
      count: data.people,
      accepted: data.applicationList.filter(
        (e) => e.position === 'people' && e.accepted === true
      ),
      pending: data.applicationList.filter(
        (e) => e.position === 'people' && e.accepted === false
      ),
    },
  ];

  // 지원하기
  const clickApply = (el) => {
    apis
      .postApply(token, data.id, el.value)
      .then(alert(`${el.position}에 지원을 완료했습니다.`));
  };

  // 지원 관리
  const acceptHandler = (applicationId) => {
    apis.patchAccept(token, applicationId);
  };
  const denyHandler = (applicationId) => {
    apis.deleteDeny(token, applicationId);
  };

  return (
    <ApplicationContainer>
      <StatusContainer>
        <SubTitle>지원 현황</SubTitle>
        <PositionsContainer>
          {positions.map((el, i) =>
            el.count ? (
              <div>
                <Position key={i}>
                  <div className='position-name'>{el.position}</div>
                  <div className='count'>{`${el.accepted.length} / ${el.count}`}</div>
                  {!data.auth ? (
                    el.count === el.accepted.length ? (
                      <ApplyButton className='closed'>마감</ApplyButton>
                    ) : (
                      <ApplyButton
                        onClick={() => {
                          clickApply(el);
                        }}>
                        지원
                      </ApplyButton>
                    )
                  ) : (
                    ''
                  )}
                </Position>
                {data.auth ? (
                  <PendingContainer>
                    <span>지원 목록</span>
                    {el.pending.length ? (
                      el.pending.map((apply) => (
                        <PendingBubble isAccepted={false}>
                          <Link to={`/users/${apply.userId}`}>
                            {apply.nickname}
                          </Link>
                          <Accept
                            fill={theme.colors.purple1}
                            onClick={() => {
                              acceptHandler(apply.id);
                            }}
                          />
                          <Deny
                            fill={theme.colors.grey4}
                            onClick={() => {
                              denyHandler(apply.id);
                            }}
                          />
                        </PendingBubble>
                      ))
                    ) : (
                      <p className='null-message'>
                        아직 지원자가 없습니다... ;_;
                      </p>
                    )}
                  </PendingContainer>
                ) : (
                  ''
                )}
              </div>
            ) : (
              ''
            )
          )}
        </PositionsContainer>
      </StatusContainer>
      {data.auth ? (
        <StatusContainer>
          <SubTitle>팀원 현황</SubTitle>
          {positions.map((el, i) =>
            el.count ? (
              <AcceptedContainer key={i}>
                {el.accepted.length ? (
                  el.accepted.map((apply) => (
                    <PendingBubble isAccepted={true}>
                      <Link to={`/users/${apply.userId}`}>
                        {apply.nickname}
                      </Link>
                      <Deny
                        fill={theme.colors.grey4}
                        onClick={() => {
                          denyHandler(apply.id);
                        }}
                      />
                    </PendingBubble>
                  ))
                ) : (
                  <p className='null-message'>
                    아직 모집된 팀원이 없습니다... ;_;
                  </p>
                )}
              </AcceptedContainer>
            ) : (
              ''
            )
          )}
        </StatusContainer>
      ) : (
        ''
      )}
    </ApplicationContainer>
  );
};

const SubTitle = styled.h4`
  font-weight: 700;
  font-size: 22px;
  margin: 5px 0 20px 0;
`;

const ApplicationContainer = styled.div`
  display: flex;
  padding: 15px 0;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
`;

const StatusContainer = styled.div`
  width: 50%;
`;

const PositionsContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

const Position = styled.div`
  display: flex;
  align-items: center;
  font-size: 18px;
  > .position-name {
    font-weight: 600;
    width: 150px;
  }
  > .count {
    width: 60px;
  }
`;

const ApplyButton = styled.button`
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
  &.closed {
    background-color: ${({ theme }) => theme.colors.grey2};
    pointer-events: none;
  }
`;

const AcceptedContainer = styled.div`
  display: flex;
  gap: 15px;
  height: 76px;
  margin-bottom: 20px;
  :last-child {
    margin-bottom: 5px;
  }
  > .null-message {
    color: ${({ theme }) => theme.colors.grey4};
    font-size: 15px;
  }
`;

const PendingContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 15px;
  margin: 15px 0 5px 0;
  > .null-message {
    display: flex;
    align-items: center;
    color: ${({ theme }) => theme.colors.grey4};
    font-size: 15px;
    height: 38px;
  }
`;

const PendingBubble = styled.div`
  display: flex;
  gap: 5px;
  padding: 10px;
  border: 1px solid
    ${({ theme, isAccepted }) =>
      isAccepted ? theme.colors.purple1 : theme.colors.grey2};
  border-radius: 999px;
  height: 38px;
  > svg {
    cursor: pointer;
  }
`;

export default Application;
