import DateBox from '@components/DateBox';
import Conditional from '@components/Conditional';
import { DAY_OF_WEEKS, HOUR_MILLISECONDS } from '@constants/index';
import { convertToFullDate, getCurrentFullDate } from '@utils/date';
import type { MonthYear, MonthScheduleMap } from '@typings/domain';

import LeftArrow from '@assets/left-arrow.svg';
import LeftArrowDisabled from '@assets/left-arrow-disabled.svg';
import RightArrow from '@assets/right-arrow.svg';
import * as S from './styles';

interface CalendarProps {
  isCoach?: boolean;
  isMultipleSelecting?: boolean;
  isOpenTimeList?: boolean;
  selectedDayList?: string[];
  onUpdateMonth: (increment: number) => void;
  onClickDate: (day: number) => void;
  monthYear: MonthYear;
  dateBoxLength: number;
  selectedDay: number | null;
  monthSchedule: MonthScheduleMap;
}

const Calendar = ({
  isCoach,
  isOpenTimeList,
  isMultipleSelecting,
  selectedDayList,
  onUpdateMonth,
  onClickDate,
  monthYear,
  dateBoxLength,
  selectedDay,
  monthSchedule,
}: CalendarProps) => {
  const { firstDOW, lastDate, year, month, startDate } = monthYear;
  const currentDateTime = new Date().getTime();
  const startDateTime = startDate.getTime() - 9 * HOUR_MILLISECONDS;

  return (
    <S.CalendarContainer isMultipleSelecting={isMultipleSelecting} isOpenTimeList={isOpenTimeList}>
      <S.YearMonthContainer>
        <span>
          {year}년 {month}월
        </span>
        <div>
          <Conditional condition={startDateTime < currentDateTime}>
            <img src={LeftArrowDisabled} alt="비활성화된 왼쪽 화살표" />
          </Conditional>
          <Conditional condition={startDateTime >= currentDateTime}>
            <img src={LeftArrow} alt="왼쪽 화살표" onClick={() => onUpdateMonth(-1)} />
          </Conditional>
          <img src={RightArrow} alt="오른쪽 화살표" onClick={() => onUpdateMonth(1)} />
        </div>
      </S.YearMonthContainer>
      <S.DateGrid>
        {DAY_OF_WEEKS.map((day) => (
          <S.DayOfWeekBox key={day}>{day}</S.DayOfWeekBox>
        ))}
        {Array.from({ length: dateBoxLength }, (_, index) => {
          const date = index - firstDOW + 1;
          const isOutOfCalendar = index < firstDOW || lastDate <= date - 1;
          const isPastDay = convertToFullDate(year, month, date) < getCurrentFullDate();
          const dateString = `${year}-${month}-${String(date).padStart(2, '0')}`;
          const isMultipleSelected = selectedDayList?.includes(dateString);

          return isOutOfCalendar ? (
            <DateBox key={index} />
          ) : (
            <DateBox
              key={index}
              date={date}
              daySchedule={monthSchedule[date]}
              onClick={() => onClickDate(date)}
              selectedDay={selectedDay}
              isMultipleSelected={isMultipleSelected}
              currentDay={convertToFullDate(year, month, date)}
              isCoach={isCoach}
              isPastDay={isPastDay}
            />
          );
        })}
      </S.DateGrid>
    </S.CalendarContainer>
  );
};

export default Calendar;
