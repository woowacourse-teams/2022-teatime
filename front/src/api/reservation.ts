import api from '@api/index';

const createReservation = (scheduleId: number) => api.post(`/api/v2/reservations`, { scheduleId });

const confirmReservation = (reservationId: number) =>
  api.post(`/api/v2/reservations/${reservationId}`, { isApproved: true });

const rejectReservation = (reservationId: number) =>
  api.post(`/api/v2/reservations/${reservationId}`, { isApproved: false });

const deleteReservation = (reservationId: number) =>
  api.delete(`/api/v2/reservations/${reservationId}`);

export { createReservation, confirmReservation, rejectReservation, deleteReservation };
