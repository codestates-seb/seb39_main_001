import { useState } from 'react';
import styled from 'styled-components';

const TechStack = ({ selected, setSelected, formData, setFormData }) => {
  // 모든 스택 이름
  const stacks = {
    프론트엔드: ['JavaScript', 'TypeScript', 'React', 'Vue', 'Svelte', 'Next'],
    백엔드: [
      'Java',
      'Spring',
      'Nodejs',
      'Nestjs',
      'Go',
      'Kotlin',
      'Express',
      'MySQL',
      'MongoDB',
      'Python',
      'Django',
      'php',
      'GraphQL',
      'Firebase',
    ],
    모바일: ['Flutter', 'Swift', 'Kotlin', 'ReactNative', 'Unity'],
    기타: [
      'AWS',
      'Kubernetes',
      'Docker',
      'Git',
      'Figma',
      'Zeplin',
      'Jest',
      'C',
    ],
  };

  const [currentTab, setCurrentTab] = useState('프론트엔드');

  //현재 탭 변경
  const tabHandler = (e) => {
    setCurrentTab(e.target.innerText);
  };

  //스택 선택
  const stackClickHandler = (e) => {
    if (selected.includes(e.target.innerText)) {
      const deletedArr = selected.filter((el) => el !== e.target.innerText);
      setSelected(deletedArr);
    } else {
      setSelected((prev) => [...prev, e.target.innerText]);
    }
    // post 요청 시 소문자로 바꿔서 tagList에 추가한다
    setFormData({
      ...formData,
      tagList: [...selected, e.target.innerText]
        .join(' ')
        .toLowerCase()
        .split(' '),
    });
  };

  //스택 초기화
  const stackResetHandler = () => {
    setSelected([]);
  };

  //스택 삭제
  const stackDeleteHandler = (idx) => {
    const deletedArr = selected.filter((e, i) => i !== idx);
    setSelected(deletedArr);
  };

  return (
    <TechStackContainer>
      <StackTab className="tab">
        {Object.keys(stacks).map((e, i) => (
          <li
            onClick={tabHandler}
            className={currentTab === e ? 'is-active' : ''}
            key={i}
          >
            {e}
          </li>
        ))}
        <li
          onClick={tabHandler}
          className={currentTab === '모두보기' ? 'is-active' : ''}
        >
          모두보기
        </li>
      </StackTab>
      <StackContainer>
        {currentTab !== '모두보기'
          ? stacks[currentTab].map((e, i) => (
              <div
                key={i}
                onClick={stackClickHandler}
                className={
                  !selected.includes(e) && selected.length > 0
                    ? 'not-selected'
                    : ''
                }
              >
                {e}
              </div>
            ))
          : [
              ...stacks.프론트엔드,
              ...stacks.백엔드,
              ...stacks.모바일,
              ...stacks.기타,
            ].map((e, i) => (
              <div
                key={i}
                onClick={stackClickHandler}
                className={
                  !selected.includes(e) && selected.length > 0
                    ? 'not-selected'
                    : ''
                }
              >
                {e}
              </div>
            ))}
      </StackContainer>
      <SelectedContainer>
        {selected.map((e, i) => (
          <div key={i}>
            {e}
            <button onClick={() => stackDeleteHandler(i)}>X</button>
          </div>
        ))}
      </SelectedContainer>
      {selected.length ? (
        <button onClick={stackResetHandler}>선택 초기화</button>
      ) : null}
    </TechStackContainer>
  );
};

const TechStackContainer = styled.div`
  margin: 20px 0;
`;

const StackTab = styled.ul`
  display: flex;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey1};
  margin-bottom: 15px;
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

const StackContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  > div {
    padding: 15px;
    border: 1px solid ${({ theme }) => theme.colors.grey3};
    border-radius: 999px;
    cursor: pointer;
  }
  > .not-selected {
    color: ${({ theme }) => theme.colors.grey3};
    border: 1px solid ${({ theme }) => theme.colors.grey1};
  }
`;

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

export default TechStack;
