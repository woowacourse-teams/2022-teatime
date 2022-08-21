import { useState } from 'react';

import Conditional from '@components/Conditional';
import { getHourMinutes } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';
import * as S from './styles';

interface ReservationTimeListProps {
  daySchedule: TimeSchedule[];
  onClickTime: (dateTime: string) => void;
  onSelectAll: (isSelected: boolean) => void;
  onUpdateSchedule: () => Promise<void>;
}

const ReservationTimeList = ({
  daySchedule,
  onClickTime,
  onSelectAll,
  onUpdateSchedule,
}: ReservationTimeListProps) => {
  const [isSelectedAll, setIsSelectedAll] = useState(false);

  const handleSelectAll = () => {
    setIsSelectedAll((prev) => !prev);
    onSelectAll(isSelectedAll);
  };

  return (
    <S.TimeListContainer>
      <S.ScrollContainer>
        {daySchedule.map((schedule) => {
          const time = getHourMinutes(schedule.dateTime);

          return (
            <S.TimeBox
              key={schedule.id}
              isPossible={schedule.isPossible}
              aria-disabled={schedule.isPossible}
              selected={schedule.isSelected ? true : false}
              onClick={() => onClickTime(schedule.dateTime)}
            >
              {time}
            </S.TimeBox>
          );
        })}
      </S.ScrollContainer>

      <Conditional condition={daySchedule.length !== 0}>
        <S.ButtonContainer>
          <S.CheckButton onClick={handleSelectAll}>
            {isSelectedAll ? '전체 해제' : '전체 선택'}
          </S.CheckButton>
          <S.ConfirmButton onClick={onUpdateSchedule}>확인</S.ConfirmButton>
        </S.ButtonContainer>
      </Conditional>
    </S.TimeListContainer>
  );
};

export default ReservationTimeList;
