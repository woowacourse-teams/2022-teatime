import dayjs from 'dayjs';

interface Crew {
  reservationId: number;
  crewId: number;
  crewName: string;
  crewImage: string;
  dateTime: string;
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

interface ReservationInfo {
  dateTime: string;
  coachName: string;
  coachImage: string;
  sheets: Sheets[];
}

type Sheets = { questionNumber: number; questionContent: string; answerContent: string };

type DaySchedule = { day: number; schedules: Schedule[] };

type CrewListMap = Record<string, Crew[]>;

export { Crew, Coach, MonthYear, Schedule, DaySchedule, ReservationInfo, CrewListMap, Sheets };
