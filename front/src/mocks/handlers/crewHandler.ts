import { DefaultBodyType, rest } from 'msw';

import { BASE_URL } from '@api/index';
import { DaySchedule } from '@typings/domain';
import { coaches, schedules, reservationInfo, crewHistories } from '../dummy/crewData';

const crewHandler = [
  // 코치 목록 조회
  rest.get(`${BASE_URL}/api/v2/coaches`, (req, res, ctx) => {
    const token = req.headers.get('Authorization');
    if (token === undefined) {
      return res(ctx.status(401));
    }

    return res(ctx.status(200), ctx.json(coaches));
  }),

  // 캘린더에서 스케줄 조회
  rest.get<DaySchedule>(`${BASE_URL}/api/v2/coaches/:coachId/schedules`, (req, res, ctx) => {
    const year = req.url.searchParams.get('year');
    const month = req.url.searchParams.get('month');

    if (year === null || month === null) {
      return res(ctx.status(400));
    }

    const currentMonth = String(new Date().getMonth() + 1).padStart(2, '0');
    const filteredSchedule = schedules(year, month).filter((schedule) => {
      if (month === currentMonth) {
        return schedule.day >= new Date().getDate();
      }
      return schedule;
    });

    return res(ctx.status(200), ctx.json(filteredSchedule));
  }),

  // 히스토리 조회
  rest.get(`${BASE_URL}/api/v2/crews/me/reservations`, (req, res, ctx) => {
    const token = req.headers.get('Authorization');
    if (token === undefined) {
      return res(ctx.status(401));
    }

    return res(ctx.status(200), ctx.json(crewHistories));
  }),

  // 시트 조회
  rest.get(`${BASE_URL}/api/v2/crews/me/reservations/:reservationId`, (req, res, ctx) => {
    const { reservationId } = req.params;

    return res(ctx.status(200), ctx.json(reservationInfo[Number(reservationId)]));
  }),

  // 예약하기
  rest.post<DefaultBodyType & { scheduleId?: number }>(
    `${BASE_URL}/api/v2/reservations`,
    (req, res, ctx) => {
      const { scheduleId } = req.body;

      if (scheduleId === null) {
        return res(ctx.status(400));
      }

      return res(ctx.status(201), ctx.set('Location', '/0'));
    }
  ),
];

export default crewHandler;
