export const boards = {
  data: [
    {
      id: 1,
      type: 'PROJECT',
      status: 'OPENING',
      startingDate: '2022-10-05',
      title: '첫 번째 게시글입니다.',
      tagList: ['vue', 'react', 'nestjs', 'docker', 'aws'],
      views: 1233,
      period: 3,
      bookmarks: 4,
      user: {
        id: 1,
        introduction: 'user1',
        email: 'user1',
        portfolio: 'github.com/user1',
        nickname: '치킨밀크티',
      },
    },
    {
      id: 2,
      type: 'STUDY',
      status: 'OPENING',
      startingDate: '2022-10-05',
      title:
        '스터디 같이 하실 분 구합니다. 두 줄을 넘어가는 제목 세 줄을 넘어가는 제목 세 줄을 넘어가는 제모오오오옥',
      tagList: ['typescript', 'javascript'],
      views: 4,
      period: 'short',
      bookmarks: 4,
      user: {
        id: 1,
        introduction: 'user1',
        email: 'user1',
        portfolio: 'github.com/user1',
        nickname: 'user mouth',
      },
    },
    {
      id: 3,
      type: 'PROJECT',
      status: 'CLOSED',
      startingDate: '2022-09-15',
      title: '모집이 완료된 글 입니다. 그리고 두 줄짜리 제목',
      tagList: ['java', 'react'],
      views: 4,
      period: 'long',
      bookmarks: 4,
      user: {
        id: 1,
        introduction: 'user1',
        email: 'user1',
        portfolio: 'github.com/user1',
        nickname: 'user mouth',
      },
    },
    {
      id: 4,
      type: 'PROJECT',
      status: 'CLOSED',
      startingDate: '2022-09-15',
      title: '모집이 완료된 글 입니다. 그리고 두 줄짜리 제목',
      tagList: ['java', 'react'],
      views: 4,
      period: 'long',
      bookmarks: 4,
      user: {
        id: 1,
        introduction: 'user1',
        email: 'user1',
        portfolio: 'github.com/user1',
        nickname: 'user mouth',
      },
    },
    {
      id: 5,
      type: 'PROJECT',
      status: 'CLOSED',
      startingDate: '2022-09-15',
      title: '모집이 완료된 글 입니다. 그리고 두 줄짜리 제목',
      tagList: ['java', 'react'],
      views: 4,
      period: 'long',
      bookmarks: 4,
      user: {
        id: 1,
        introduction: 'user1',
        email: 'user1',
        portfolio: 'github.com/user1',
        nickname: 'user mouth',
      },
    },
    {
      id: 6,
      type: 'PROJECT',
      status: 'CLOSED',
      startingDate: '2022-09-15',
      title: '모집이 완료된 글 입니다. 그리고 두 줄짜리 제목',
      tagList: ['java', 'react'],
      views: 4,
      period: 'long',
      bookmarks: 4,
      user: {
        id: 1,
        introduction: 'user1',
        email: 'user1',
        portfolio: 'github.com/user1',
        nickname: 'user mouth',
      },
    },
  ],
  pagination: {
    page: 1,
    size: 5,
    totalElements: 10,
    totalPages: 2,
    filterOptions: {
      type: null,
      tag: null,
      period: null,
    },
  },
};

