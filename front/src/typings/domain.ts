import dayjs from 'dayjs';

interface Crew {
  id: number;
  name: string;
  dateTime: string;
  image: string;
}
interface Coach {
  id: number;
  name: string;
  description: string;
  image: string;
}

interface MonthYear {
  startDate: dayjs.Dayjs;
  firstDOW: number; // 0 === Sunday
  lastDate: number;
  month: string;
  year: string;
}

interface Schedule {
  id: number;
  dateTime: string;
  isPossible?: boolean;
  isSelected?: boolean;
}

type CrewList = { status: string; crews: Crew[] };

type CrewListMap = Record<string, Crew[]>;

type DaySchedule = { day: number; schedules: Schedule[] };

export { Crew, Coach, MonthYear, Schedule, DaySchedule, CrewList, CrewListMap };
