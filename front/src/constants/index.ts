const ROUTES = {
  HOME: '/',
  CREW: '/crew',
  COACH: '/coach',
  SCHEDULE: '/schedule',
  QUESTION: '/question',
  RESERVATION: '/reservation',
  CREW_SHEET: '/crew-sheet',
  COACH_SHEET: '/coach-sheet',
  HISTORY_SHEET: '/history-sheet',
  CREW_HISTORY: '/crew-history',
  COACH_HISTORY: '/coach-history',
  COACH_PROFILE: '/coach-profile',
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
  TIME: 300000,
};

const HOUR_MILLISECONDS = 60 * 60 * 1000;

const MAX_LENGTH = {
  SHEET: 2000,
  NAME: 20,
  DESCRIPTION: 60,
  QUESTION: 50,
};

const QUESTIONS_LENGTH = 3;

const ERROR_MESSAGE = {
  INVALID_TOKEN: '유효하지 않은 토큰입니다.',
  WRONG_TOKEN: '토큰이 잘못되었습니다.',
  NOT_EXIST_TOKEN: '헤더에 토큰이 존재하지 않습니다.',
};

export {
  ROUTES,
  DAY_OF_WEEKS,
  CALENDAR_DATE_LENGTH,
  DAY_NUMBER,
  LOCAL_DB,
  CACHE,
  HOUR_MILLISECONDS,
  MAX_LENGTH,
  QUESTIONS_LENGTH,
  ERROR_MESSAGE,
};
