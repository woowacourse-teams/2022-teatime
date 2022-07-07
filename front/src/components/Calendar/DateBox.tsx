import { Schedule } from '@typings/domain';
import axios from 'axios';
import dayjs from 'dayjs';
import { Date, DateContainer, ScheduleBar } from './styles';

interface DateBoxProps {
  date?: number;
  schedules?: Schedule[];
}

const DateBox = ({ date, schedules = [] }: DateBoxProps) => {
  const handleSubmitCrewName = async (scheduleId: number) => {
    alert('면담신청이 예약되었습니다.😆');
    await axios.post(`/coaches/0/schedules/${scheduleId}`, { crewName: '안' });
  };

  return (
    <DateContainer>
      <Date>{date}</Date>
      {schedules.map((schedule) => {
        return (
          <ScheduleBar key={schedule.id} onClick={() => handleSubmitCrewName(schedule.id)}>
            {dayjs(schedule.dateTime).format('H:mm')} - {dayjs(schedule.dateTime).format('H:mm')}
          </ScheduleBar>
        );
      })}
    </DateContainer>
  );
};

export default DateBox;
