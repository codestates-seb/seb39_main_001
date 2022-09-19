import axios from 'axios';

export const joinSubmit = (data) => {
  axios
    .post('/join', data)
    .then((res) => console.log(res))
    .catch((err) => console.log(err));
};

// 게시글 목록 조회
export const getBoards = () => {
  axios.get('');
};

