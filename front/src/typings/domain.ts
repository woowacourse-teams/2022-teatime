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

type Status = 'pending' | 'approved' | 'completed';

type CrewList = { status: Status; crews: Crew[] };

type CrewListMap = Record<Status, Crew[]>;

type DaySchedule = { day: number; schedules: Schedule[] };

type ScheduleMap = Record<number, Schedule[]>;

export { Crew, Coach, MonthYear, Schedule, DaySchedule, ScheduleMap, CrewList, CrewListMap };
