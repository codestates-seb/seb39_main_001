import axios from 'axios';

export const apis = {
  // 회원가입
  postJoin: async (token, data) => {
    await axios
      .post(`http://juse.iptime.org:8080/users/join`, data, {
        headers: {
          Auth: token,
        },
      })
      .catch((err) => console.log(err));
  },
  getNickname: async (nickname) => {
    return await axios
      .get(`http://juse.iptime.org:8080/users/nicknames?q=${nickname}`)
      .then((res) => res.data.data)
      .catch((err) => console.log(err));
  },

  // 마이페이지
  getUsers: async (token) => {
    return await axios
      .get(`http://juse.iptime.org:8080/users`, {
        headers: {
          Auth: token,
        },
      })
      .then((res) => res.data.data)
      .catch((err) => console.log(err));
  },
  getOtherUsers: async (token, userId) => {
    return await axios
      .get(`http://juse.iptime.org:8080/users/${userId}`, {
        headers: {
          Auth: token,
        },
      })
      .then((res) => res.data.data)
      .catch((err) => console.log(err));
  },
  deleteUser: async (token, userId) => {
    await axios
      .delete(`http://juse.iptime.org:8080/users`, {
        headers: {
          Auth: token,
        },
      })
      .catch((err) => console.log(err));
  },

  // 유저 정보 수정
  patchUser: async (token, user) => {
    await axios
      .patch(`http://juse.iptime.org:8080/users`, user, {
        headers: {
          Auth: token,
        },
      })
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
  },

  // 게시물 상세
  getBoardDetail: async (token, boardId) => {
    return await axios
      .get(`http://juse.iptime.org:8080/boards/${boardId}`, {
        headers: {
          Auth: token,
        },
      })
      .then((res) => res.data.data)
      .catch((err) => console.log(err));
  },

  // 질문 게시 수정 삭제
  postQuestion: async (token, data, boardId) => {
    await axios
      .post(`http://juse.iptime.org:8080/questions/${boardId}`, data, {
        headers: {
          Auth: token,
        },
      })
      .then((res) => console.log(res.data.data));
  },
  patchQuestion: async (token, data, questionId) => {
    await axios
      .patch(`http://juse.iptime.org:8080/questions/${questionId}`, data, {
        headers: {
          Auth: token,
        },
      })
      .catch((err) => console.log(err));
  },
  deleteQuestion: async (token, questionId) => {
    await axios
      .delete(`http://juse.iptime.org:8080/questions/${questionId}`, {
        headers: {
          Auth: token,
        },
      })
      .then((res) => console.log(res));
  },

  // 답변 게시 수정 삭제
  postAnswer: async (token, data, questionId) => {
    await axios
      .post(`http://juse.iptime.org:8080/answers/${questionId}`, data, {
        headers: {
          Auth: token,
        },
      })
      .then((res) => console.log(res.data.data));
  },
  patchAnswer: async (token, data, answerId) => {
    await axios
      .patch(`http://juse.iptime.org:8080/answers/${answerId}`, data, {
        headers: {
          Auth: token,
        },
      })
      .catch((err) => console.log(err));
  },
  deleteAnswer: async (token) => {
    await axios
      .delete(`http://juse.iptime.org:8080/answers/1`, {
        headers: {
          Auth: token,
        },
      })
      .then((res) => console.log(res));
  },

  ///////////////////////////////////

  boardPost: async (token) => {
    await axios
      .post(
        `http://juse.iptime.org:8080/boards`,
        {
          title: '첫 번째 게시글',
          backend: 5,
          frontend: 2,
          designer: 1,
          etc: 2,
          people: 10,
          contact: 'contact',
          dueDate: '2022-09-14',
          startingDate: '2022-10-05',
          period: '3',
          onOffline: 'online',
          content: 'content1',
          type: 'PROJECT',
          tagList: ['java', 'react', 'figma'],
        },
        {
          headers: {
            Auth: token,
          },
        }
      )
      .then((res) => console.log(res.data.data));
  },
  postApply: async (token) => {
    await axios
      .post(
        `http://juse.iptime.org:8080/applications/1?position=backend`,
        {},
        {
          headers: {
            Auth: token,
          },
        }
      )
      .then((res) => console.log(res.data.data));
  },
  accept: async (token) => {
    await axios
      .patch(
        `http://juse.iptime.org:8080/applications/2`,
        {},
        {
          headers: {
            Auth: token,
          },
        }
      )
      .then((res) => console.log(res.data.data));
  },
  bookmark: async (token) => {
    await axios
      .post(
        `http://juse.iptime.org:8080/bookmarks/1`,
        {},
        {
          headers: {
            Auth: token,
          },
        }
      )
      .then((res) => console.log(res.data.data));
  },
  myjuse: async (token) => {
    await axios
      .get(`http://juse.iptime.org:8080/users/myjuse`, {
        headers: {
          Auth: token,
        },
      })
      .then((res) => console.log(res.data.data));
  },
  like: async (token) => {
    await axios
      .post(
        `http://juse.iptime.org:8080/likes/1`,
        {},
        {
          headers: {
            Auth: token,
          },
        }
      )
      .then((res) => console.log(res.data.data));
  },
  dislike: async (token) => {
    await axios
      .delete(`http://juse.iptime.org:8080/likes/1`, {
        headers: {
          Auth: token,
        },
      })
      .then((res) => console.log(res.data.data));
  },

  bookDelete: async (token) => {
    await axios
      .delete(`http://juse.iptime.org:8080/bookmarks/1`, {
        headers: {
          Auth: token,
        },
      })
      .then((res) => console.log(res));
  },
  update: async (token) => {
    await axios
      .patch(
        `http://juse.iptime.org:8080/boards/1`,
        {
          contact: 'updated contact',
          dueDate: '2022-09-14',
          startingDate: '2022-10-05',
          period: '3',
          onOffline: 'online',
          content: '수정된 게시글',
          title: '또 수정',
          type: 'PROJECT',
          tagList: ['figma'],
        },
        {
          headers: {
            Auth: token,
          },
        }
      )
      .then((res) => console.log(res));
  },
  myUpdate: async (token) => {
    await axios
      .patch(
        `http://juse.iptime.org:8080/users`,
        {
          introduction: '용우정입니다',
          portfolio: '포폴업뎃',
          nickname: '용정우',
          skillStackTags: ['react', 'java'],
        },
        {
          headers: {
            Auth: token,
          },
        }
      )
      .then((res) => console.log(res));
  },
};
