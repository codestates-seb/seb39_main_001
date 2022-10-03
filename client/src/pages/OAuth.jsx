import { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useNavigate, useParams } from 'react-router-dom';
import { apis } from '../apis/axios';

const OAuth = () => {
  const navigate = useNavigate();
  const token = window.location.search.slice(16);
  const isUser = window.location.search.slice(8, 9);
  const [cookies, setCookie] = useCookies();

  useEffect(() => {
    if (isUser === '1') {
      apis
        .getUsers(token)
        .then((data) => setCookie('userId', data.id, { path: '/' }))
        .then(() => setCookie('user', token, { path: '/' }))
        .then(() => navigate('/'));
    } else {
      navigate('/join', { state: { token } });
    }
  });

  return <div>login</div>;
};

export default OAuth;
