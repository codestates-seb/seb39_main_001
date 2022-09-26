import { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import styled from 'styled-components';
import EditHeaderTemplate from '../components/EditHeaderTemplate';
import TextEditor from '../components/TextEditor';
import { boards } from '../mocks/db';
import { board1 } from '../mocks/db';

// cookie를 props로 받아온다
const EditNewMeeting = () => {
	// Patch 요청 시 필요
	// const { boardId } = useParams();
	// const navigate = useNavigate();

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
		status: 'OPENING',
		tagList: [],
	});

	useEffect(() => {
		// mock data
		const dataFetch = async () => {
			await fetch('/board1')
				.then((res) => res.json())
				.then((res) => {
					const data = res.data; // board1.data
					console.log('board1.data:', data);
					console.log(data.type);
					setFormData({
						title: data.title,
						backend: data.backend,
						frontend: data.frontend,
						designer: data.designer,
						etc: data.etc,
						people: data.people,
						contact: data.contact,
						dueDate: data.dueDate,
						startingDate: data.startingDate,
						period: data.period,
						onOffline: data.onOffline,
						content: data.content,
						type: data.type,
						status: data.status,
						tagList: data.tagList,
					});
				});
		};
		dataFetch();
	}, []);

	const titleInputHandler = (e) => {
		setFormData({
			...formData,
			title: e.target.value,
		});
	};

	// Patch 요청
	const submitHandler = (e) => {
		e.preventDefault();
		console.log('submit하는 formData:', formData);

		// MSW Patch 요쳥 ----->
		// fetch('/board1', {
		// 	method: 'PATCH',
		// 	headers: {
		// 		'Content-Type': 'application/json',
		//     // 쿠키도 같이 헤더에 보낸다.
		// 	},
		// 	body: formData,
		// }).then((res) => {
		// 	fetch('/board1')
		// 		.then((res) => res.json())
		// 		.then((data) => {
		// 			setFormData(data);
		// 		});
		// });

		// Patch 요청 (서버 연결 후) ----->
		// axios
		//   .patch(`/boards/${boardId}`)
		//   .then((res) => console.log(res))
		//   .catch((err) => console.log(err))

		//   navigate(`/boards/${boardId}`);
		// console.log(formData);
	};

	// useEffect(() => {
	// 	const previousDataFetch = () => {
	// 		axios
	// 			.get(`/boards/${boardId}`)
	// 			.then((res) => {
	// 				const data = res.formData;
	// 				setFormData(...formData);
	// 			})
	// 			.catch((err) => console.log(err));
	// 	};
	// 	previousDataFetch();
	// }, []);

	return (
		<>
			<div>✍️ edit page</div>
			<NewMeetingPageContainer>
				<Title>어떤 모임인가요?</Title>
				<EditHeaderTemplate formData={formData} setFormData={setFormData} />
				<Title>모임을 소개해주세요!</Title>
				<input
					type='text'
					placeholder='제목을 입력해주세요'
					onChange={titleInputHandler}
					value={formData.title}
				/>
				<TextEditor formData={formData} setFormData={setFormData} />
				<div className='buttons'>
					<CancelButton to='../'>취소</CancelButton>
					<SubmitButton onClick={submitHandler}>등록하기</SubmitButton>
				</div>
			</NewMeetingPageContainer>
		</>
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

export default EditNewMeeting;
