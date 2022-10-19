import { rest } from 'msw';

import { BASE_URL } from '@api/index';
import type {
  DaySchedule as DayScheduleResponse,
  Coach as CoachResponse,
  Reservation as ReservationResponse,
  CrewListMap,
} from '@typings/domain';
import { coachList, crewList, scheduleList, interviewInfo, historySheet } from './data';

const handlers = [
  rest.get<CoachResponse[]>(`${BASE_URL}/api/coaches`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(coachList));
  }),
  rest.get<DayScheduleResponse[]>(`${BASE_URL}/api/coaches/:id/schedules`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(scheduleList));
  }),
  rest.get<ReservationResponse>(`${BASE_URL}/api/crews/me/reservations/1`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(interviewInfo));
  }),
  rest.get<CrewListMap[]>(`${BASE_URL}/api/coaches/me/reservations`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(crewList));
  }),
  rest.get(`${BASE_URL}/api/crews/:id/reservations`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(historySheet));
  }),

  rest.post(`${BASE_URL}/api/coaches/:id/schedules/:scheduleId`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),
  rest.post(`${BASE_URL}/api/reservations/:reservationId`, (req, res, ctx) => {
    return res(ctx.status(201));
  }),

  rest.put(`${BASE_URL}/api/coaches/:id/schedules`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.delete(`${BASE_URL}/api/reservations/:reservationId`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),
];

export default handlers;
