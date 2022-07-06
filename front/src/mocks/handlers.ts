import type { ScheduleMap as ScheduleMapResponse } from '@typings/domain';
import { rest } from 'msw';
import { coaches, schedules } from './data';

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
  rest.get<ScheduleMapResponse>(`/coaches/:id/schedules`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(schedules));
  }),
];

export default handlers;
