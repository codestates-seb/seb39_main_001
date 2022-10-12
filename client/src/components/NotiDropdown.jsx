import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { ReactComponent as Inbox } from '../assets/icons/inbox.svg';

const NotiDropdown = () => {
	// const NotiDropdown = ({ data }) => {
	// const NotiSummary = ({ data }) => {
	//   if (data.type === 'APPLICATION') {
	//     return (<p>{data.nickname} 님이 게시글에 지원하였습니다.</p>);
	//   } else if (data.type === 'LIKE') {
	//     return (<p>{data.nickname} 님이 회원님을 좋아요 했습니다.</p>);
	//   } else if (data.type === 'ACCEPT') {
	//     return (<p>지원하신 모임에 수락되었습니다.</p>);
	//   } else if (data.type === 'DECLINE') {
	//     return (<p>지원하신 모임에 거절되었습니다.</p>);
	//   } else if (data.type === 'CLOSE') {
	//     return (<p>북마크한 게시글이 모집마감 되었습니다.</p>);
	//   } else if (data.type === 'QUESTION') {
	//     return (<p>작성하신 게시글에 문의 사항이 등록되었습니다.</p>);
	//   } else if (data.type === 'ANSWER') {
	//     return (<p>작성하신 문의 사항에 답변이 등록되었습니다.</p>);
	//   } else if (data.type === 'UPCOMING') {
	//     return <p>북마크한 게시글의 마감 기한이 3일 남았습니다.</p>;
	//   }
	// };

	return (
		<DropdownNotification>
			<p className='count-message'>읽지 않은 알림이 5개 있습니다</p>
			{/* <NotificationLink to='/boards/17'>
									<p>말포이 님이 문의사항을 등록했습니다.</p>
									<span>30분 전</span>
								</NotificationLink>
								<NotificationLink to='/boards/17'>
									<p>헐마이어머니 님이 문의사항에 대한 답변을 등록했습니다.</p>
									<span>43분 전</span>
								</NotificationLink>
								<NotificationLink to='/boards/17'>
									<p>말포이 님이 게시글에 지원했습니다.</p>
									<span>1시간 전</span>
								</NotificationLink>
								<NotificationLink to='/boards/17'>
									<p>게시글이 모집마감 되었습니다.</p>
									<span>2시간 전</span>
								</NotificationLink>
								<NotificationLink to='/boards/17'>
									<p>북마크한 게시글의 마감 기한이 3일 남았습니다.</p>
									<span>어제</span>
								</NotificationLink> */}
			{/* {data.map((el) => (
				<NotificationLink to={`/boards/${el.boardId}`}>
					<NotiSummary />
					<span>{el.createdAt}</span>
				</NotificationLink>
			))} */}
			<NotificationBtn to='/notifications'>
				<Inbox />
				알림함으로 이동
			</NotificationBtn>
		</DropdownNotification>
	);
};

const DropdownNotification = styled.nav`
	background: ${({ theme }) => theme.background};
	border-radius: 8px;
	position: absolute;
	top: 45px;
	right: -56px;
	width: 350px;
	box-shadow: 0 1px 8px ${({ theme }) => theme.colors.grey2};
	z-index: 999;
	color: ${({ theme }) => theme.colors.grey4};
	.count-message {
		font-size: 12px;
		text-align: center;
		padding-top: 10px;
	}
`;

const NotificationLink = styled(Link)`
	display: flex;
	justify-content: space-between;
	border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
	padding: 15px 20px;
	font-size: 14px;
	color: ${({ theme }) => theme.colors.black1};
	> i {
		margin-right: 10px;
	}
	span {
		font-size: 12px;
		color: ${({ theme }) => theme.colors.grey4};
	}
`;

const NotificationBtn = styled(Link)`
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 10px;
	font-size: 14px;
	/* width: 140px; */
	> svg {
		margin-right: 10px;
	}
	:hover {
		color: ${({ theme }) => theme.colors.purple1};
	}
`;

export default NotiDropdown;
