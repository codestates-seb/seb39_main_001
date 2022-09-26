import { Link } from 'react-router-dom';
import { useState } from 'react';
import styled from 'styled-components';
import LoginModal from './LoginModal';

const NavbarPublic = () => {
	const [showModal, setShowModal] = useState(false);

	const openModal = () => {
		setShowModal(!showModal);
	};

	return (
		<NavbarContainer>
			<NavbarSubContainer>
				<Logo />
				<LoginButton onClick={openModal}>로그인</LoginButton>
				<LoginModal showModal={showModal} setShowModal={setShowModal} />
			</NavbarSubContainer>
		</NavbarContainer>
	);
};

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

const LoginButton = styled.button`
	width: 80px;
	padding: 10px;
	background-color: #ffffff;
	border: 1px solid ${({ theme }) => theme.colors.purple1};
	color: ${({ theme }) => theme.colors.purple1};
	border-radius: 4px;
	cursor: pointer;
	:hover {
		background: ${({ theme }) => theme.colors.purple1};
		color: #ffffff;
	}
`;

export default NavbarPublic;