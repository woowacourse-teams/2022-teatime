import dayjs from 'dayjs';

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
  month: string; // MM
  year: string; // YY
}

interface Schedule {
  id: number;
  dateTime: Date;
  isPossible: boolean;
}

type ScheduleMap = Record<number, Schedule[]>;

export { Coach, MonthYear, Schedule, ScheduleMap };
