import axios from 'axios';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import HeaderTemplate from '../components/HeaderTemplate';
import TextEditor from '../components/TextEditor';

// cookie를 props로 받아온다
const NewMeeting = () => {
  const [formData, setFormData] = useState({
    title: '',
    backend: 0,
    frontend: 0,
    designer: 0,
    etc: 0,
    people: 0,
    contact: '',
    dueDate: '',
    startingDate: '',
    period: '',
    onOffline: '',
    content: '',
    type: 'PROJECT',
    status: 'OPENING',
    tagList: [],
  });

  const [company, setCompany] = useState([{ position: 'frontend', count: 0 }]);

  // 모집 유형이 스터디일때 인원
  const [count, setCount] = useState(0);

  // 모집 유형이 study로 바뀔 때 프로젝트 모집 포지션, 인원 초기화 / project로 바뀔 때 스터디 인원 초기화
  useEffect(() => {
    if (formData.type === 'STUDY') {
      setCompany([{ position: 'frontend', count: 0 }]);
      setFormData({
        ...formData,
        frontend: 0,
        backend: 0,
        designer: 0,
        etc: 0,
        people: count,
      });
    }

    if (formData.type === 'PROJECT') {
      setCount(0);
      setFormData({
        ...formData,
        people: 0,
      });
    }
  }, [formData.type]);

  // 이벤트 핸들러
  const titleInputHandler = (e) => {
    setFormData({
      ...formData,
      title: e.target.value,
    });
  };

  // Post 요청
  const submitHandler = () => {
    // 모집 유형 미선택시 에러
    if (formData.type === '') {
      alert('모집 유형은 필수 선택입니다.');
      throw Error('모집 유형은 필수 선택입니다.');
    }

    // 기술 스택 미선택시 에러
    if (formData.tagList.length === 0) {
      alert('기술 스택은 반드시 1개 이상 추가해야 합니다.');
      throw Error('기술 스택은 반드시 1개 이상 추가해야 합니다.');
    }

    // 제목 미입력시 에러
    if (formData.title.length === 0) {
      alert('제목을 입력해주세요.');
      throw Error('제목을 입력해주세요.');
    }

    // 내용 미입력시 에러
    if (formData.content === '<p><br></p>') {
      alert('본문 내용을 입력해주세요.');
      throw Error('본문 내용을 입력해주세요.');
    }

    // 최소 인원 미입력시 에러
    if (
      formData.frontend === 0 &&
      formData.backend === 0 &&
      formData.designer === 0 &&
      formData.etc === 0
    ) {
      alert('최소 1명 이상의 인원을 선택해주세요.');
    }

    const temp = {};
    company.forEach((e) => {
      Object.assign(temp, { [e.position]: e.count });
    });

    setFormData({ ...formData, ...temp, people: count });

    // Post 요청
    // axios
    //   .post('juse.iptime.org:8080/boards', formData, { Auth: cookies.user })
    //   .then((res) => console.log(res))
    //   .catch((err) => console.log(err));

    console.log('formData.tagList:', formData.tagList);
    console.log('formData.content:', formData.content);
    console.log('formData:', formData);
  };

  return (
    <NewMeetingPageContainer>
      <Title>어떤 모임인가요?</Title>
      <HeaderTemplate
        formData={formData}
        setFormData={setFormData}
        company={company}
        setCompany={setCompany}
        count={count}
        setCount={setCount}
      />
      <Title>모임을 소개해주세요!</Title>
      <input
        type='text'
        placeholder='제목을 입력해주세요'
        onChange={titleInputHandler}
      />
      <TextEditor formData={formData} setFormData={setFormData} />
      <div className='buttons'>
        <CancelButton to='/'>취소</CancelButton>
        <SubmitButton onClick={submitHandler}>등록하기</SubmitButton>
      </div>
    </NewMeetingPageContainer>
  );
};

const NewMeetingPageContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  > input {
    width: 800px;
    padding: 10px 15px;
    border: 1px solid ${({ theme }) => theme.colors.grey3};
    border-radius: 4px;
    margin: 10px 0px 15px 0px;
    :focus {
      outline: 1px solid ${({ theme }) => theme.colors.purple1};
    }
    :hover {
      border: 1px solid ${({ theme }) => theme.colors.purple1};
    }
  }
  > .buttons {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    width: 250px;
  }
`;

const Title = styled.div`
  width: 800px;
  font-size: 18px;
  font-weight: 700;
  padding: 10px;
  color: ${({ theme }) => theme.colors.black1};
`;

const CancelButton = styled(Link)`
  width: 100px;
  padding: 10px 15px;
  background: #ffffff;
  font-size: 14px;
  text-align: center;
  color: ${({ theme }) => theme.colors.black1};
  border: 1px solid ${({ theme }) => theme.colors.grey3};
  border-radius: 4px;
  cursor: pointer;
  :hover {
    color: ${({ theme }) => theme.colors.black1};
    border: 1px solid ${({ theme }) => theme.colors.grey3};
    background: ${({ theme }) => theme.colors.grey2};
  }
`;

const SubmitButton = styled.button`
  width: 100px;
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

export default NewMeeting;
