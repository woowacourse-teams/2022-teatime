import { useContext, useState } from 'react';

import Conditional from '@components/Conditional';
import { ScheduleDispatchContext, ScheduleStateContext } from '@context/ScheduleProvider';
import { UserStateContext } from '@context/UserProvider';
import useSnackbar from '@hooks/useSnackbar';
import api from '@api/index';
import { getHourMinutes } from '@utils/date';
import * as S from './styles';

interface TimeSchedule {
  id: number;
  dateTime: string;
  isPossible?: boolean;
  isSelected?: boolean;
}

interface AllTimeListProps {
  daySchedule: TimeSchedule[];
  onClickTime: (dateTime: string) => void;
  onSelectAll: (isSelected: boolean) => void;
  onUpdateSchedule: () => Promise<void>;
}

const AllTimeList = ({
  daySchedule,
  onClickTime,
  onSelectAll,
  onUpdateSchedule,
}: AllTimeListProps) => {
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

export default AllTimeList;
