type UserRole = 'CREW' | 'COACH';

type SheetStatus = 'WRITING' | 'SUBMITTED';

type CrewHistoryStatus = 'BEFORE_APPROVED' | 'APPROVED' | 'IN_PROGRESS' | 'DONE' | 'CANCELED';

type CoachHistoryStatus = 'DONE' | 'CANCELED';

interface UserInfo {
  image: string;
  name: string;
  role: UserRole;
  token: string;
}

interface CoachData {
  image: string;
  name: string;
  description: string;
  isPokable: boolean;
}

interface Crew {
  reservationId: number;
  crewId: number;
  crewName: string;
  crewImage: string;
  dateTime: string;
  sheetStatus?: SheetStatus;
}

interface Coach {
  id: number;
  name: string;
  description: string;
  image: string;
  isPossible: boolean;
  isPokable: boolean;
}

interface MonthYear {
  month: string;
  year: string;
  startDate: Date;
  firstDOW: number;
  lastDate: number;
}

interface Reservation {
  dateTime: string;
  coachName: string;
  coachImage: string;
  sheetStatus: SheetStatus;
  reservationStatus: CrewHistoryStatus;
  sheets: Sheets[];
}

interface ReservationByCoach {
  dateTime: string;
  crewName: string;
  crewImage: string;
  sheetStatus: SheetStatus;
  reservationStatus: CrewHistoryStatus;
  sheets: Sheets[];
}

interface CrewHistory {
  reservationId: number;
  coachName: string;
  coachImage: string;
  dateTime: string;
  status: CrewHistoryStatus;
}

interface CoachHistory {
  reservationId: number;
  crewId: number;
  crewName: string;
  crewImage: string;
  dateTime: string;
  status: CoachHistoryStatus;
}

interface HistoryList {
  reservationId: number;
  coachName: string;
  coachImage: string;
  dateTime: string;
  sheets: Sheets[];
}

interface Sheets {
  questionNumber: number;
  questionContent: string;
  answerContent: string | null;
  isRequired?: boolean;
}

interface TimeSchedule {
  id: number;
  dateTime: string;
  isPossible?: boolean;
  isSelected?: boolean;
}

interface DaySchedule {
  day: number;
  schedules: TimeSchedule[];
}

interface ScheduleInfo {
  monthSchedule: MonthScheduleMap;
  daySchedule: TimeSchedule[];
  date: string;
}

interface MultipleTime {
  id: number;
  dateTime: string;
  isSelected: boolean;
}
interface MultipleDaySchedule {
  dates: string[];
  times: MultipleTime[];
}

interface Question {
  questionNumber: number;
  questionContent: string;
  isRequired: boolean;
}

interface Questions {
  questions: Question[];
}

interface AccessToken {
  accessToken: string;
}

type MonthScheduleMap = Record<number, TimeSchedule[]>;

type CrewListMap = Record<string, Crew[]>;

export {
  UserInfo,
  CoachData,
  UserRole,
  Crew,
  Coach,
  MonthYear,
  ScheduleInfo,
  DaySchedule,
  TimeSchedule,
  Reservation,
  MultipleTime,
  MultipleDaySchedule,
  MonthScheduleMap,
  CrewListMap,
  Sheets,
  CrewHistory,
  HistoryList,
  CoachHistory,
  CrewHistoryStatus,
  CoachHistoryStatus,
  ReservationByCoach,
  Question,
  Questions,
  AccessToken,
};
