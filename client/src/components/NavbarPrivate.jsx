import { useState, useEffect, useRef } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';
import {
	NavbarContainer,
	NavbarSubContainer,
	Logo,
	NavButtons,
	DarkThemeBtn,
	LightThemeBtn,
} from './NavbarPublic';
import { ReactComponent as NotificationIcon } from '../assets/icons/notification.svg';
import { ReactComponent as UserIcon } from '../assets/icons/user.svg';
import { ReactComponent as BookmarkIcon } from '../assets/icons/bookmark.svg';
import { ReactComponent as LogoutIcon } from '../assets/icons/logout.svg';
import { useCookies } from 'react-cookie';
import { apis } from '../apis/axios';
import useDetectClose from '../hooks/useDetectClose';
import { ReactComponent as Sun } from '../assets/icons/sun.svg';
import { ReactComponent as Moon } from '../assets/icons/moon.svg';

const NavbarPrivate = ({ removeCookie, theme, toggleTheme }) => {
	const [cookies] = useCookies();
	const token = cookies.user;
	const [imageSrc, setImageSrc] = useState('/icons/img/user-default.png');
	const param = useParams();
	const navigate = useNavigate();
	// dropdown 외부 클릭 감지
	const dropdownRef = useRef(null);
	const [isOpen, setIsOpen] = useDetectClose(dropdownRef, false);

	const handleLogout = () => {
		removeCookie('user', { path: '/' });
		removeCookie('userId', { path: '/' });
		navigate('/');
	};

	// 글 한번에 여러번 쓰기 이스터에그
	const handleIteration = (iter) => {
		for (let i = 0; i < iter; i++) {
			const data = {
				title: `${i}번째 글`,
				backend: 2,
				frontend: 2,
				designer: 0,
				etc: 0,
				people: 0,
				contact: 'chicken@milk.tea',
				dueDate: '2022-09-14',
				startingDate: '2022-10-05',
				period: '3',
				onOffline: 'online',
				content: `자동으로 작성된 ${i}번째 글입니다.`,
				type: 'PROJECT',
				tagList: ['java', 'react'],
			};
			apis.postBoard(token, data);
		}
	};

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
							<Moon width='13' fill='#ffea00' />
							<p>다크 모드</p>
						</DarkThemeBtn>
					) : (
						<LightThemeBtn className='theme-btn' onClick={toggleTheme}>
							<Sun width='13' fill='#00e676' />
							<p>라이트 모드</p>
						</LightThemeBtn>
					)}
					<Notification>
						<NotificationIcon
							width='18px'
							height='18px'
							onClick={() => {
								handleIteration(10);
							}}
						/>
					</Notification>
					<Profile
						ref={dropdownRef}
						onClick={() => {
							setIsOpen(!isOpen);
						}}
					>
						<img src={imageSrc} alt='profile' />
						{isOpen ? (
							<DropdownNav>
								<DropdownLink to='/users'>
									<UserIcon />
									<p>마이페이지</p>
								</DropdownLink>
								<DropdownLink to='/users/myjuse'>
									<BookmarkIcon />
									<p>나의 JUSE</p>
								</DropdownLink>
								<DropdownLink to='/' onClick={handleLogout}>
									<LogoutIcon />
									<p>로그아웃</p>
								</DropdownLink>
							</DropdownNav>
						) : (
							''
						)}
					</Profile>
				</NavButtons>
			</NavbarSubContainer>
		</NavbarContainer>
	);
};

const Notification = styled.div`
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

const DropdownNav = styled.nav`
	background: ${({ theme }) => theme.background};
	border-radius: 8px;
	position: absolute;
	top: 60px;
	right: 0;
	width: 140px;
	box-shadow: 0 1px 8px ${({ theme }) => theme.colors.grey2};
	z-index: 999;
`;

const DropdownLink = styled(Link)`
	display: flex;
	justify-content: space-between;
	border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
	padding: 15px 20px;
	font-size: 14px;
	color: ${({ theme }) => theme.colors.black1};
	> i {
		margin-right: 10px;
	}
	:hover {
		color: ${({ theme }) => theme.colors.purple1};
	}
`;

export default NavbarPrivate;
