import dayjs from 'dayjs';

type Status = 'pending' | 'approve' | 'comment';

interface Crew {
  id: number;
  name: string;
  dateTime: string;
  status: Status;
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

type DaySchedule = { day: number; schedules: Schedule[] };

type ScheduleMap = Record<number, Schedule[]>;

export { Crew, Coach, MonthYear, Schedule, DaySchedule, ScheduleMap };
