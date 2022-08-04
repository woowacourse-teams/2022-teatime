import { rest } from 'msw';

import { BASE_URL } from '@api/index';
import type {
  DaySchedule as DayScheduleResponse,
  Coach as CoachResponse,
  InterviewInfo as InterviewInfoResponse,
  CrewListMap,
} from '@typings/domain';
import { coachList, crewList, scheduleList, interviewInfo } from './data';

const handlers = [
  rest.get<CoachResponse[]>(`${BASE_URL}/api/coaches`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(coachList));
  }),
  rest.get<DayScheduleResponse[]>(`${BASE_URL}/api/coaches/:id/schedules`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(scheduleList));
  }),
  rest.get<InterviewInfoResponse>(`${BASE_URL}/api/crews/me/reservations/1`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(interviewInfo));
  }),

  rest.post(`${BASE_URL}/api/coaches/:id/schedules/:scheduleId`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.put(`${BASE_URL}/api/coaches/:id/schedules`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.get<CrewListMap[]>(`${BASE_URL}/api/coaches/me/reservations`, (req, res, ctx) => {
    console.log('req', req.headers);
    return res(ctx.status(200), ctx.json(crewList));
  }),

  rest.post(`${BASE_URL}/api/reservations/:reservationId`, (req, res, ctx) => {
    return res(ctx.status(201));
  }),
];

export default handlers;
