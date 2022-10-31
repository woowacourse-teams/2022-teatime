import { rest } from 'msw';

import { BASE_URL } from '@api/index';
import { crews, coachHistories } from '../dummy/coachData';

type DaySchedule = {
  date: string;
  schedules: string[];
};

const coachHandler = [
  // 예약 목록 조회
  rest.get(`${BASE_URL}/api/v2/coaches/me/reservations`, (req, res, ctx) => {
    const token = req.headers.get('Authorization');
    if (token === undefined) {
      return res(ctx.status(401));
    }

    return res(ctx.status(200), ctx.json(crews));
  }),

  // 히스토리 조회
  rest.get(`${BASE_URL}/api/v2/coaches/me/history`, (req, res, ctx) => {
    const token = req.headers.get('Authorization');
    if (token === undefined) {
      return res(ctx.status(401));
    }

    return res(ctx.status(200), ctx.json(coachHistories));
  }),

  // 스케줄 등록
  rest.put<DaySchedule[]>(`${BASE_URL}/api/v2/coaches/me/schedules`, (req, res, ctx) => {
    const [{ date, schedules }] = req.body;

    if (date === null || schedules === null) {
      return res(ctx.status(400));
    }

    return res(ctx.status(204));
  }),

  // 예약 승인 및 거절
  rest.post<{ isApproved: boolean }>(
    `${BASE_URL}/api/v2/reservations/:reservationId`,
    (req, res, ctx) => {
      const { reservationId } = req.params;
      const { isApproved } = req.body;

      if (reservationId === null || isApproved === null) {
        return res(ctx.status(400));
      }

      if (isApproved) {
        console.log('승인하기');
      } else {
        console.log('거절하기');
      }

      return res(ctx.status(204));
    }
  ),

  // 예약 완료
  rest.put(`${BASE_URL}/api/v2/reservations/:reservationId`, (req, res, ctx) => {
    const { reservationId } = req.params;

    if (reservationId === null) {
      return res(ctx.status(400));
    }

    return res(ctx.status(204));
  }),

  // 예약 취소
  rest.delete(`${BASE_URL}/api/v2/reservations/:reservationId`, (req, res, ctx) => {
    const { reservationId } = req.params;

    if (reservationId === null) {
      return res(ctx.status(400));
    }

    return res(ctx.status(204));
  }),
];

export default coachHandler;
