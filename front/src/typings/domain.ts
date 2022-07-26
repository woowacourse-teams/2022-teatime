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
  month: string;
  year: string;
}

interface Schedule {
  id: number;
  dateTime: string;
  isPossible?: boolean;
  isSelected?: boolean;
}

type DaySchedule = { day: number; schedules: Schedule[] };

type ScheduleMap = Record<number, Schedule[]>;

export { Coach, MonthYear, Schedule, DaySchedule, ScheduleMap };
