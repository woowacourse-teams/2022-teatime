import { useState } from 'react';
import axios from 'axios';
import dayjs from 'dayjs';
import { Schedule } from '@typings/domain';
import { Date, DateContainer } from './styles';

interface DateBoxProps {
  date?: number;
  monthSchedule?: Schedule[];
}

const DateBox = ({ date, monthSchedule = [] }: DateBoxProps) => {
  console.log('monthSchedule', monthSchedule);
  return (
    <DateContainer hasSchedule={!!monthSchedule.length}>
      <Date>{date}</Date>
    </DateContainer>
  );
};

export default DateBox;

{
  /* <ScheduleBar key={schedule.id}>
{dayjs(schedule.dateTime).format('H:mm')} - {dayjs(schedule.dateTime).format('H:mm')}
</ScheduleBar> */
}
