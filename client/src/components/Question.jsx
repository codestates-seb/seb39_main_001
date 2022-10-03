import styled from 'styled-components';
import { ReactComponent as Edit } from '../assets/icons/edit.svg';
import { ReactComponent as Delete } from '../assets/icons/delete.svg';
import { useState } from 'react';
import { useCookies } from 'react-cookie';
import { apis } from '../apis/axios';
import { Link } from 'react-router-dom';
import { useMutation, useQueryClient } from 'react-query';

const Question = ({ data }) => {
  const [isEdit, setIsEdit] = useState(false);
  const [editContent, setEditContent] = useState(data.content);
  const [cookies] = useCookies();
  const token = cookies.user;
  const queryClient = useQueryClient();

	// 수정 인풋 받기
	const editChangeHandler = (e) => {
		setEditContent(e.target.value);
	};

  // 수정 submit
  const editClickMutation = useMutation(
    () => {
      if (editContent) {
        const questionId = data.id;
        const content = { content: editContent };
        apis.patchQuestion(token, content, questionId);
      } else {
        alert('질문 내용을 입력하세요.');
        return;
      }
    },
    {
      onSuccess: () => {
        setTimeout(() => {
          queryClient.invalidateQueries('board');
        }, 200);
        setIsEdit(false);
      },
      onError: () => {
        alert('질문 작성에 실패하였습니다.');
      },
    }
  );

  // 질문 삭제
  const deleteClickMutation = useMutation(
    () => {
      if (window.confirm('질문을 삭제하시겠습니까?')) {
        const questionId = data.id;
        apis.deleteQuestion(token, questionId);
        setEditContent('');
      } else {
        return;
      }
    },
    {
      onSuccess: () => {
        setTimeout(() => {
          queryClient.invalidateQueries('board');
        }, 200);
      },
      onError: () => {
        alert('질문 삭제에 실패하였습니다.');
      },
    }
  );

  return (
    <>
      <QuestionContent>
        <UserInfo>
          <Link to={`/users/${data.user.id}`}>
            <img src={data.user.img} alt='profile' />
          </Link>
          <NameContainer>
            <Link to={`/users/${data.user.id}`}>
              <p className='name'>{data.user.nickname}</p>
            </Link>
            <p className='date'>{`${
              data.createdAt.split('T')[0]
            } ${data.createdAt.split('T')[1].slice(0, 5)}`}</p>
          </NameContainer>
        </UserInfo>
        <ContentButton>
          <ContentContainer>{data.content}</ContentContainer>
          {data.auth ? (
            <ButtonContainer>
              <Edit
                onClick={() => {
                  setEditContent(data.content);
                  setIsEdit(!isEdit);
                }}
              />
              <Delete onClick={() => deleteClickMutation.mutate()} />
            </ButtonContainer>
          ) : (
            ''
          )}
        </ContentButton>
      </QuestionContent>
      {isEdit ? (
        <QuestionEditor>
          <textarea value={editContent} onChange={editChangeHandler}></textarea>
          <button onClick={() => editClickMutation.mutate()}>문의 수정</button>
        </QuestionEditor>
      ) : (
        ''
      )}
    </>
  );
};

const QuestionContent = styled.div`
  /* display: flex;
  justify-content: space-between;
  align-items: center; */
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
  display: flex;
  gap: 10px;
  img {
    width: 40px;
    height: 40px;
    padding: 1px;
    border: 1px solid ${({ theme }) => theme.colors.grey3};
    border-radius: 50%;
  }
`;

const NameContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  .name {
    font-weight: 700;
  }
  .date {
    color: ${({ theme }) => theme.colors.grey4};
    font-weight: 200;
  }
`;

const ContentButton = styled.div`
  display: flex;
  margin-top: 10px;
`;

const ContentContainer = styled.div`
  max-width: calc(100% - 80px);
  overflow-wrap: break-word;
  line-height: 1.6;
  padding-left: 50px;
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
