import { Schedule } from '@typings/domain';
import { DateContainer, TodayIndicator } from './styles';

interface DateBoxProps {
  date?: number;
  monthSchedule?: Schedule[];
  onClick?: () => void;
  selectedDay?: number | null;
  today?: string;
}

const DateBox = ({ date, monthSchedule = [], onClick, selectedDay, today }: DateBoxProps) => {
  const isSelected = monthSchedule.length > 0 && selectedDay === date;
  const isToday = Number(today) === date;

  return (
    <DateContainer hasSchedule={!!monthSchedule.length} isSelected={!!isSelected} onClick={onClick}>
      {date}
      {isToday && <TodayIndicator />}
    </DateContainer>
  );
};

export default DateBox;
