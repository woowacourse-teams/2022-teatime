import { useContext, useState } from 'react';

import Conditional from '@components/Conditional';
import useSnackbar from '@hooks/useSnackbar';
import { UserStateContext } from '@context/UserProvider';
import { getHourMinutes } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';
import api from '@api/index';
import * as S from './styles';

interface ScheduleTimeListProps {
  date: string;
  daySchedule: TimeSchedule[];
  onClickTime: (dateTime: string) => void;
  onSelectAll: (isSelected: boolean) => void;
  onUpdateSchedule: (selectedTimes: string[]) => void;
}

const ScheduleTimeList = ({
  date,
  daySchedule,
  onClickTime,
  onSelectAll,
  onUpdateSchedule,
}: ScheduleTimeListProps) => {
  const { userData } = useContext(UserStateContext);
  const showSnackbar = useSnackbar();
  const [isSelectedAll, setIsSelectedAll] = useState(false);

  const getSelectedTimes = () => {
    return daySchedule.reduce((newArray, { isSelected, dateTime }) => {
      if (isSelected) {
        newArray.push(dateTime);
      }
      return newArray;
    }, [] as string[]);
  };

  const handleSelectAll = () => {
    setIsSelectedAll((prev) => !prev);
    onSelectAll(isSelectedAll);
  };

  const handleUpdateDaySchedule = async () => {
    const selectedTimes = getSelectedTimes();
    try {
      await api.put(
        `/api/v2/coaches/me/schedules`,
        {
          date,
          schedules: selectedTimes,
        },
        {
          headers: {
            Authorization: `Bearer ${userData?.token}`,
          },
        }
      );
      onUpdateSchedule(selectedTimes);
      showSnackbar({ message: '확정되었습니다. ✅' });
    } catch (error) {
      alert(error);
      console.log(error);
    }
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
          <S.ConfirmButton onClick={handleUpdateDaySchedule}>확인</S.ConfirmButton>
        </S.ButtonContainer>
      </Conditional>
    </S.TimeListContainer>
  );
};

export default ScheduleTimeList;
