import { useState } from 'react';
import { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import styled from 'styled-components';
import { apis } from '../apis/axios';
import { useInView } from 'react-intersection-observer';
import { useInfiniteQuery } from 'react-query';
import { notifications } from '../mocks/db';
import { Link } from 'react-router-dom';
import { ReactComponent as Indicator } from '../assets/icons/circle-fill.svg';
import { ReactComponent as CloseBtnIcon } from '../assets/icons/close.svg';
import theme from '../assets/styles/Theme';
import NotiBoard from '../components/NotiBoard';

const Notification = () => {
  const [cookies] = useCookies();
  const token = cookies.user;
  const [currentTab, setCurrentTab] = useState('new');
  // // infinite scroll
  // const { ref, inView } = useInView();
  // const { data, status, fetchNextPage, isFetchingNextPage, refetch } =
  //   useInfiniteQuery(
  //     [currentTab, token],
  //     ({ pageParam = 1 }) =>
  //       apis.getNotifications(token, currentTab, pageParam),
  //     {
  //       getNextPageParam: (lastPage) =>
  //         !lastPage.isLast ? lastPage.nextPage : undefined,
  //     }
  //   );
  // useEffect(() => {
  //   if (inView) fetchNextPage();
  // }, [inView, fetchNextPage]);

  const data = notifications;

  // 게시글 타입 탭 변경
  const tabHandler = (e) => {
    let tabValue = 'new';
    if (e.target.innerText === '읽음') {
      tabValue = 'old';
    } else if (e.target.innerText === '전체') {
      tabValue = '';
    }
    setCurrentTab(tabValue);
  };

  return (
    <NotiContainer>
      <NotiHeader>
        <h2>알림함</h2>
      </NotiHeader>
      <MiniNav>
        <NotiTab>
          <li
            onClick={tabHandler}
            className={currentTab === 'new' ? 'is-active' : ''}>
            안읽음
          </li>
          <li
            onClick={tabHandler}
            className={currentTab === 'old' ? 'is-active' : ''}>
            읽음
          </li>
          <li
            onClick={tabHandler}
            className={currentTab === '' ? 'is-active' : ''}>
            전체
          </li>
        </NotiTab>
        <ClearAll>모두 읽음 표시</ClearAll>
      </MiniNav>
      <NotiWrapper>
        {data?.map((el, idx) => (
          <Link to={`/boards/${el.boardId}`} key={el.id}>
            <NotiBoard el={el} />
          </Link>
        ))}
      </NotiWrapper>
    </NotiContainer>
  );
};

const NotiContainer = styled.div`
  max-width: 1300px;
  padding: 0 30px;
  padding-bottom: 50px;
  margin: auto;
`;

const NotiHeader = styled.header`
  padding: 50px 0 20px 0;
  > h2 {
    font-weight: 700;
    font-size: 26px;
  }
`;

const MiniNav = styled.nav`
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
  margin-top: 15px;
`;

const NotiTab = styled.ul`
  display: flex;
  align-items: center;
  font-size: 22px;
  > li {
    color: ${({ theme }) => theme.colors.grey3};
    padding: 10px 0;
    margin-right: 20px;
    cursor: pointer;
  }
  > .is-active {
    color: inherit;
    font-weight: 600;
  }
`;

const ClearAll = styled.div`
  color: ${({ theme }) => theme.colors.grey4};
  :hover {
    color: ${({ theme }) => theme.colors.purple1};
    cursor: pointer;
  }
`;

const NotiWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 20px 0;
`;

export default Notification;
