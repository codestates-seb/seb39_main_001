import { Link, useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import styled from 'styled-components';
import { ReactComponent as UserIcon } from '../assets/icons/user.svg';
import { ReactComponent as BookmarkIcon } from '../assets/icons/bookmark.svg';
import { ReactComponent as LogoutIcon } from '../assets/icons/logout.svg';

const ProfileDropdown = ({ removeCookie }) => {
	const [cookies] = useCookies();
	const navigate = useNavigate();

	const handleLogout = () => {
		removeCookie('user', { path: '/' });
		removeCookie('userId', { path: '/' });
		navigate('/');
	};

	return (
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
	);
};

const DropdownNav = styled.nav`
	background: ${({ theme }) => theme.background};
	border-radius: 8px;
	position: absolute;
	top: 50px;
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

export default ProfileDropdown;
