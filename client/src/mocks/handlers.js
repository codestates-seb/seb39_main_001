import { rest } from 'msw';
import { boards } from './db';
import { board1 } from './db';

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

  // 글쓰기
  rest.post('/boards', (req, res, ctx) => {
    boards.data.push(req.json);
    return res(ctx.status(201), ctx.json('성공!'));
  }),

  // 글쓰기 수정
    rest.patch('/board1', (req, res, ctx) => {
    boards.data(req.json);
    return res(ctx.status(201), ctx.json('수정성공!'));
  }),

  // 단일 게시글 불러오기 (board1)
  rest.get('/board1', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(board1));
  })
];

