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

interface Schedule {
  id: number;
  dateTime: string;
  isPossible?: boolean;
  isSelected?: boolean;
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

interface DaySchedule {
  day: number;
  schedules: Schedule[];
}

type ScheduleMap = Record<number, Schedule[]>;

type CrewListMap = Record<string, Crew[]>;

export {
  UserInfo,
  UserRole,
  Crew,
  Coach,
  MonthYear,
  Schedule,
  DaySchedule,
  Reservation,
  ScheduleMap,
  CrewListMap,
  Sheets,
  History,
  HistoryList,
};
