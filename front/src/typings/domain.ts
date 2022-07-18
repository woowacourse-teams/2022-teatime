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
  dateTime: Date | string;
  isPossible: boolean;
}

type DaySchedule = { day: number; schedules: Schedule[] };

type ScheduleMap = Record<number, Schedule[]>;

export { Coach, MonthYear, Schedule, DaySchedule, ScheduleMap };
