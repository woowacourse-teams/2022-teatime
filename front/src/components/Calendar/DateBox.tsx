import { Dispatch, SetStateAction, useEffect, useState } from 'react';
import axios from 'axios';
import dayjs from 'dayjs';
import { Schedule } from '@typings/domain';
import { Date, DateContainer } from './styles';

interface DateBoxProps {
  date?: number;
  monthSchedule?: Schedule[];
  onClick?: () => void;
  selectedDay?: number | null;
}

const DateBox = ({ date, monthSchedule = [], onClick, selectedDay }: DateBoxProps) => {
  const isSelected = monthSchedule.length > 0 && selectedDay === date;
  console.log(!!isSelected);

  return (
    <DateContainer hasSchedule={!!monthSchedule.length} onClick={onClick} isSelected={!!isSelected}>
      {date}
    </DateContainer>
  );
};

export default DateBox;

{
  /* <ScheduleBar key={schedule.id}>
{dayjs(schedule.dateTime).format('H:mm')} - {dayjs(schedule.dateTime).format('H:mm')}
</ScheduleBar> */
}
