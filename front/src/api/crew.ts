import api from '@api/index';
import { CrewHistory, HistoryList, Reservation, Sheets } from '@typings/domain';

const getCrewHistoriesByMe = () => api.get<CrewHistory[]>('/api/v2/crews/me/reservations');

const getCrewHistoriesByCoach = (crewId: string) =>
  api.get<HistoryList[]>(`/api/v2/crews/${crewId}/reservations`);

const getCrewReservationByMe = (reservationId: string) =>
  api.get<Reservation>(`/api/v2/crews/me/reservations/${reservationId}`);

const getCrewCanceledReservation = (reservationId: string) =>
  api.get<Reservation>(`/api/v2/crews/me/canceled-reservations/${reservationId}`);

const getCrewReservationByCoach = (crewId: number, reservationId: string) =>
  api.get<Reservation>(`/api/v2/crews/${crewId}/reservations/${reservationId}`);

const editCrewReservation = (reservationId: string, isSubmitted: boolean, sheets: Sheets[]) =>
  api.put(`/api/v2/crews/me/reservations/${reservationId}`, {
    status: isSubmitted ? 'SUBMITTED' : 'WRITING',
    sheets,
  });

const editCrewNickName = (name: string) => api.put(`/api/v2/crews/me/profile`, { name });

export {
  getCrewHistoriesByMe,
  getCrewHistoriesByCoach,
  getCrewReservationByMe,
  getCrewCanceledReservation,
  getCrewReservationByCoach,
  editCrewReservation,
  editCrewNickName,
};