import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { NavbarContainer, NavbarSubContainer, Logo } from './NavbarPublic';
import { ReactComponent as NotificationIcon } from '../assets/icons/notification.svg';
import { ReactComponent as UserIcon } from '../assets/icons/user.svg';
import { ReactComponent as BookmarkIcon } from '../assets/icons/bookmark.svg';
import { ReactComponent as LogoutIcon } from '../assets/icons/logout.svg';
import { useCookies } from 'react-cookie';
import { apis } from '../apis/axios';

const NavbarPrivate = ({ removeCookie }) => {
	const [dropdownOpen, setDropdownOpen] = useState(false);
	const navigate = useNavigate();

	const dropdownClickHandler = () => {
		setDropdownOpen(!dropdownOpen);
	};

	const handleLogout = () => {
		removeCookie('user', { path: '/' });
		removeCookie('userId', { path: '/' });
		navigate('/');
	};

	// 글 한번에 여러번 쓰기 이스터에그
	const [cookies] = useCookies();
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
			apis.postBoard(cookies.user, data);
		}
	};

	return (
		<NavbarContainer>
			<NavbarSubContainer>
				<Logo />
				<NavButtons>
					<Notification>
						<NotificationIcon
							width='18px'
							height='18px'
							onClick={() => {
								handleIteration(10);
							}}
						/>
					</Notification>
					<Profile onClick={dropdownClickHandler}>
						{dropdownOpen ? (
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
						) : null}
					</Profile>
				</NavButtons>
			</NavbarSubContainer>
		</NavbarContainer>
	);
};

const NavButtons = styled.div`
	display: flex;
	justify-content: space-between;
	width: 80px;
`;

const Notification = styled.div`
	font-size: 18px;
	color: ${({ theme }) => theme.colors.grey4};
	display: flex;
	align-items: center;
	cursor: pointer;
	:hover {
		color: ${({ theme }) => theme.colors.black1};
	}
`;

const Profile = styled.div`
	position: relative;
	width: 32px;
	height: 32px;
	border: 1px solid ${({ theme }) => theme.colors.grey3};
	border-radius: 50px;
	cursor: pointer;
`;

const DropdownNav = styled.nav`
	background: #ffffff;
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
	color: ${({ theme }) => theme.colors.grey4};
	> i {
		margin-right: 10px;
	}
	:hover {
		background: ${({ theme }) => theme.colors.grey1};
	}
`;

export default NavbarPrivate;
