import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import Card from '../components/Card';
import ScrollToTop from '../components/ScrollToTop';
import TechStack from '../components/TechStack';
import { Link, useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { apis } from '../apis/axios';
import { useInView } from 'react-intersection-observer';
import { useInfiniteQuery } from 'react-query';
import { ReactComponent as ToggleOn } from '../assets/icons/toggle-on.svg';
import { ReactComponent as ToggleOff } from '../assets/icons/toggle-off.svg';
import theme from '../assets/styles/Theme';
import Select from 'react-select';
import Carousel from '../components/Carousel';

const Home = () => {
  const [cookies] = useCookies();
  const token = cookies.user;
  const navigate = useNavigate();
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
    const periodArr = e.map((obj) => obj.value);
    setPeriodFilter(periodArr);
  };

  const periodOptions = [
    { value: 'short', label: '1개월 미만' },
    { value: '1', label: '1개월' },
    { value: '2', label: '2개월' },
    { value: '3', label: '3개월' },
    { value: '4', label: '4개월' },
    { value: '5', label: '5개월' },
    { value: '6', label: '6개월' },
    { value: 'long', label: '장기' },
  ];

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

  // 모집 글 작성 페이지로 이동
  const linkToCreate = () => {
    if (token) {
      navigate('/boards');
    } else {
      alert('로그인이 필요한 페이지입니다.');
    }
  };

  return (
    <HomeContainer>
      <Carousel />
      <TechStack selected={techFilter} setSelected={setTechFilter} />
      <PeriodContainer>
        <Select
          className='period-select'
          options={periodOptions}
          isMulti={true}
          placeholder={'모든 기간'}
          styles={{
            multiValue: (provided) => ({
              ...provided,
              backgroundColor: theme.colors.grey1,
              padding: '3px',
            }),
            multiValueRemove: (provided) => ({
              ...provided,
              color: theme.colors.black1,
            }),
            option: (provided, state) => ({
              ...provided,
              ':hover': {
                color: theme.colors.black1,
              },
            }),
          }}
          onChange={(e) => {
            dropDownHandler(e);
          }}
          theme={(theme) => ({
            ...theme,
            colors: {
              ...theme.colors,
              primary25: '#f0e7fe',
              primary: '#be99ff',
              background: `${({ theme }) => theme.background}`,
              text: `${({ theme }) => theme.text}`,
            },
          })}
        />
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
        <CreateButton onClick={linkToCreate}>모집 글 작성</CreateButton>
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
      {isFetchingNextPage ? '' : <div ref={ref}></div>}
      {status === 'loading' ? (
        <NullBoards>서버 휴식 중.. (๑ᵕ⌓ᵕ̤)...zzZ</NullBoards>
      ) : (
        ''
      )}
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

const PeriodContainer = styled.div`
  > .period-select {
    width: 50%;
    min-width: 300px;
    > div {
      background-color: ${({ theme }) => theme.background};
      color: ${({ theme }) => theme.text};
      border-color: ${({ theme }) => theme.colors.grey3};
    }
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
  > span {
    color: ${({ theme }) => theme.colors.black1};
  }
`;

const CreateButton = styled.button`
  padding: 10px 15px;
  background-color: ${({ theme }) => theme.background};
  font-size: 14px;
  color: ${({ theme }) => theme.colors.purple1};
  border: 1px solid ${({ theme }) => theme.colors.purple1};
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
  text-align: center;
`;

export default Home;
