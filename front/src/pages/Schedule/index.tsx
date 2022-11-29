import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Frame from '@components/Frame';
import Calendar from '@components/Calendar';
import CalendarSelectList from '@components/CalendarSelectList';
import Title from '@components/Title';
import Conditional from '@components/Conditional';
import ScheduleTimeList from '@components/TimeList/ScheduleTimeList';
import MultipleTimeList from '@components/TimeList/MultipleTimeList';
import useBoolean from '@hooks/useBoolean';
import useRefetch from '@hooks/useRefetch';
import useSchedule from './hooks/useSchedule';
import useMultipleSchedule from './hooks/useMultipleSchedule';
import { SnackbarContext } from '@context/SnackbarProvider';
import { editCoachSchedule, getCoachSchedulesByMe } from '@api/coach';
import { getFormatDate } from '@utils/date';
import { logError } from '@utils/logError';
import { getSelectedTimes } from './utils/times';
import { ERROR_MESSAGE, ROUTES } from '@constants/index';
import { theme } from '@styles/theme';
import * as SS from '@styles/common';
import * as S from './styles';

const Schedule = () => {
  const navigate = useNavigate();
  const showSnackbar = useContext(SnackbarContext);
  const { refetchCount, refetch } = useRefetch();
  const { value: isOpenTimeList, setTrue: openTimeList, setFalse: closeTimeList } = useBoolean();
  const {
    value: isOpenMultipleTimeList,
    setTrue: openMultipleTimeList,
    setFalse: closeMultipleTimeList,
  } = useBoolean();
  const {
    schedule,
    isSelectedAll,
    createScheduleMap,
    selectDaySchedule,
    handleSelectAllTimes,
    updateAvailableTimes,
    initSelectedTimes,
    handleClickTime,
    monthYear,
    selectedDay,
    setSelectedDay,
    dateBoxLength,
    updateMonthSchedule,
  } = useSchedule();
  const {
    selectedDayList,
    initSelectedMultipleDates,
    initSelectedMultipleTimes,
    selectMultipleDays,
    handleClickMultipleTime,
  } = useMultipleSchedule();
  const { year, month } = monthYear;
  const [calendarMode, setCalenderMode] = useState('singleSelect');

  const isMultipleSelecting = calendarMode === 'multiSelect' && selectedDayList.dates.length > 0;

  const handleUpdateMonth = (increment: number) => {
    if (isMultipleSelecting) {
      alert(`${month}월 등록을 완료해주세요.`);
      return;
    }

    closeTimeList();
    updateMonthSchedule(increment);
  };

  const handleClickDate = (day: number) => {
    openTimeList();
    selectDaySchedule(day);
  };

  const handleCalendarMode = (e: React.MouseEvent<HTMLElement>) => {
    const target = e.target as HTMLElement;
    if (target.tagName !== 'LI') return;
    setCalenderMode(target.id);
  };

  const handleClickMultipleDate = (day: number) => {
    const date = getFormatDate(year, month, day);
    const hasImPossibleDay =
      schedule.monthSchedule[day].filter((v) => v.isPossible === false).length > 0;

    if (hasImPossibleDay) {
      showSnackbar({ message: '예약이 확정된 날짜는 선택할 수 없어요. ⛔️' });
      return;
    }

    selectMultipleDays(date);
  };

  const handleCompleteMultipleDate = () => {
    openMultipleTimeList();
    initSelectedMultipleTimes();
  };

  const handleReSelectMultipleDate = () => {
    closeMultipleTimeList();
    initSelectedMultipleTimes();
  };

  const handleUpdateDaySchedule = async () => {
    const selectedTimes = getSelectedTimes(schedule.daySchedule);
    const daySchedules = [{ date: schedule.date, schedules: selectedTimes }];

    try {
      await editCoachSchedule(daySchedules);
      updateAvailableTimes(selectedTimes);
      showSnackbar({ message: '확정되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        const errorCode = error.response?.status;
        logError(error);

        switch (errorCode) {
          case 400: {
            alert(ERROR_MESSAGE.FAIL_ENROLL_SCHEDULE);
            refetch();
            setSelectedDay(0);
            closeTimeList();
            break;
          }

          default: {
            navigate(ROUTES.ERROR);
            break;
          }
        }
      }
    }
  };

  const handleUpdateMultipleDaySchedule = async () => {
    const selectedTimes = getSelectedTimes(selectedDayList.times);
    const multipleDaySchedules = selectedDayList.dates.map((date) => {
      const schedules = selectedTimes.map((time) => `${date}T${time}:00.000Z`);
      return { date, schedules };
    });

    try {
      await editCoachSchedule(multipleDaySchedules);
      refetch();
      closeMultipleTimeList();
      initSelectedMultipleDates();
      setCalenderMode('singleSelect');
      showSnackbar({ message: '일괄 적용되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        const errorCode = error.response?.status;
        logError(error);

        switch (errorCode) {
          case 400: {
            alert(ERROR_MESSAGE.FAIL_ENROLL_SCHEDULE);
            refetch();
            closeMultipleTimeList();
            break;
          }

          default: {
            navigate(ROUTES.ERROR);
            break;
          }
        }
      }
    }
  };

  const handleSelectMode = (e: React.MouseEvent<HTMLElement>) => {
    setSelectedDay(0);
    handleCalendarMode(e);
  };

  useEffect(() => {
    closeTimeList();
    closeMultipleTimeList();
    initSelectedMultipleDates();
  }, [calendarMode]);

  useEffect(() => {
    initSelectedTimes();
  }, [schedule.date, calendarMode]);

  useEffect(() => {
    (async () => {
      try {
        const { data: coachSchedules } = await getCoachSchedulesByMe(year, month);
        createScheduleMap(coachSchedules);
      } catch (error) {
        if (error instanceof AxiosError) {
          logError(error);
          navigate(ROUTES.ERROR);
          return;
        }
      }
    })();
  }, [monthYear, refetchCount]);

  return (
    <Frame>
      <SS.ScheduleContainer>
        <Title
          text="등록 가능한"
          highlightText={isOpenTimeList || isOpenMultipleTimeList ? '시간을' : '날짜를'}
          highlightColor={theme.colors.YELLOW_400}
          extraText="선택해주세요."
          tooltipText="면담 신청자는 30분 단위로 신청할 수 있습니다."
        />
        <SS.CalendarContainer>
          <div>
            <CalendarSelectList
              lists={[
                { id: 'singleSelect', text: '개별 선택' },
                { id: 'multiSelect', text: '다중 선택' },
              ]}
              selectedItem={calendarMode}
              onSelect={handleSelectMode}
            />
            <Calendar
              isCoach
              isOpenTimeList={isOpenTimeList}
              isMultipleSelecting={isOpenMultipleTimeList}
              monthSchedule={schedule.monthSchedule}
              monthYear={monthYear}
              dateBoxLength={dateBoxLength}
              selectedDay={selectedDay}
              selectedDayList={selectedDayList.dates}
              onClickDate={
                calendarMode === 'multiSelect' ? handleClickMultipleDate : handleClickDate
              }
              onUpdateMonth={handleUpdateMonth}
            />

            <Conditional condition={!isOpenMultipleTimeList && isMultipleSelecting}>
              <S.SelectStatusButton onClick={handleCompleteMultipleDate}>
                날짜 선택 완료하기
              </S.SelectStatusButton>
            </Conditional>

            <Conditional condition={isOpenMultipleTimeList}>
              <S.SelectStatusButton onClick={handleReSelectMultipleDate}>
                날짜 다시 선택하기
              </S.SelectStatusButton>
            </Conditional>
          </div>

          <Conditional condition={isOpenTimeList}>
            <ScheduleTimeList
              data={schedule.daySchedule}
              onClickTime={handleClickTime}
              onSelectAll={handleSelectAllTimes}
              onSubmit={handleUpdateDaySchedule}
              isSelectedAll={isSelectedAll}
            />
          </Conditional>

          <Conditional condition={isOpenMultipleTimeList}>
            <MultipleTimeList
              data={selectedDayList.times}
              onClickTime={handleClickMultipleTime}
              onSubmit={handleUpdateMultipleDaySchedule}
            />
          </Conditional>
        </SS.CalendarContainer>
      </SS.ScheduleContainer>
    </Frame>
  );
};

export default Schedule;
