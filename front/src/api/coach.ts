import api from '@api/index';
import { Coach, DaySchedule, CrewListMap, CoachHistory } from '@typings/domain';

const getCoaches = () => api.get<Coach[]>(`/api/v2/coaches`);

const getCoachSchedulesByMe = (year: string, month: string) =>
  api.get<DaySchedule[]>(`/api/v2/coaches/me/schedules?year=${year}&month=${month}`);

const getCoachSchedulesByCrew = (coachId: string, year: string, month: string) =>
  api.get<DaySchedule[]>(`/api/v2/coaches/${coachId}/schedules?year=${year}&month=${month}`);

const editCoachSchedule = (date: string, schedules: string[]) =>
  api.put(`/api/v2/coaches/me/schedules`, { date, schedules });

const getCoachReservations = () => api.get<CrewListMap>('/api/v2/coaches/me/reservations');

const getCoachHistories = () => api.get<CoachHistory[]>('/api/v2/coaches/me/history');

export {
  getCoaches,
  getCoachSchedulesByMe,
  getCoachSchedulesByCrew,
  getCoachReservations,
  editCoachSchedule,
  getCoachHistories,
};
