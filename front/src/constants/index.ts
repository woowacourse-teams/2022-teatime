const ROUTES = {
  HOME: '/',
  CREW: '/crew',
  COACH: '/coach',
  SCHEDULE: '/schedule',
  RESERVATION: '/reservation',
  CREW_SHEET: '/crew-sheet',
  COACH_SHEET: '/coach-sheet',
  HISTORY_SHEET: '/history-sheet',
  CREW_HISTORY: '/crew-history',
  CERTIFICATION: '/certification',
};

const DAY_OF_WEEKS = ['일', '월', '화', '수', '목', '금', '토'];

const CALENDAR_DATE_LENGTH = {
  MIN: 35,
  MAX: 42,
};

const DAY_NUMBER = {
  SATURDAY: 6,
  SUNDAY: 0,
};

const HOUR_MILLISECONDS = 60 * 60 * 1000;

export { ROUTES, DAY_OF_WEEKS, CALENDAR_DATE_LENGTH, DAY_NUMBER, HOUR_MILLISECONDS };
