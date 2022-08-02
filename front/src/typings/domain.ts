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

interface InterviewInfo {
  dateTime: string;
  coachName: string;
  coachImage: string;
  sheets: Sheets[];
}

type Sheets = { questionNumber: number; questionContent: string; answerContent: string };

type DaySchedule = { day: number; schedules: Schedule[] };

type CrewList = { status: string; crews: Crew[] };

type CrewListMap = Record<string, Crew[]>;

export { Crew, Coach, MonthYear, Schedule, DaySchedule, InterviewInfo, CrewList, CrewListMap };
