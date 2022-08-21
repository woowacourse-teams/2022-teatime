import DateBox from '@components/DateBox';
import Conditional from '@components/Conditional';
import { DAY_NUMBER, DAY_OF_WEEKS } from '@constants/index';
import { convertToFullDate, getCurrentFullDate } from '@utils/date';
import type { MonthYear, MonthScheduleMap } from '@typings/domain';
import * as S from './styles';

import LeftArrow from '@assets/left-arrow.svg';
import LeftArrowDisabled from '@assets/left-arrow-disabled.svg';
import RightArrow from '@assets/right-arrow.svg';

interface CalendarProps {
  isCoach?: boolean;
  onUpdateMonth: (increment: number) => void;
  onClickDate: (day: number, isWeekend: boolean) => void;
  monthYear: MonthYear;
  dateBoxLength: number;
  selectedDay: number | null;
  monthSchedule: MonthScheduleMap;
}

const Calendar = ({
  isCoach,
  onUpdateMonth,
  onClickDate,
  monthYear,
  dateBoxLength,
  selectedDay,
  monthSchedule,
}: CalendarProps) => {
  const { firstDOW, lastDate, year, month, startDate } = monthYear;
  const currentDate = new Date();

  return (
    <S.CalendarContainer>
      <S.YearMonthContainer>
        <span>
          {year}년 {month}월
        </span>
        <div>
          <Conditional condition={startDate < currentDate}>
            <img src={LeftArrowDisabled} alt="이전 버튼 비활성화 아이콘" />
          </Conditional>
          <Conditional condition={startDate >= currentDate}>
            <img src={LeftArrow} alt="이전 버튼 아이콘" onClick={() => onUpdateMonth(-1)} />
          </Conditional>
          <img src={RightArrow} alt="다음 버튼 아이콘" onClick={() => onUpdateMonth(1)} />
        </div>
      </S.YearMonthContainer>
      <S.DateGrid>
        {DAY_OF_WEEKS.map((day) => (
          <S.DayOfWeekBox key={day}>{day}</S.DayOfWeekBox>
        ))}
        {Array.from({ length: dateBoxLength }, (_, index) => {
          const date = index - firstDOW + 1;
          const isOutOfCalendar = index < firstDOW || lastDate <= date - 1;
          const dayNumber = convertToFullDate(year, month, date).getDay();
          const isWeekend = dayNumber === DAY_NUMBER.SUNDAY || dayNumber === DAY_NUMBER.SATURDAY;
          const isPastDay = convertToFullDate(year, month, date) < getCurrentFullDate();

          return isOutOfCalendar ? (
            <DateBox key={index} />
          ) : (
            <DateBox
              key={index}
              date={date}
              daySchedule={monthSchedule[date]}
              onClick={() => onClickDate(date, isWeekend)}
              selectedDay={selectedDay}
              currentDay={convertToFullDate(year, month, date)}
              isCoach={isCoach}
              isWeekend={isWeekend}
              isPastDay={isPastDay}
            />
          );
        })}
      </S.DateGrid>
    </S.CalendarContainer>
  );
};

export default Calendar;
