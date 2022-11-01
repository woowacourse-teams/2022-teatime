import { getDateTime, getHourMinutes } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';
import * as S from './styles';

interface ScheduleTimeListProps {
  isSelectedAll: boolean;
  daySchedule: TimeSchedule[];
  onClickTime: (dateTime: string) => void;
  onSelectAll: () => void;
  onUpdateDaySchedule: () => Promise<void>;
}

const ScheduleTimeList = ({
  isSelectedAll,
  daySchedule,
  onClickTime,
  onSelectAll,
  onUpdateDaySchedule,
}: ScheduleTimeListProps) => {
  return (
    <S.TimeListContainer>
      <S.ScrollContainer>
        {daySchedule.map(({ id, dateTime, isPossible, isSelected }) => {
          const time = getHourMinutes(dateTime);
          const isPastTime = new Date() > getDateTime(dateTime);

          return (
            <S.ScheduleTimeBox
              key={id}
              isPossible={isPossible}
              isPastTime={isPastTime}
              isSelected={isSelected ? true : false}
              onClick={() => onClickTime(dateTime)}
            >
              {time}
            </S.ScheduleTimeBox>
          );
        })}
      </S.ScrollContainer>

      <S.ButtonContainer>
        <S.CheckButton onClick={onSelectAll}>
          {isSelectedAll ? '전체 해제' : '전체 선택'}
        </S.CheckButton>
        <S.ConfirmButton onClick={onUpdateDaySchedule}>확인</S.ConfirmButton>
      </S.ButtonContainer>
    </S.TimeListContainer>
  );
};

export default ScheduleTimeList;
