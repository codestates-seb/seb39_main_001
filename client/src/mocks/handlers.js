import { rest } from 'msw';

export const handlers = [
  rest.get('/todos', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json('hi'));
  }),

  // 로그인
  rest.post('/login', (req, res, ctx) => {
    return res(ctx.status(201));
  }),

  //게시물 목록 불러오기
  rest.get('/boards?'),
];
