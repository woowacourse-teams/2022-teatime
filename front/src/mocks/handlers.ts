import { rest } from 'msw';
import type {
  DaySchedule as DayScheduleResponse,
  Coach as CoachResponse,
  Crew as CrewResponse,
} from '@typings/domain';
import { BASE_URL } from '@api/index';
import { coachList, crewList, scheduleList } from './data';

const handlers = [
  rest.get<CoachResponse[]>(`${BASE_URL}/api/coaches`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(coachList));
  }),

  rest.get<DayScheduleResponse[]>(`${BASE_URL}/api/coaches/:id/schedules`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(scheduleList));
  }),

  rest.post(`${BASE_URL}/api/coaches/:id/schedules/:scheduleId`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.put(`${BASE_URL}/api/coaches/:id/schedules`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.get<CrewResponse[]>(`${BASE_URL}/api/crews`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(crewList));
  }),
];

export default handlers;
