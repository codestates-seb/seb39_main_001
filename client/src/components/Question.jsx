import styled from 'styled-components';
import { ReactComponent as Edit } from '../assets/icons/edit.svg';
import { ReactComponent as Delete } from '../assets/icons/delete.svg';
import { useState } from 'react';
import { useCookies } from 'react-cookie';
import { apis } from '../apis/axios';

const Question = ({ data }) => {
  const [isEdit, setIsEdit] = useState(false);
  const [editContent, setEditContent] = useState(data.content);
  const [cookies] = useCookies();
  const token = cookies.user;

  // 수정 인풋 받기
  const editChangeHandler = (e) => {
    setEditContent(e.target.value);
  };

  // 수정 submit
  const editClickHandler = () => {
    const content = { content: editContent };
    const questionId = data.id;
    apis
      .patchQuestion(token, content, questionId)
      .then((res) => console.log(res))
      .then(setIsEdit(false));
  };

  // 질문 삭제
  const deleteHandler = () => {
    if (window.confirm('질문을 삭제하시겠습니까?')) {
      const questionId = data.id;
      apis.deleteQuestion(token, questionId).then((res) => console.log(res));
    } else {
      return;
    }
  };

  return (
    <>
      <QuestionContent>
        <div>{data.content}</div>
        {data.auth ? (
          <ButtonContainer>
            <Edit
              onClick={() => {
                setIsEdit(!isEdit);
              }}
            />
            <Delete onClick={deleteHandler} />
          </ButtonContainer>
        ) : (
          ''
        )}
        <UserInfo>{data.user.nickname}</UserInfo>
      </QuestionContent>
      {isEdit ? (
        <QuestionEditor>
          <textarea value={editContent} onChange={editChangeHandler}></textarea>
          <button onClick={editClickHandler}>문의 수정</button>
        </QuestionEditor>
      ) : (
        ''
      )}
    </>
  );
};

const QuestionContent = styled.div`
  display: flex;
  justify-content: space-between;
  padding-bottom: 20px;
`;

const ButtonContainer = styled.div`
  margin-left: auto;
  display: flex;
  gap: 10px;
  color: ${({ theme }) => theme.colors.grey4};
  > svg {
    cursor: pointer;
  }
`;

const UserInfo = styled.div`
  margin-left: 20px;
`;

const QuestionEditor = styled.div`
  display: flex;
  align-items: center;
  > textarea {
    resize: none;
    width: 100%;
    min-height: 50px;
    padding: 10px;
    margin: 10px 0;
    margin-right: 10px;
    border: 2px solid ${({ theme }) => theme.colors.grey2};
  }
  > button {
    padding: 10px;
    flex-shrink: 0;
  }
`;

export default Question;
