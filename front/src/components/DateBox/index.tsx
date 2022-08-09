import dayjs from 'dayjs';

import { Schedule } from '@typings/domain';
import * as S from './styles';

interface DateBoxProps {
  date?: number;
  daySchedule?: Schedule[];
  onClick?: () => void;
  selectedDay?: number | null;
  currentDay?: string;
  isCoach?: boolean;
  isWeekend?: boolean;
  isPastDay?: boolean;
}

const DateBox = ({
  date,
  daySchedule = [],
  onClick,
  selectedDay,
  currentDay,
  isCoach,
  isWeekend,
  isPastDay,
}: DateBoxProps) => {
  const isSelected = (daySchedule.length > 0 || isCoach) && selectedDay === date;
  const isToday = currentDay === dayjs().format('YYYY-MM-DD');
  const hasSchedule =
    daySchedule.filter((time) => time.isPossible === true || (time.isPossible === false && isCoach))
      .length > 0;

  return (
    <S.DateContainer
      hasSchedule={hasSchedule}
      hasDate={!!date}
      isSelected={!!isSelected}
      isCoach={isCoach}
      isWeekend={isWeekend}
      isPastDay={isPastDay}
      onClick={onClick}
    >
      {date}
      {isToday && <S.TodayIndicator />}
    </S.DateContainer>
  );
};

export default DateBox;
