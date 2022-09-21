import { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useNavigate, useParams } from 'react-router-dom';

const OAuth = () => {
  const navigate = useNavigate();
  const token = window.location.search.slice(7);
  const [cookies, setCookie] = useCookies(['user']);

  useEffect(() => {
    setCookie('user', token);
    navigate('/');
  });

  return <div>login</div>;
};

export default OAuth;
