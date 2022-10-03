import { Link } from 'react-router-dom';
import { useState } from 'react';
import styled from 'styled-components';
import LoginModal from './LoginModal';
import { ReactComponent as Sun } from '../assets/icons/sun.svg';
import { ReactComponent as Moon } from '../assets/icons/moon.svg';

const NavbarPublic = ({ theme, toggleTheme }) => {
	const [showModal, setShowModal] = useState(false);

	const openModal = () => {
		setShowModal(!showModal);
	};

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
					<LoginButton onClick={openModal}>로그인</LoginButton>
				</NavButtons>
				<LoginModal showModal={showModal} setShowModal={setShowModal} />
			</NavbarSubContainer>
		</NavbarContainer>
	);
};

export const NavButtons = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
	width: 230px;
`;

export const NavbarContainer = styled.div`
	display: flex;
	justify-content: center;
	padding: 10px 0px;
	box-shadow: 0 0 5px 3px rgba(0, 0, 0, 0.1);
`;

export const NavbarSubContainer = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: space-between;
	align-items: center;
	width: 100%;
	max-width: 1300px;
	padding: 0px 30px;
`;

export const Logo = () => {
	return (
		<LogoLink to='/'>
			JU:SE
			<p>Junior to Senior</p>
		</LogoLink>
	);
};

const LogoLink = styled(Link)`
	font-size: 30px;
	font-weight: 900;
	color: ${({ theme }) => theme.colors.purple1};
	> p {
		font-size: 10px;
	}
`;

export const LoginButton = styled.button`
	width: 80px;
	padding: 10px;
	background-color: ${({ theme }) => theme.background};
	border: 1px solid ${({ theme }) => theme.colors.purple1};
	color: ${({ theme }) => theme.colors.purple1};
	border-radius: 4px;
	cursor: pointer;
	:hover {
		background: ${({ theme }) => theme.colors.purple1};
		color: #ffffff;
	}
`;

export const DarkThemeBtn = styled.button`
	right: 120px;
	display: flex;
	flex-direction: row;
	justify-content: space-around;
	align-items: center;
	width: 120px;
	height: 50px;
	border: none;
	border-radius: 999px;
	background: #ffffff;
	box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
	z-index: 9999;
	cursor: pointer;
	:hover {
		background-color: #292929;
		color: #fafafa;
	}
`;

export const LightThemeBtn = styled.button`
	right: 120px;
	display: flex;
	flex-direction: row;
	justify-content: space-around;
	align-items: center;
	width: 120px;
	height: 50px;
	border: none;
	border-radius: 999px;
	background: #3a3b3c;
	color: #fafafa;
	box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
	z-index: 9999;
	cursor: pointer;
	:hover {
		background-color: #ffffff;
		color: #333533;
	}
`;

export default NavbarPublic;
