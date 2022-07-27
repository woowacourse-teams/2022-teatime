import dayjs from 'dayjs';
import { Schedule } from '@typings/domain';
import * as S from './styles';

interface DateBoxProps {
  date?: number;
  daySchedule?: Schedule[];
  onClick?: () => void;
  selectedDay?: number | null;
  today?: string;
  isCoach?: boolean;
  isWeekend?: boolean;
}

const DateBox = ({
  date,
  daySchedule = [],
  onClick,
  selectedDay,
  today,
  isCoach,
  isWeekend,
}: DateBoxProps) => {
  const isSelected = (daySchedule.length > 0 || isCoach) && selectedDay === date;
  const isToday = today === dayjs().format('YYYY-MM-DD');
  const hasSchedule = daySchedule.filter((time) => time.isPossible === true).length > 0;

  return (
    <S.DateContainer
      hasSchedule={hasSchedule}
      hasDate={!!date}
      isSelected={!!isSelected}
      isCoach={isCoach}
      isWeekend={isWeekend}
      onClick={onClick}
    >
      {date}
      {isToday && <S.TodayIndicator />}
    </S.DateContainer>
  );
};

export default DateBox;
