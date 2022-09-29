import { useState, useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { apis } from '../apis/axios';
import { ReactComponent as Heart } from '../assets/icons/heart.svg';
import { ReactComponent as HeartFill } from '../assets/icons/heart-fill.svg';

const UserInfo = () => {
	const [cookies, setCookies, removeCookie] = useCookies();
	const token = cookies.user;
	// const myId = +cookies.userId;
	const myId = 1;
	const [isLike, setIsLike] = useState(false);

	const navigate = useNavigate();

	const [data, setData] = useState({
		id: '',
		nickname: '',
		like: 0,
		myUserList: [],
		skillStackTags: [],
		introduction: '',
		email: '',
		portfolio: '',
	});

	const {
		nickname,
		liked,
		myUserList,
		skillStackTags,
		introduction,
		email,
		portfolio,
	} = data;
	const location = useLocation().pathname;
	const isMe = location === '/users' ? true : false;

	// 나의 마이페이지인지, 남의 마이페이지인지 구분하여 api 호출
	useEffect(() => {
		if (isMe) {
			apis.getUsers(token).then((res) => setData(res));
		} else {
			apis.getOtherUsers(token, location.slice(-1)).then((res) => setData(res));
		}
	}, []);

	const addLikeHandler = () => {
		// 내가 좋아요를 누르는 사람이 나 자신이 아닌지 확인
		if (!isMe && myId !== data.id) {
			setIsLike((prev) => !prev);
			console.log('좋아요');
			// 좋아요 post 요청
			apis.postLike(token, data.id);
		} else {
			alert('자기 자신을 좋아요 할 수 없습니다!');
		}
	};

	const deleteLikeHandler = () => {
		// 내가 좋아요 해제를 누르는 사람이 나 자신이 아닌지 확인
		if (!isMe && myId !== data.id) {
			setIsLike((prev) => !prev);
			console.log('좋아요 취소');
			// 좋아요 delete 요청
			apis.deleteLike(token, data.id);
		} else {
			alert('자기 자신을 싫어요 할 수 없습니다!');
		}
	};

	// 회원 탈퇴
	const deleteHandler = () => {
		if (window.confirm('정말 탈퇴하시겠습니까?')) {
			apis
				.deleteUser(token)
				.then(removeCookie('user', { path: '/' }))
				.then(navigate('/'));
		} else {
			return;
		}
	};

	console.log('myId:', myId);
	console.log('data.id:', data.id);
	console.log('location:', location);
	console.log('isMe:', isMe);
	console.log('likedByMe:', data.likedByMe);

	return (
		<UserInfoContainer>
			<BasicInfo>
				<UserImg></UserImg>
				<RoundButton>
					{isLike ? (
						<HeartFill fill='tomato' onClick={deleteLikeHandler} />
					) : (
						<Heart onClick={addLikeHandler} />
					)}
				</RoundButton>
				<MiniBox>{nickname}</MiniBox>
				<MiniBox>
					<HeartFill fill='tomato' />
					{liked}
				</MiniBox>
			</BasicInfo>
			<MainInfo>
				{isMe ? (
					<div>
						<InfoLabel>내가 좋아하는 사용자</InfoLabel>
						<LikedUsers>
							{myUserList.map((e, i) => (
								<Link key={i} to={`/users/${e.id}`}>
									<User>
										<div className='img'></div>
										<span>{e.nickname}</span>
									</User>
								</Link>
							))}
						</LikedUsers>
					</div>
				) : (
					''
				)}
				<InfoLabel>기술 스택</InfoLabel>
				<StacksContainer>
					{skillStackTags.map((e, i) => (
						<Stack key={i} src={`/icons/stacks/${e}.png`} alt={`${e}`} />
					))}
				</StacksContainer>
				<InfoLabel>연락처</InfoLabel>
				<a href={'mailto:' + email}>{email}</a>
				<InfoLabel>포트폴리오</InfoLabel>
				<a href={'http://' + portfolio}>{portfolio}</a>
				<InfoLabel>한 줄 소개</InfoLabel>
				<p>{introduction}</p>
			</MainInfo>
			{isMe ? (
				<ButtonContainer>
					<Link to='/users/edit'>
						<StyledButton>정보 수정</StyledButton>
					</Link>
					<StyledButton onClick={deleteHandler}>회원 탈퇴</StyledButton>
				</ButtonContainer>
			) : (
				''
			)}
		</UserInfoContainer>
	);
};

const UserInfoContainer = styled.div`
	max-width: 1300px;
	margin: auto;
	padding: 50px 30px;
`;

const BasicInfo = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	width: 150px;
	margin: auto;
	margin-bottom: 20px;
`;

const UserImg = styled.div`
	width: 150px;
	height: 150px;
	background-color: ${({ theme }) => theme.colors.grey3};
	border-radius: 50%;
`;

const RoundButton = styled.button`
	width: 40px;
	height: 40px;
	border-radius: 50%;
	border: 1px solid ${({ theme }) => theme.colors.grey3};
	background-color: #fff;
	margin-left: auto;
	font-size: 20px;
	padding-top: 5px;
	transform: translateY(-100%);
	cursor: pointer;
	:hover {
		border: 1px solid ${({ theme }) => theme.colors.grey4};
	}
`;

const MiniBox = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
	gap: 5px;
	padding: 10px;
	margin-bottom: 10px;
	width: 100%;
	border: 1px solid ${({ theme }) => theme.colors.grey2};
`;

const MainInfo = styled.div`
	padding-bottom: 30px;
	> a {
		text-decoration: underline;
	}
`;

const InfoLabel = styled.div`
	font-size: 20px;
	font-weight: 700;
	color: ${({ theme }) => theme.colors.grey5};
	padding: 40px 0 30px 0;
`;

const LikedUsers = styled.div`
	display: flex;
	gap: 20px;
`;

const User = styled.div`
	display: flex;
	flex-wrap: wrap;
	align-items: center;
	gap: 5px;
	> .img {
		width: 20px;
		height: 20px;
		border-radius: 50%;
		background-color: ${({ theme }) => theme.colors.grey2};
	}
`;

const StacksContainer = styled.div`
	display: flex;
	flex-wrap: wrap;
	gap: 15px;
`;

const Stack = styled.img`
	border: 1px solid ${({ theme }) => theme.colors.grey2};
	border-radius: 50%;
	width: 60px;
	height: 60px;
	padding: 5px;
	margin-right: 20px;
`;

const ButtonContainer = styled.div`
	display: flex;
	justify-content: flex-end;
	align-items: center;
	gap: 15px;
	margin-top: 30px;
	button {
		padding: 10px;
	}
`;

const StyledButton = styled.button`
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

export default UserInfo;
