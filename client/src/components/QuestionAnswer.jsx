import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { ReactComponent as Edit } from '../assets/icons/edit.svg';
import { ReactComponent as Delete } from '../assets/icons/delete.svg';
import { useState } from 'react';

const QuestionAnswer = ({ data }) => {
  const [isQEdit, setIsQEdit] = useState(false);
  const [isAEdit, setIsAEdit] = useState(false);

  const [qContent, setQContent] = useState('');
  const [aContent, setAContent] = useState('');

  // 인풋 핸들러
  const questionChange = (e) => {
    setQContent(e.target.value);
  };
  const answerChange = (e) => {
    setAContent(e.target.value);
  };

  // 클릭 핸들러
  const qEditMode = (e) => {
    setIsQEdit(true);
  };
  const aEditMode = () => {
    setIsAEdit(true);
  };

  return (
    <QuestionContainer>
      <SubTitle>문의 사항</SubTitle>
      {data.questionList.map((e, i) => {
        return (
          <ContentContainer key={i}>
            <QuestionContent>
              <div>{e.content}</div>
              <ButtonContainer>
                <Edit onClick={() => qEditMode(e)} />
                <Delete />
              </ButtonContainer>
              <div>{e.user.nickname}</div>
            </QuestionContent>
            {e.answer ? (
              isAEdit ? (
                <Editor>
                  <input type='text' value={e.answer.content} />
                  <button>답변 등록</button>
                </Editor>
              ) : (
                <AnswerContainer>
                  <span className='label'>답변 :</span>
                  <span>{e.answer.content}</span>
                  <ButtonContainer>
                    <Edit />
                    <Delete />
                  </ButtonContainer>
                  <div>{e.answer.user.nickname}</div>
                </AnswerContainer>
              )
            ) : (
              <AnswerCreator>
                <input type='text' value={aContent} placeholder='답변을 입력하세요.' onChange={answerChange} />
                <button>답변 등록</button>
              </AnswerCreator>
            )}
          </ContentContainer>
        );
      })}
      <QuestionCreator>
        <textarea value={qContent} placeholder='문의 사항을 입력하세요.' onChange={questionChange} />
        <button>문의 등록</button>
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

const QuestionContent = styled.div`
  display: flex;
  padding-bottom: 20px;
`;

const AnswerContainer = styled.div`
  display: flex;
  > .label {
    margin-left: 30px;
  }
`;

const AnswerCreator = styled.div`
  display: flex;
  gap: 10px;
  > input {
    margin-left: 30px;
    padding: 5px;
    width: 500px;
  }
`;

const ButtonContainer = styled.div`
  margin-left: auto;
  margin-right: 20px;
  display: flex;
  gap: 10px;
  color: ${({ theme }) => theme.colors.grey4};
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
  }
  > button {
    margin-left: auto;
    padding: 10px;
  }
`;

const Editor = styled.div``;

export default QuestionAnswer;
