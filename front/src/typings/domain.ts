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

export { Coach, MonthYear };
