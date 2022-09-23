import { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useNavigate, useParams } from 'react-router-dom';

const OAuth = () => {
  const navigate = useNavigate();
  const token = window.location.search.slice(16);
  const isUser = window.location.search.slice(8, 9);
  const [cookies, setCookie] = useCookies(['user']);

  useEffect(() => {
    if (isUser === '1') {
      setCookie('user', token);
      navigate('/');
    } else {
      setCookie('user', token);
      navigate('/join');
    }
  });

  return <div>login</div>;
};

export default OAuth;
