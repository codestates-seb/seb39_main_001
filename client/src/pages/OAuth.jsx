import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

const OAuth = () => {
  const token = window.location.search.slice(7);
  console.log(token);

  return <div>login</div>;
};

export default OAuth;
