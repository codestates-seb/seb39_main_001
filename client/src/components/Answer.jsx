import styled from 'styled-components';
import { ReactComponent as Edit } from '../assets/icons/edit.svg';
import { ReactComponent as Delete } from '../assets/icons/delete.svg';
import { ReactComponent as Reply } from '../assets/icons/reply.svg';
import { useState } from 'react';
import { useCookies } from 'react-cookie';
import { apis } from '../apis/axios';
import { Link } from 'react-router-dom';
import { useMutation, useQueryClient } from 'react-query';

const Answer = ({ data, auth, questionId }) => {
  const [isEdit, setIsEdit] = useState(false);
  const [editContent, setEditContent] = useState(data ? data.content : '');
  const [cookies] = useCookies();
  const token = cookies.user;
  const [answer, setAnswer] = useState('');
  const queryClient = useQueryClient();

  // 수정 인풋 받기
  const editChangeHandler = (e) => {
    setEditContent(e.target.value);
  };

  // 수정 submit
  const editClickMutation = useMutation(
    () => {
      if (editContent) {
        const answerId = data.id;
        const content = { content: editContent };
        apis.patchAnswer(token, content, answerId);
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

  // 답변 삭제
  const deleteHandler = () => {
    if (window.confirm('답변을 삭제하시겠습니까?')) {
      const answerId = data.id;
      apis.deleteAnswer(token, answerId).then((res) => console.log(res));
      setAnswer('');
    } else {
      return;
    }
  };

  // 답변 인풋 핸들러
  const aInputHandler = (e) => {
    setAnswer(e.target.value);
  };

  // 답변 등록 핸들러
  const qSubmitMutation = useMutation(
    () => {
      if (answer) {
        const content = { content: answer };
        apis.postAnswer(token, content, questionId);
      } else {
        alert('답변 내용을 입력하세요.');
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
        alert('답변 작성에 실패하였습니다.');
      },
    }
  );

  return (
    <>
      {data ? (
        <AnswerContainer>
          <Reply />
          <div className='flex-container'>
            <UserInfo>
              <Link to={`/users/${data.user.id}`}>
                <UserImg>
                  <img src={data.user.img} alt='profile' />
                </UserImg>
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
              <p>{data.content}</p>
              {auth ? (
                <ButtonContainer>
                  <Edit
                    onClick={() => {
                      setEditContent(data.content);
                      setIsEdit(!isEdit);
                    }}
                  />
                  <Delete onClick={() => deleteHandler()} />
                </ButtonContainer>
              ) : (
                ''
              )}
            </ContentButton>
          </div>
        </AnswerContainer>
      ) : auth ? (
        <AnswerCreator>
          <textarea
            placeholder='답변을 입력하세요.'
            onChange={aInputHandler}
            value={answer}
          />
          <SubmitButton onClick={() => qSubmitMutation.mutate()}>
            답변 등록
          </SubmitButton>
        </AnswerCreator>
      ) : (
        ''
      )}
      {isEdit ? (
        <AnswerEditor>
          <textarea value={editContent} onChange={editChangeHandler}></textarea>
          <SubmitButton onClick={() => editClickMutation.mutate()}>
            답변 수정
          </SubmitButton>
        </AnswerEditor>
      ) : (
        ''
      )}
    </>
  );
};

const AnswerContainer = styled.div`
  display: flex;

  > svg {
    width: 20px;
    height: 20px;
    transform: rotate(180deg);
    margin: 5px 15px;
  }
  > .flex-container {
    width: calc(100% - 50px);
  }
`;

const NameContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  .name {
    font-weight: 700;
    color: ${({ theme }) => theme.colors.purple1};
  }
  .date {
    color: ${({ theme }) => theme.colors.grey4};
    font-weight: 200;
  }
`;

const ContentButton = styled.div`
  margin-top: 10px;
  display: flex;
  /* max-width: 1098px; */
  > p {
    max-width: calc(100% - 80px);
    overflow-wrap: break-word;
    line-height: 1.6;
    padding-left: 50px;
  }
`;

const ButtonContainer = styled.div`
	margin-left: auto;
	display: flex;
	gap: 10px;
	color: ${({ theme }) => theme.colors.grey4};
	> svg {
		cursor: pointer;
		:hover {
			color: ${({ theme }) => theme.colors.purple1};
		}
	}
`;

const UserInfo = styled.div`
  display: flex;
  gap: 10px;
`;

const UserImg = styled.div`
  position: relative;
  width: 40px;
  height: 40px;
  border: 1px solid ${({ theme }) => theme.colors.grey3};
  border-radius: 50%;
  > img {
    position: absolute;
    top: 0;
    left: 0;
    transform: translate(50, 50);
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 999px;
    margin: auto;
    padding: 2px;
  }
`;

const AnswerEditor = styled.div`
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
		border-radius: 4px;
		background: ${({ theme }) => theme.background};
		color: ${({ theme }) => theme.text};
	}
	> button {
		padding: 10px;
		flex-shrink: 0;
	}
`;

const AnswerCreator = styled.div`
	display: flex;
	gap: 10px;
	> textarea {
		resize: none;
		margin-left: 50px;
		padding: 5px;
		width: calc(100% - 90px);
		border: 2px solid ${({ theme }) => theme.colors.grey2};
		border-radius: 4px;
		background: ${({ theme }) => theme.background};
		color: ${({ theme }) => theme.text};
	}
	> button {
		width: 80px;
	}
`;

const SubmitButton = styled.button`
	padding: 5px 10px;
	background: #ffffff;
	font-size: 14px;
	color: ${({ theme }) => theme.colors.grey4};
	border: 1px solid ${({ theme }) => theme.colors.grey4};
	background: ${({ theme }) => theme.background};
	border-radius: 4px;
	cursor: pointer;
	:hover {
		color: ${({ theme }) => theme.colors.purple1};
		border: 1px solid ${({ theme }) => theme.colors.purple1};
	}
`;

export default Answer;
