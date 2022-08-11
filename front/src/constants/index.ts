const ROUTES = {
  HOME: '/',
  CREW: '/crew',
  COACH: '/coach',
  SCHEDULE: '/schedule',
  RESERVATION: '/reservation',
  SHEET: '/sheet',
  HISTORY: '/history',
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
