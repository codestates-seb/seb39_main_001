import { useState } from 'react';
import styled from 'styled-components';
import TechStack from '../components/TechStack';

const Home = () => {
  const [techFilter, setTechFilter] = useState([]);
  const [periodFilter, setPeriodFilter] = useState([]);
  const [currentTab, setCurrentTab] = useState('전체');

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

  const naming = (e) => {
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
    setCurrentTab(e.target.innerText);
  };

  return (
    <HomeContainer>
      <StyledCarousel>캐러셀 영역</StyledCarousel>
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
                {naming(e)}
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
          <li onClick={tabHandler} className={currentTab === '전체' ? 'is-active' : ''}>
            전체
          </li>
          <li onClick={tabHandler} className={currentTab === '프로젝트' ? 'is-active' : ''}>
            프로젝트
          </li>
          <li onClick={tabHandler} className={currentTab === '스터디' ? 'is-active' : ''}>
            스터디
          </li>
        </TypeSelector>
        <CreateButton>모집 글 작성</CreateButton>
      </ListHeader>
    </HomeContainer>
  );
};

const HomeContainer = styled.div`
  max-width: 1300px;
  margin: auto;
`;

const StyledCarousel = styled.div`
  height: 350px;
  background-color: ${({ theme }) => theme.colors.grey2};
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
  margin: 15px 0;
  > li {
    color: ${({ theme }) => theme.colors.grey3};
    padding: 10px 0;
    padding-right: 20px;
    cursor: pointer;
  }
  > .is-active {
    color: inherit;
  }
`;

const CreateButton = styled.button`
  padding: 5px 15px;
`;

export default Home;
