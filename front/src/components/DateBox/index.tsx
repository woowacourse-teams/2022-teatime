import { getCurrentFullDate } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';
import * as S from './styles';

interface DateBoxProps {
  date?: number;
  daySchedule?: TimeSchedule[];
  onClick?: () => void;
  selectedDay?: number | null;
  currentDay?: Date;
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
  const isToday = currentDay?.getTime() === getCurrentFullDate().getTime();
  const hasSchedule =
    daySchedule.filter((time) => time.isPossible === true || time.isPossible === false).length > 0;

  const impossibleSchdules = daySchedule.filter((time) => time.isPossible === false && !isCoach);

  const isAllImpossibleSchedules =
    impossibleSchdules.length > 0 && impossibleSchdules.length === daySchedule.length;

  return (
    <S.DateContainer
      hasSchedule={hasSchedule}
      isAllImpossibleSchedules={isAllImpossibleSchedules}
      hasDate={!!date}
      isSelected={!!isSelected}
      isCoach={isCoach}
      // isWeekend={isWeekend}
      isPastDay={isPastDay}
      onClick={onClick}
    >
      {date}
      {isToday && <S.TodayIndicator />}
    </S.DateContainer>
  );
};

export default DateBox;
