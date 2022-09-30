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

// 프록시: https://cors-jwy.herokuapp.com/
function App() {
	const [cookies, setCookie, removeCookie] = useCookies(['user']);

	return (
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
				<Route path='/users/:userId' element={<UserInfo key='othersPage' />} />
				<Route path='/users/edit' element={<EditUser />} />
				<Route path='/boards' element={<NewMeeting />} />
				<Route path='/users/myjuse' element={<MyJuse />} />
				<Route path='/boards/edit' element={<EditNewMeeting />} />
			</Routes>
		</>
	);
}

export default App;
