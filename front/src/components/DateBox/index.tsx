import { Schedule } from '@typings/domain';
import { DateContainer, TodayIndicator } from './styles';

interface DateBoxProps {
  date?: number;
  monthSchedule?: Schedule[];
  onClick?: () => void;
  selectedDay?: number | null;
  today?: string;
  isCoach?: boolean;
  isWeekend?: boolean;
}

const DateBox = ({
  date,
  monthSchedule = [],
  onClick,
  selectedDay,
  today,
  isCoach,
  isWeekend,
}: DateBoxProps) => {
  const isSelected = (monthSchedule.length > 0 || isCoach) && selectedDay === date;
  const isToday = Number(today) === date;

  return (
    <DateContainer
      hasSchedule={!!monthSchedule.length}
      hasDate={!!date}
      isSelected={!!isSelected}
      isCoach={isCoach}
      isWeekend={isWeekend}
      onClick={onClick}
    >
      {date}
      {isToday && <TodayIndicator />}
    </DateContainer>
  );
};

export default DateBox;
