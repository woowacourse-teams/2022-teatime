import type { DaySchedule as DayScheduleResponse, Coach as CoachResponse } from '@typings/domain';
import { rest } from 'msw';
import { coachList, scheduleList } from './data';

const handlers = [
  rest.get<CoachResponse[]>('/coaches', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(coachList));
  }),

  rest.get<DayScheduleResponse[]>('/coaches/:id/schedules', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(scheduleList));
  }),

  rest.post('/coaches/:id/schedules/:scheduleId', (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.put('/coaches/:id/schedules', (req, res, ctx) => {
    return res(ctx.status(200));
  }),
];

export default handlers;
