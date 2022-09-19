import { rest } from 'msw';
import { users } from './db';
import { boards } from './db';


export const handlers = [
  rest.get('/todos', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json('hi'));
  }),

  // 로그인
  rest.post('/login', (req, res, ctx) => {
    return res(ctx.status(201));
  }),

  //회원가입
  rest.post('/join', (req, res, ctx) => {
    users.push(req.body);
    return res(ctx.status(200));
  }),

  //게시물 목록 불러오기
  rest.get('/boards?'),

];
