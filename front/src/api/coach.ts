import { api } from '@api/index';
import { Coach, DaySchedule, CrewListMap, CoachHistory, CoachData } from '@typings/domain';

const getCoaches = () => api.get<Coach[]>(`/api/v2/coaches`);

const getCoachProfile = () => api.get<CoachData>('/api/v2/coaches/me/profile');

const getCoachSchedulesByMe = (year: string, month: string) =>
  api.get<DaySchedule[]>(`/api/v2/coaches/me/schedules?year=${year}&month=${month}`);

const getCoachSchedulesByCrew = (coachId: string, year: string, month: string) =>
  api.get<DaySchedule[]>(`/api/v2/coaches/${coachId}/schedules?year=${year}&month=${month}`);

const getCoachReservations = () => api.get<CrewListMap>('/api/v2/coaches/me/reservations');

const getCoachHistories = () => api.get<CoachHistory[]>('/api/v2/coaches/me/history');

const editCoachProfile = (data: { name: string; description: string }) =>
  api.put(`/api/v2/coaches/me/profile`, data);

// Todo: 변경된 다중선택 api
// const editCoachSchedule = (data: { date: string; schedules: string[] }[]) =>
//   api.put(`/api/v2/coaches/me/schedules`, { data });

const editCoachSchedule = (date: string, schedules: string[]) =>
  api.put(`/api/v2/coaches/me/schedules`, { date, schedules });

export {
  getCoaches,
  getCoachProfile,
  getCoachSchedulesByMe,
  getCoachSchedulesByCrew,
  getCoachReservations,
  getCoachHistories,
  editCoachSchedule,
  editCoachProfile,
};
