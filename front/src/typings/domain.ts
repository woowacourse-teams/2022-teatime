type UserRole = 'CREW' | 'COACH';

type SheetStatus = 'WRITING' | 'SUBMITTED';

interface UserInfo {
  image: string;
  name: string;
  role: UserRole;
  token: string;
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
  status: string;
  sheets: Sheets[];
}

interface History {
  reservationId: number;
  coachName: string;
  coachImage: string;
  dateTime: string;
  status: string;
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
  answerContent: string;
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

type MonthScheduleMap = Record<number, TimeSchedule[]>;

type CrewListMap = Record<string, Crew[]>;

export {
  UserInfo,
  UserRole,
  Crew,
  Coach,
  MonthYear,
  ScheduleInfo,
  DaySchedule,
  TimeSchedule,
  Reservation,
  MonthScheduleMap,
  CrewListMap,
  Sheets,
  History,
  HistoryList,
};
