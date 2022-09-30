import { useState } from 'react';
import { useCookies } from 'react-cookie';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { apis } from '../apis/axios';
import { ReactComponent as Heart } from '../assets/icons/heart.svg';
import { ReactComponent as HeartFill } from '../assets/icons/heart-fill.svg';
import { useQuery, useMutation, useQueryClient } from 'react-query';

const UserInfo = () => {
	const [cookies, setCookies, removeCookie] = useCookies();
	const token = cookies.user;
	const myId = +cookies.userId;
	const param = useParams();
	const userId = param.userId;
	const queryClient = useQueryClient();

	const navigate = useNavigate();

	const [userData, setUserData] = useState({
		id: '',
		nickname: '',
		like: 0,
		myUserList: [],
		skillStackTags: [],
		introduction: '',
		email: '',
		portfolio: '',
		likedByMe: false,
	});

	const {
		nickname,
		liked,
		myUserList,
		skillStackTags,
		introduction,
		email,
		portfolio,
		likedByMe,
	} = userData;

	const location = useLocation().pathname;
	const isMe = location === '/users' ? true : false;

	// 나의 마이페이지인지, 남의 마이페이지인지 구분하여 api 호출
	const { data, isLoading, isError, error } = useQuery(
		'userInfo',
		isMe ? () => apis.getUsers(token) : () => apis.getOtherUsers(token, userId),
		{
			onSuccess: (data) => {
				console.log(data);
				setUserData(data);
			},
			onError: (error) => {
				console.log(error);
			},
		}
	);

	// 좋아요
	const postLikeMutation = useMutation(() => apis.postLike(token, userId), {
		onMutate: (variable) => {
			console.log('onMutate', variable);
		},
		onSuccess: (data, variable, context) => {
			queryClient.invalidateQueries('userInfo');
		},
		onError: (error) => {
			alert('자신에게 좋아요 할 수 없습니다.');
		},
		onSettled: () => {
			console.log('settled');
		},
	});

	// 좋아요 취소
	const deleteLikeMutation = useMutation(() => apis.deleteLike(token, userId), {
		onMutate: (variable) => {
			console.log('onMutate', variable);
		},
		onSuccess: (data, variable, context) => {
			queryClient.invalidateQueries('userInfo');
		},
		onError: (error) => {
			alert('에러났다');
		},
		onSettled: () => {
			console.log('settled');
		},
	});

	// 회원 탈퇴
	const deleteHandler = () => {
		if (window.confirm('정말 탈퇴하시겠습니까?')) {
			apis
				.deleteUser(token)
				.then(() => {
					removeCookie('user', { path: '/' });
					removeCookie('userId', { path: '/' });
				})
				.then(navigate('/'));
		} else {
			return;
		}
	};

	return (
		<UserInfoContainer>
			<BasicInfo>
				<UserImg></UserImg>
				<RoundButton>
					{likedByMe ? (
						<HeartFill
							fill='tomato'
							onClick={() => deleteLikeMutation.mutate()}
						/>
					) : (
						<Heart onClick={() => postLikeMutation.mutate()} />
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