export const board1 = {
  data: {
    id: 1,
    title: 'board 1',
    backend: 5,
    curBackend: 0,
    frontend: 2,
    curFrontend: 0,
    designer: 1,
    curDesigner: 0,
    etc: 2,
    curEtc: 0,
    people: 10,
    contact: 'chickenmilktea@gmail.com',
    dueDate: '2022-09-14',
    startingDate: '2022-10-05',
    period: '3',
    onOffline: 'online',
    content: '<h1>HELLO WORLD</h1>',
    views: 0,
    bookmarks: 0,
    tagList: ['java', 'react'],
    applicationList: [],
    questionList: [
      {
        id: 1,
        content: '정신이 있습니까?',
        user: {
          id: 1,
          nickname: '질문자',
          img: null,
        },
        auth: true,
        answer: {
          id: 1,
          user: {
            id: 1,
            nickname: 'angelolsen',
            img: null,
          },
          content: '없습니다',
          createdAt: '2022-09-21T16:17:07.067981',
          modifiedAt: '2022-09-21T16:17:25.052494',
        },
        createdAt: '2022-09-21T16:17:07.067981',
        modifiedAt: '2022-09-21T16:17:25.052494',
      },
      {
        id: 1,
        content: '답변이 안달린 질문',
        auth: false,
        user: {
          id: 2,
          nickname: '다른질문자',
          img: null,
        },
        answer: null,
        createdAt: '2022-09-21T16:17:07.067981',
        modifiedAt: '2022-09-21T16:17:25.052494',
      },
    ],
    type: 'PROJECT',
    status: 'OPENING',
    user: {
      id: 1,
      profileImage: null,
      introduction: 'shutupandkissmeholdmetight',
      email: 'hsbang.thom@gmail.com',
      portfolio: 'holdmetight',
      nickname: ' angelolsen',
      skillStackTags: [],
    },
    createdAt: '2022-09-21T16:17:01.506336',
    modifiedAt: '2022-09-21T16:17:01.506336',
    auth: true,
    bookmarked: true,
  },
};

export const user = {
  data: {
    id: 1,
    introduction: '안녕하세요 프론트엔드 개발 입문한지 5개월된 코린이입니다.',
    email: 'chickenmilktea@gmail.com',
    portfolio: 'github.com/user1',
    nickname: '치킨밀크티',
    skillStackTags: ['java', 'react', 'mongodb'],
    myUserList: [
      {
        id: 10,
        introduction: null,
        email: null,
        portfolio: null,
        nickname: '젠틀맨',
      },
      {
        id: 999,
        introduction: null,
        email: null,
        portfolio: null,
        nickname: '달건이',
      },
    ],
    liked: 10,
  },
};

export const myjuse = {
  data: {
    id: 1,
    myBookmarkList: [
      {
        id: 1,
        type: 'PROJECT',
        status: 'OPENING',
        startingDate: '2022-10-05',
        title: 'stub1',
        tagList: ['java', 'react'],
        views: 4,
        bookmarks: 4,
        user: {
          id: 1,
          introduction: 'user1',
          email: 'user1',
          portfolio: 'github.com/user1',
          nickname: 'user mouth',
        },
      },
      {
        id: 2,
        type: 'STUDY',
        status: 'OPENING',
        startingDate: null,
        title: 'board2',
        tagList: [],
        views: 100,
        bookmarks: 2,
        user: {
          id: 1,
          introduction: null,
          email: null,
          portfolio: null,
          nickname: 'user2',
        },
      },
      {
        id: 4,
        type: 'STUDY',
        status: 'OPENING',
        startingDate: null,
        title: 'spitz',
        tagList: [],
        views: 24,
        bookmarks: 10,
        user: {
          id: 5,
          introduction: null,
          email: null,
          portfolio: null,
          nickname: '용남이형!',
        },
      },
    ],
    myParticipationList: [
      {
        id: 3,
        type: 'PROJECT',
        status: 'OPENING',
        startingDate: null,
        title: 'aws project',
        tagList: [],
        views: 15,
        bookmarks: 5,
        user: {
          id: 5,
          introduction: null,
          email: null,
          portfolio: null,
          nickname: '민수씨!',
        },
      },
    ],
    myBoards: null,
    myApplicationList: [
      {
        id: 4,
        type: 'STUDY',
        status: 'OPENING',
        startingDate: null,
        title: 'spitz',
        tagList: [],
        views: 24,
        bookmarks: 10,
        user: {
          id: 5,
          introduction: null,
          email: null,
          portfolio: null,
          nickname: '용남이형!',
        },
      },
    ],
  },
};
