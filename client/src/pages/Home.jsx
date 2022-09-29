import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import Card from '../components/Card';
import ScrollToTop from '../components/ScrollToTop';
import TechStack from '../components/TechStack';
import { Link } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { apis } from '../apis/axios';
import { useInView } from 'react-intersection-observer';
import { useInfiniteQuery } from 'react-query';
import { ReactComponent as ToggleOn } from '../assets/icons/toggle-on.svg';
import { ReactComponent as ToggleOff } from '../assets/icons/toggle-off.svg';
import theme from '../assets/styles/Theme';

const Home = () => {
  const [cookies] = useCookies();
  const token = cookies.user;
  // filter Parameters
  const [techFilter, setTechFilter] = useState([]);
  const [periodFilter, setPeriodFilter] = useState([]);
  const [currentTab, setCurrentTab] = useState('');
  const [statusFilter, setStatusFilter] = useState('opening');
  // infinite scroll
  const { ref, inView } = useInView();
  const { data, status, fetchNextPage, isFetchingNextPage } = useInfiniteQuery(
    [techFilter, periodFilter, currentTab, statusFilter, token],
    ({ pageParam = 1 }) =>
      apis.getBoards(
        token,
        currentTab,
        techFilter,
        periodFilter,
        statusFilter,
        pageParam
      ),
    {
      getNextPageParam: (lastPage) =>
        !lastPage.isLast ? lastPage.nextPage : undefined,
    }
  );
  useEffect(() => {
    if (inView) fetchNextPage();
  }, [inView, fetchNextPage]);

  // 프로젝트 기간 필터 설정
  const dropDownHandler = (e) => {
    if (!periodFilter.includes(e.target.value)) {
      setPeriodFilter((prev) => [...prev, e.target.value]);
    }
  };

  // 프로젝트 기간 필터 삭제
  const periodDeleteHandler = (idx) => {
    const deletedArr = periodFilter.filter((e, i) => i !== idx);
    setPeriodFilter(deletedArr);
  };

  const periodNaming = (e) => {
    if (e === 'short') {
      return '1개월 미만';
    } else if (e === 'long') {
      return '장기';
    } else {
      return e + '개월';
    }
  };

  // 게시글 타입 탭 변경
  const tabHandler = (e) => {
    let tabValue = '';
    if (e.target.innerText === '프로젝트') {
      tabValue = 'project';
    } else if (e.target.innerText === '스터디') {
      tabValue = 'study';
    }
    setCurrentTab(tabValue);
  };

  // 모집 중, 완료 토글
  const statusToggle = () => {
    if (statusFilter === 'opening') {
      setStatusFilter('');
    } else {
      setStatusFilter('opening');
    }
  };

  return (
    <HomeContainer>
      <StyledCarousel></StyledCarousel>
      <TechStack selected={techFilter} setSelected={setTechFilter} />
      <PeriodContainer>
        <select onChange={dropDownHandler} defaultValue='기간 설정'>
          <option disabled>기간 설정</option>
          <option value='short'>1개월 미만</option>
          <option value='1'>1개월</option>
          <option value='2'>2개월</option>
          <option value='3'>3개월</option>
          <option value='4'>4개월</option>
          <option value='5'>5개월</option>
          <option value='6'>6개월</option>
          <option value='long'>장기</option>
        </select>
        {periodFilter.length ? (
          <SelectedContainer>
            {periodFilter.map((e, i) => (
              <div key={i}>
                {periodNaming(e)}
                <button onClick={() => periodDeleteHandler(i)}>X</button>
              </div>
            ))}
          </SelectedContainer>
        ) : (
          ''
        )}
      </PeriodContainer>
      <ListHeader>
        <TypeSelector>
          <li
            onClick={tabHandler}
            className={currentTab === '' ? 'is-active' : ''}>
            전체
          </li>
          <li
            onClick={tabHandler}
            className={currentTab === 'project' ? 'is-active' : ''}>
            프로젝트
          </li>
          <li
            onClick={tabHandler}
            className={currentTab === 'study' ? 'is-active' : ''}>
            스터디
          </li>
          <StatusSelector>
            {statusFilter === 'opening' ? (
              <ToggleOn
                width={'26px'}
                height={'26px'}
                fill={theme.colors.purple1}
                onClick={statusToggle}
              />
            ) : (
              <ToggleOff
                width={'26px'}
                height={'26px'}
                fill={theme.colors.grey4}
                onClick={statusToggle}
              />
            )}
            <span>모집 중만 보기</span>
          </StatusSelector>
        </TypeSelector>
        <Link to='/boards'>
          <CreateButton>모집 글 작성</CreateButton>
        </Link>
      </ListHeader>
      <BoardsContainer>
        {data?.pages.map((page, index) => (
          <React.Fragment key={index}>
            {page.data.length ? (
              page.data.map((e, i) => <Card key={e.id} data={e} />)
            ) : (
              <NullBoards>조건에 맞는 글이 없습니다. T^T</NullBoards>
            )}
          </React.Fragment>
        ))}
      </BoardsContainer>
      {isFetchingNextPage ? <div>loading</div> : <div ref={ref}></div>}
      <ScrollToTop />
    </HomeContainer>
  );
};

const HomeContainer = styled.div`
  max-width: 1300px;
  margin: auto;
  padding: 0 30px;
  padding-bottom: 100px;
`;

const StyledCarousel = styled.div`
  height: 350px;
  background-color: ${({ theme }) => theme.colors.grey2};
  background-image: url('치킨밀크티.png');
  background-repeat: no-repeat;
  background-size: contain;
  background-position: center;
`;

const PeriodContainer = styled.div``;

const SelectedContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
  padding: 20px 0;
  > div {
    padding: 7px;
    background-color: ${({ theme }) => theme.colors.grey1};
  }
`;

const ListHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const TypeSelector = styled.ul`
  display: flex;
  align-items: center;
  margin: 15px 0;
  font-size: 24px;
  > li {
    color: ${({ theme }) => theme.colors.grey3};
    padding: 10px 0;
    margin-right: 20px;
    cursor: pointer;
  }
  > .is-active {
    color: inherit;
  }
`;

const StatusSelector = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  color: ${({ theme }) => theme.colors.grey5};
`;

const CreateButton = styled.button`
  padding: 10px 15px;
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
`;

const BoardsContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 40px 60px;
`;

const NullBoards = styled.div`
  padding: 100px 0 50px 0;
  font-size: 24px;
  color: ${({ theme }) => theme.colors.grey4};
`;

export default Home;
