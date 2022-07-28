import type {
  DaySchedule as DayScheduleResponse,
  Coach as CoachResponse,
  Crew as CrewResponse,
} from '@typings/domain';
import { rest } from 'msw';
import { coachList, crewList, scheduleList } from './data';

const handlers = [
  rest.get<CoachResponse[]>('/api/coaches', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(coachList));
  }),

  rest.get<DayScheduleResponse[]>('/api/coaches/:id/schedules', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(scheduleList));
  }),

  rest.post('/api/coaches/:id/schedules/:scheduleId', (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.put('/api/coaches/:id/schedules', (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.get<CrewResponse[]>('/api/crews', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(crewList));
  }),
];

export default handlers;
