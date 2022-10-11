import styled from 'styled-components';
import Question from './Question';
import Answer from './Answer';
import { useState } from 'react';
import { apis } from '../apis/axios';
import { useCookies } from 'react-cookie';
import { useMutation, useQueryClient } from 'react-query';

const QuestionAnswer = ({ data }) => {
  const [cookies] = useCookies();
  const token = cookies.user;
  const [question, setQuestion] = useState('');
  const queryClient = useQueryClient();

  // 질문 인풋 핸들러
  const qInputHandler = (e) => {
    setQuestion(e.target.value);
  };

  // 질문 등록 핸들러
  const qSubmitMutation = useMutation(
    () => {
      if (token) {
        if (question) {
          const boardId = data.id;
          const content = { content: question };
          apis.postQuestion(token, content, boardId);
        } else {
          alert('질문 내용을 입력하세요.');
        }
      } else {
        alert('로그인이 필요한 기능입니다.');
      }
      return;
    },
    {
      onSuccess: () => {
        setTimeout(() => {
          queryClient.invalidateQueries('board');
        }, 200);
        setQuestion('');
      },
      onError: () => {
        alert('질문 작성에 실패하였습니다.');
      },
    }
  );

  return (
    <QuestionContainer>
      <SubTitle>문의 사항</SubTitle>
      {data.questionList.map((e, i) => (
        <ContentContainer key={i}>
          <Question data={e} />
          <Answer data={e.answer} auth={data.auth} questionId={e.id} />
        </ContentContainer>
      ))}
      <QuestionCreator>
        <textarea
          placeholder='문의 사항을 입력하세요.'
          value={question}
          onChange={qInputHandler}
        />
        <SubmitButton onClick={() => qSubmitMutation.mutate()}>
          문의 등록
        </SubmitButton>
      </QuestionCreator>
    </QuestionContainer>
  );
};

const SubTitle = styled.h4`
  font-weight: 700;
  font-size: 22px;
  margin: 5px 0 20px 0;
`;

const QuestionContainer = styled.div`
  padding: 15px 0;
`;

const ContentContainer = styled.div`
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
  padding: 10px 0 20px 0;
`;

const QuestionCreator = styled.div`
  padding: 10px 0;
  display: flex;
  flex-direction: column;
  > textarea {
    resize: none;
    width: 100%;
    min-height: 100px;
    padding: 10px;
    margin: 10px 0;
    border: 2px solid ${({ theme }) => theme.colors.grey2};
    border-radius: 4px;
    background: ${({ theme }) => theme.background};
    color: ${({ theme }) => theme.text};
    :focus {
      outline-color: ${({ theme }) => theme.colors.purple1};
    }
    :focus::-webkit-input-placeholder {
      color: transparent;
    }
  }
  > button {
    margin-left: auto;
    padding: 10px;
  }
`;

const SubmitButton = styled.button`
  padding: 10px 15px;
  background: ${({ theme }) => theme.background};
  font-size: 14px;
  color: ${({ theme }) => theme.colors.grey4};
  border: 1px solid ${({ theme }) => theme.colors.grey4};
  border-radius: 4px;
  cursor: pointer;
  :hover {
    color: ${({ theme }) => theme.colors.purple1};
    border: 1px solid ${({ theme }) => theme.colors.purple1};
    background: ${({ theme }) => theme.background};
  }
`;

export default QuestionAnswer;
