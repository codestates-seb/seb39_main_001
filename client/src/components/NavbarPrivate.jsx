import { useState, useEffect, useRef } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useQuery } from 'react-query';
import styled from 'styled-components';
import { useCookies } from 'react-cookie';
import { apis } from '../apis/axios';
import useDetectClose from '../hooks/useDetectClose';
import ProfileDropdown from './ProfileDropdown';
import NotiDropdown from './NotiDropdown';
import {
	NavbarContainer,
	NavbarSubContainer,
	Logo,
	NavButtons,
	DarkThemeBtn,
	LightThemeBtn,
} from './NavbarPublic';
import { ReactComponent as NotificationIcon } from '../assets/icons/notification.svg';
import { ReactComponent as Sun } from '../assets/icons/sun.svg';
import { ReactComponent as Moon } from '../assets/icons/moon.svg';

const NavbarPrivate = ({ removeCookie, theme, toggleTheme }) => {
	const [cookies] = useCookies();
	const token = cookies.user;
	const [imageSrc, setImageSrc] = useState('/icons/img/user-default.png');
	const param = useParams();

	// dropdown 외부 클릭 감지
	const dropdownRef = useRef(null);
	const notificationRef = useRef(null);
	const [isOpen, setIsOpen] = useDetectClose(dropdownRef, false);
	const [notificationOpen, setNotificationOpen] = useDetectClose(
		notificationRef,
		false
	);

	// 알림 아이콘 클릭 시 get 요청
	// const { data, error, isSuccess, isError, isLoading } = useQuery(
	// 	'notifications',
	// 	() => apis.getRecentNotifications(token),
	//   {
	//     onSuccess: (data) => {
	//       console.log(data);
	//     },
	//     onError: (error) => {
	//       console.log(error);
	//     }
	//   }
	// );

	const notificationHandler = () => {
		setNotificationOpen(!notificationOpen);
	};

	// 글 한번에 여러번 쓰기 이스터에그
	// const handleIteration = (iter) => {
	//   for (let i = 0; i < iter; i++) {
	//     const data = {
	//       title: `${i}번째 글`,
	//       backend: 2,
	//       frontend: 2,
	//       designer: 0,
	//       etc: 0,
	//       people: 0,
	//       contact: 'chicken@milk.tea',
	//       dueDate: '2022-09-14',
	//       startingDate: '2022-10-05',
	//       period: '3',
	//       onOffline: 'online',
	//       content: `자동으로 작성된 ${i}번째 글입니다.`,
	//       type: 'PROJECT',
	//       tagList: ['java', 'react'],
	//     };
	//     apis.postBoard(token, data);
	//   }
	// };

	useEffect(() => {
		apis.getUsers(token).then((data) => setImageSrc(data.img));
	}, [token, param]);

	return (
		<NavbarContainer>
			<NavbarSubContainer>
				<Logo />
				<NavButtons>
					{theme === 'light' ? (
						<DarkThemeBtn className='theme-btn' onClick={toggleTheme}>
							<Moon fill='#ffea00' />
						</DarkThemeBtn>
					) : (
						<LightThemeBtn className='theme-btn' onClick={toggleTheme}>
							<Sun fill='#00e676' />
						</LightThemeBtn>
					)}
					<Notification>
						<NotificationIcon
							width='18px'
							height='18px'
							// onClick={() => {
							//   handleIteration(10);
							// }}
							ref={notificationRef}
							onClick={notificationHandler}
						/>
						{/* {notificationOpen ? <NotiDropdown data={data} /> : ''} */}
						{notificationOpen ? <NotiDropdown /> : ''}
					</Notification>
					<Profile
						ref={dropdownRef}
						onClick={() => {
							setIsOpen(!isOpen);
						}}
					>
						<img src={imageSrc} alt='profile' />
						{isOpen ? <ProfileDropdown removeCookie={removeCookie} /> : ''}
					</Profile>
				</NavButtons>
			</NavbarSubContainer>
		</NavbarContainer>
	);
};

const Notification = styled.div`
	position: relative;
	font-size: 18px;
	color: ${({ theme }) => theme.colors.grey4};
	display: flex;
	align-items: center;
	cursor: pointer;
	:hover {
		color: ${({ theme }) => theme.colors.purple1};
	}
`;

const Profile = styled.div`
	position: relative;
	width: 32px;
	height: 32px;
	border: 1px solid ${({ theme }) => theme.colors.grey3};
	border-radius: 50px;
	cursor: pointer;
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
	:hover {
		border: 1px solid ${({ theme }) => theme.colors.purple1};
	}
`;

export default NavbarPrivate;
