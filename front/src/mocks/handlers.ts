import type {
  DaySchedule as DayScheduleResponse,
  Coach as CoachResponse,
  InterviewInfo as InterviewInfoResponse,
} from '@typings/domain';
import { rest } from 'msw';
import { coachList, interviewInfo, scheduleList } from './data';
import { BASE_URL } from '../api/index';

const handlers = [
  rest.get<CoachResponse[]>(`${BASE_URL}/api/coaches`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(coachList));
  }),

  rest.get<DayScheduleResponse[]>(`/api/coaches/:id/schedules`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(scheduleList));
  }),
  rest.get<InterviewInfoResponse>(`${BASE_URL}/api/crews/me/reservations/1`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(interviewInfo));
  }),

  rest.post('/api/coaches/:id/schedules/:scheduleId', (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.put('/api/coaches/:id/schedules', (req, res, ctx) => {
    return res(ctx.status(200));
  }),
];

export default handlers;
