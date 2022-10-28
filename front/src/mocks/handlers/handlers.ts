import { rest } from 'msw';

import { BASE_URL } from '@api/index';
import type { DaySchedule, Coach, Reservation, CrewListMap } from '@typings/domain';
import {
  coachList,
  crewList,
  scheduleList,
  interviewInfo,
  history,
  sheet,
  historySheet,
} from '../dummy/data';

const handlers = [
  rest.get<Coach[]>(`${BASE_URL}/api/v2/coaches`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(coachList));
  }),

  rest.get<DaySchedule[]>(`${BASE_URL}/api/v2/coaches/:id/schedules`, (req, res, ctx) => {
    const { teamId } = req.params;
    return res(ctx.status(200), ctx.json(scheduleList));
  }),

  rest.get<Reservation>(`${BASE_URL}/api/v2/crews/me/reservations/1`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(interviewInfo));
  }),

  rest.get<CrewListMap[]>(`${BASE_URL}/api/v2/coaches/me/reservations`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(crewList));
  }),
  rest.get(`${BASE_URL}/api/v2/crews/me/reservations`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(history));
  }),

  rest.get(`${BASE_URL}/api/v2/crews/me/reservations/1`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(sheet));
  }),

  rest.get(`${BASE_URL}/api/crews/:id/reservations`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(historySheet));
  }),

  rest.post(`${BASE_URL}/api/v2/coaches/:id/schedules/:scheduleId`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),
  rest.post(`${BASE_URL}/api/v2/reservations/:reservationId`, (req, res, ctx) => {
    return res(ctx.status(201));
  }),

  rest.put(`${BASE_URL}/api/v2/coaches/:id/schedules`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.delete(`${BASE_URL}/api/v2/reservations/:reservationId`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.post(`${BASE_URL}/api/v2/reservations`, (req, res, ctx) => {
    return res(ctx.status(201), ctx.set('Location', '/1'));
  }),
];

export default handlers;
