import { rest } from 'msw';
import { coaches } from './data';

interface CoachResponse {
  id: number;
  name: string;
  image: string;
  description: string;
}

const handlers = [
  rest.get<CoachResponse>('/coaches', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(coaches));
  }),
];

export default handlers;
