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
  ERROR: '/error',
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

const BOARD = {
  BEFORE_APPROVED: 'beforeApproved',
  APPROVED: 'approved',
  IN_PROGRESS: 'inProgress',
} as const;

const LOCAL_DB = {
  USER: 'user',
  SELECTED_BOARD: 'selectedBoard',
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
const SKELETON_CARD_LENGTH = 8;

const ERROR_MESSAGE = {
  INVALID_TOKEN: '유효하지 않은 토큰입니다.',
  WRONG_TOKEN: '토큰이 잘못되었습니다.',
  NOT_EXIST_TOKEN: '헤더에 토큰이 존재하지 않습니다.',
  EXCEED_TEXT_LENGTH: '더이상 작성할 수 없습니다. 글자수를 조정해주세요.',
  FAIL_NICKNAME_CHANGE: '닉네임 변경이 실패하였습니다. 다시 시도해주세요.',
  FAIL_LOGIN: '에러가 발생했습니다. 다시 로그인해주세요.',
  FAIL_APPROVE: '승인이 실패하였습니다. 다시 시도해주세요.',
  FAIL_COMPLETE_RESERVATION: '면담을 완료할 수 없습니다. 다시 시도해주세요.',
  FAIL_REJECT_RESERVATION: '면담을 거절할 수 없습니다. 다시 시도해주세요.',
  FAIL_CANCEL_RESERVATION: '면담을 취소할 수 없습니다. 다시 시도해주세요.',
  FAIL_EDIT_PROFILE: '프로필 수정이 실패하였습니다. 다시 시도해주세요.',
  FAIL_REQUEST: '요청이 실패하였습니다. 다시 시도해주세요.',
  FAIL_SAVE: '저장이 실패하였습니다. 다시 시도해주세요.',
  FAIL_SUBMIT: '제출이 실패하였습니다. 다시 시도해주세요.',
  FAIL_ENROLL_SCHEDULE: '스케줄 등록에 실패하였습니다. 다시 시도해주세요.',
};

export {
  ROUTES,
  DAY_OF_WEEKS,
  CALENDAR_DATE_LENGTH,
  SKELETON_CARD_LENGTH,
  DAY_NUMBER,
  BOARD,
  LOCAL_DB,
  CACHE,
  HOUR_MILLISECONDS,
  MAX_LENGTH,
  QUESTIONS_LENGTH,
  ERROR_MESSAGE,
};
