import styled from 'styled-components';
import { ReactComponent as Edit } from '../assets/icons/edit.svg';
import { ReactComponent as Delete } from '../assets/icons/delete.svg';
import { useState } from 'react';
import { useCookies } from 'react-cookie';
import { apis } from '../apis/axios';

const Answer = ({ data, auth, questionId }) => {
	const [isEdit, setIsEdit] = useState(false);
	const [editContent, setEditContent] = useState(data ? data.content : '');
	const [cookies] = useCookies();
	const token = cookies.user;
	const [answer, setAnswer] = useState('');

	// 수정 인풋 받기
	const editChangeHandler = (e) => {
		setEditContent(e.target.value);
	};

	// 수정 submit
	const editClickHandler = () => {
		const content = { content: editContent };
		const answerId = data.id;
		apis
			.patchAnswer(token, content, answerId)
			.then((res) => console.log(res))
			.then(setIsEdit(false));
	};

	// 답변 삭제
	const deleteHandler = () => {
		if (window.confirm('답변을 삭제하시겠습니까?')) {
			const answerId = data.id;
			apis.deleteAnswer(token, answerId).then((res) => console.log(res));
		} else {
			return;
		}
	};

	// 답변 인풋 핸들러
	const aInputHandler = (e) => {
		setAnswer(e.target.value);
	};

	// 답변 등록 핸들러
	const aSubmitHandler = () => {
		const content = { content: answer };
		apis.postAnswer(token, content, questionId);
	};

	return (
		<>
			{data ? (
				<AnswerContainer>
					{/* <span className='label'>답변 :</span> */}
					<span>{data.content}</span>
					{auth ? (
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
				</AnswerContainer>
			) : auth ? (
				<AnswerCreator>
					<textarea
						placeholder='답변을 입력하세요.'
						onChange={aInputHandler}
						value={answer}
					/>
					<SubmitButton onClick={aSubmitHandler}>답변 등록</SubmitButton>
				</AnswerCreator>
			) : (
				''
			)}
			{isEdit ? (
				<AnswerEditor>
					<textarea value={editContent} onChange={editChangeHandler}></textarea>
					<SubmitButton onClick={editClickHandler}>답변 수정</SubmitButton>
				</AnswerEditor>
			) : (
				''
			)}
		</>
	);
};

const AnswerContainer = styled.div`
	display: flex;
	justify-content: space-between;
	> .label {
		margin-left: 30px;
	}
	border: 1px solid blue;
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
		margin-left: 30px;
		padding: 5px;
		width: 500px;
	}
`;

const SubmitButton = styled.button`
	padding: 5px 10px;
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

export default Answer;
