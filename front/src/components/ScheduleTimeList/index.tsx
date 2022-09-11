import { useContext } from 'react';
import { AxiosError } from 'axios';

import { SnackbarContext } from '@context/SnackbarProvider';
import api from '@api/index';
import { getHourMinutes } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';
import * as S from './styles';

interface ScheduleTimeListProps {
  date: string;
  isSelectedAll: boolean;
  daySchedule: TimeSchedule[];
  onClickTime: (dateTime: string) => void;
  onSelectAll: () => void;
  onUpdateSchedule: (selectedTimes: string[]) => void;
}

const ScheduleTimeList = ({
  date,
  isSelectedAll,
  daySchedule,
  onClickTime,
  onSelectAll,
  onUpdateSchedule,
}: ScheduleTimeListProps) => {
  const showSnackbar = useContext(SnackbarContext);

  const getSelectedTimes = () => {
    return daySchedule.reduce((newArray, { isSelected, dateTime }) => {
      if (isSelected) {
        newArray.push(dateTime);
      }
      return newArray;
    }, [] as string[]);
  };

  const handleUpdateDaySchedule = async () => {
    const selectedTimes = getSelectedTimes();
    try {
      await api.put(`/api/v2/coaches/me/schedules`, {
        date,
        schedules: selectedTimes,
      });

      onUpdateSchedule(selectedTimes);
      showSnackbar({ message: '확정되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
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
        <S.ConfirmButton onClick={handleUpdateDaySchedule}>확인</S.ConfirmButton>
      </S.ButtonContainer>
    </S.TimeListContainer>
  );
};

export default ScheduleTimeList;
