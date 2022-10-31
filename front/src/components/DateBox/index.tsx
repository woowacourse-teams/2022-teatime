import { getCurrentFullDate } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';
import * as S from './styles';

interface DateBoxProps {
  date: number;
  daySchedule: TimeSchedule[];
  onClick: () => void;
  selectedDay: number | null;
  currentDay: Date;
  isMultipleSelected: boolean;
  isCoach: boolean;
  isPastDay: boolean;
}

const DateBox = ({
  date,
  daySchedule = [],
  onClick,
  selectedDay,
  currentDay,
  isCoach,
  isMultipleSelected,
  isPastDay,
}: Partial<DateBoxProps>) => {
  const isSelected = (daySchedule.length > 0 || isCoach) && selectedDay === date;
  const isToday = currentDay?.getTime() === getCurrentFullDate().getTime();
  const hasSchedule =
    daySchedule.filter((time) => time.isPossible === true || time.isPossible === false).length > 0;

  const impossibleSchdules = daySchedule.filter((time) => time.isPossible === false && !isCoach);
  const isImpossibleDay =
    impossibleSchdules.length > 0 && impossibleSchdules.length === daySchedule.length;

  return (
    <S.DateContainer
      aria-label={isImpossibleDay ? `${date}일 예약 가능한 시간 없음` : `${date}일`}
      tabIndex={hasSchedule ? 0 : -1}
      hasSchedule={hasSchedule}
      hasDate={!!date}
      isSelected={!!isSelected || !!isMultipleSelected}
      isCoach={isCoach}
      isImpossibleDay={isImpossibleDay}
      isPastDay={isPastDay}
      onClick={onClick}
    >
      {date}
      {isToday && <S.TodayIndicator />}
    </S.DateContainer>
  );
};

export default DateBox;
