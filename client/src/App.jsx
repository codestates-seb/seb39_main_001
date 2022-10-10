import { Routes, Route, useParams } from 'react-router-dom';
import NavbarPrivate from './components/NavbarPrivate';
import NavbarPublic from './components/NavbarPublic';
import Home from './pages/Home';
import Join from './pages/Join';
import NewMeeting from './pages/NewMeeting';
import Board from './pages/Board';
import OAuth from './pages/OAuth';
import UserInfo from './pages/UserInfo';
import EditUser from './pages/EditUser';
import EditNewMeeting from './pages/EditNewMeeting';
import { useCookies } from 'react-cookie';
import MyJuse from './pages/MyJuse';
import styled, { ThemeProvider } from 'styled-components';
import GlobalStyles from './assets/styles/GlobalStyle';
import theme from './assets/styles/Theme';
import { lightTheme, darkTheme } from './assets/styles/Theme';
import { useState } from 'react';
import { ReactComponent as Sun } from './assets/icons/sun.svg';
import { ReactComponent as Moon } from './assets/icons/moon.svg';

// 프록시: https://cors-jwy.herokuapp.com/
function App() {
	const [cookies, setCookie, removeCookie] = useCookies();
	const [theme, setTheme] = useState('light');
	const toggleTheme = () => {
		theme === 'light' ? setTheme('dark') : setTheme('light');
	};

	return (
		<ThemeProvider theme={theme === 'light' ? lightTheme : darkTheme}>
			<GlobalStyles />
			<>
				{cookies.user ? (
					<NavbarPrivate removeCookie={removeCookie} />
				) : (
					<NavbarPublic />
				)}
				<Routes>
					<Route path='/' element={<Home />} />
					<Route path='/join' element={<Join />} />
					<Route path='/boards/:boardId' element={<Board />} />
					<Route path='/oauth2/redirect' element={<OAuth />} />
					{/* 같은 컴포넌트인데 path가 다를 때는 unique key를 지정*/}
					<Route path='/users/' element={<UserInfo key='myPage' />} />
					<Route
						path='/users/:userId'
						element={<UserInfo key='othersPage' />}
					/>
					<Route path='/users/edit' element={<EditUser />} />
					<Route path='/boards' element={<NewMeeting />} />
					<Route path='/users/myjuse' element={<MyJuse />} />
					<Route path='/boards/edit' element={<EditNewMeeting />} />
				</Routes>
			</>
			{/* <ThemeBtn onClick={toggleTheme}> */}
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
		</ThemeProvider>
	);
}

const DarkThemeBtn = styled.button`
	position: fixed;
	bottom: 50px;
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

const LightThemeBtn = styled.button`
	position: fixed;
	bottom: 50px;
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

export default App;
