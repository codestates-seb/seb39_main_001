import axios from 'axios';

export const joinSubmit = (data) => {
  axios
    .post('/join', data)
    .then((res) => console.log(res))
    .catch((err) => console.log(err));
};
