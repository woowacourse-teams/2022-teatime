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
        {daySchedule.map((schedule) => {
          const time = getHourMinutes(schedule.dateTime);
          const isPastTime = new Date() > getDateTime(schedule.dateTime);

          return (
            <S.TimeBox
              key={schedule.id}
              isPossible={schedule.isPossible}
              isPastTime={isPastTime}
              selected={schedule.isSelected ? true : false}
              onClick={() => onClickTime(schedule.dateTime)}
            >
              {time}
            </S.TimeBox>
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
