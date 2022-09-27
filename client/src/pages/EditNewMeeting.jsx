import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import styled from 'styled-components';
import EditHeaderTemplate from '../components/EditHeaderTemplate';
import TextEditor from '../components/TextEditor';
import { apis } from '../apis/axios';

const EditNewMeeting = () => {
	const navigate = useNavigate();
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
		const dataFetch = async () => {
			await axios
				// .get(`http://juse.iptime.org:8080/boards/${boardId}`)
				.get(`http://juse.iptime.org:8080/boards/4`, {
					headers: {
						Auth: 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGVhdHMwMUBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNjY0MjU0MTk3LCJleHAiOjE2NzMyNTQxOTd9.KoJKZMYTY_qcpdBUjqAtgwe46VzKLp0CCIQDZ6NOnOk',
					},
				})
				.then((res) => {
					const data = res.data.data;
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
				})
				.catch((err) => console.log(err));
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

		axios
			.patch('http://juse.iptime.org:8080/boards/4', formData, {
				headers: {
					Auth: 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGVhdHMwMUBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNjY0MjU0MTk3LCJleHAiOjE2NzMyNTQxOTd9.KoJKZMYTY_qcpdBUjqAtgwe46VzKLp0CCIQDZ6NOnOk',
				},
			})
			.then((res) => console.log(res))
			.then(() => {
				navigate('/');
			})
			.catch((err) => console.log(err));

		console.log('수정한 formData:', formData);
	};

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
