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
  COACH_HISTORY: '/coach-history',
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

const LOCAL_DB = {
  USER: 'user',
};

const CACHE = {
  KEY: 'coaches',
  TIME: 600000,
};

const HOUR_MILLISECONDS = 60 * 60 * 1000;

const SHEET_MAX_LENGTH = 2000;

export {
  ROUTES,
  DAY_OF_WEEKS,
  CALENDAR_DATE_LENGTH,
  DAY_NUMBER,
  LOCAL_DB,
  CACHE,
  HOUR_MILLISECONDS,
  SHEET_MAX_LENGTH,
};
