import { Schedule } from '@typings/domain';
import dayjs from 'dayjs';
import { DateContainer, ScheduleBar } from './styles';

interface DateBoxProps {
  date?: number;
  schedules?: Schedule[];
}

const DateBox = ({ date, schedules = [] }: DateBoxProps) => {
  return (
    <DateContainer>
      <div>{date}</div>
      {schedules.map((schedule) => {
        return (
          <ScheduleBar key={schedule.id}>
            {dayjs(schedule.dateTime).format('H:MM')} ~ {dayjs(schedule.dateTime).format('H:MM')}
          </ScheduleBar>
        );
      })}
    </DateContainer>
  );
};

export default DateBox;
