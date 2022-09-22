import axios from 'axios';
import { useState } from 'react';
import { createRoutesFromElements } from 'react-router-dom';
import styled from 'styled-components';
import HeaderTemplate from '../components/HeaderTemplate';
import TextEditor from '../components/TextEditor';

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
    type: '',
    status: 'opening',
    tagList: [],
  });

  const [company, setCompany] = useState([{ position: 'frontend', count: 0 }]);

  const titleInputHandler = (e) => {
    setFormData({
      ...formData,
      title: e.target.value,
    });
  };

  const submitHandler = () => {
    const temp = {};
    company.forEach((e) => {
      Object.assign(temp, { [e.position]: e.count });
    });
    setFormData({ ...formData, ...temp });
    // Server 통신 시 Post 요청
    axios
      .post('/boards', formData)
      .then((res) => console.log(res))
      .catch((err) => console.log(err));

    console.log(formData);
  };

  return (
    <NewMeetingPageContainer>
      <Title>어떤 모임인가요?</Title>
      <HeaderTemplate
        formData={formData}
        setFormData={setFormData}
        company={company}
        setCompany={setCompany}
      />
      <Title>모임을 소개해주세요!</Title>
      <input
        type="text"
        placeholder="제목을 입력해주세요"
        onChange={titleInputHandler}
      />
      <TextEditor formData={formData} setFormData={setFormData} />
      <SubmitButton onClick={submitHandler}>등록하기</SubmitButton>
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
`;

const Title = styled.div`
  width: 800px;
  font-size: 18px;
  font-weight: 700;
  padding: 10px;
  color: ${({ theme }) => theme.colors.black1};
`;

const SubmitButton = styled.button`
  width: 100px;
  padding: 10px 15px;
  background: #ffffff;
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
