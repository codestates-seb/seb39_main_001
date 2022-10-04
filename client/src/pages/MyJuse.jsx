import { useState } from 'react';
import { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import styled from 'styled-components';
import { apis } from '../apis/axios';
import Card from '../components/Card';

const MyJuse = () => {
	const [cookies] = useCookies();
	const token = cookies.user;

	const [data, setData] = useState({
		myBookmarkList: [],
		myParticipationList: [],
		myApplicationList: [],
		myBoards: [],
	});

	useEffect(() => {
		apis.getMyjuse(token).then((data) => setData(data));
	}, []);

	return (
		<MyJuseContainer>
			<JuseLabel>나의 북마크</JuseLabel>
			<CardContainer>
				{data.myBookmarkList.length ? (
					data.myBookmarkList.map((e, i) => <Card data={e} key={i} />)
				) : (
					<NullMessage>마음에 드는 모임을 북마크해 보세요!</NullMessage>
				)}
			</CardContainer>
			<JuseLabel>참여중인 모임</JuseLabel>
			<CardContainer>
				{data.myParticipationList.length ? (
					data.myParticipationList.map((e, i) => <Card data={e} key={i} />)
				) : (
					<NullMessage>지원을 통해 모임에 참여해보세요!</NullMessage>
				)}
			</CardContainer>
			<JuseLabel>작성한 글</JuseLabel>
			<CardContainer>
				{data.myBoards.length ? (
					data.myBoards.map((e, i) => <Card data={e} key={i} />)
				) : (
					<NullMessage>직접 팀장이 되어 사람들을 모아 보세요!</NullMessage>
				)}
			</CardContainer>
			<JuseLabel>지원한 모임</JuseLabel>
			<CardContainer>
				{data.myApplicationList.length ? (
					data.myApplicationList.map((e, i) => <Card data={e} key={i} />)
				) : (
					<NullMessage>마음에 드는 모임에 지원해 보세요!</NullMessage>
				)}
			</CardContainer>
		</MyJuseContainer>
	);
};

const MyJuseContainer = styled.div`
	max-width: 1300px;
	padding: 0 30px;
	padding-bottom: 50px;
	margin: auto;
`;

const JuseLabel = styled.div`
	font-size: 20px;
	font-weight: 700;
	color: ${({ theme }) => theme.colors.grey5};
	padding: 40px 0 30px 0;
`;

const CardContainer = styled.div`
	display: flex;
	flex-wrap: wrap;
	gap: 30px;
`;

const NullMessage = styled.p`
	color: ${({ theme }) => theme.colors.grey3};
`;

export default MyJuse;
