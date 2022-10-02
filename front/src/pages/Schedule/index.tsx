import { useContext, useEffect, useState } from 'react';
import { AxiosError } from 'axios';

import Frame from '@components/Frame';
import Calendar from '@components/Calendar';
import CalendarSelectList from '@components/CalendarSelectList';
import Title from '@components/Title';
import ScheduleTimeList from '@components/ScheduleTimeList';
import useCalendar from '@hooks/useCalendar';
import useBoolean from '@hooks/useBoolean';
import useSelectList from '@hooks/useSelectList';
import { SnackbarContext } from '@context/SnackbarProvider';
import { editCoachSchedule, getCoachSchedulesByMe } from '@api/coach';
import { getFormatDate } from '@utils/date';
import type { DaySchedule, ScheduleInfo, MonthScheduleMap } from '@typings/domain';
import { theme } from '@styles/theme';
import * as SS from '@styles/common';
import * as S from './styles';

const timeArray = [
  '10:00',
  '10:30',
  '11:00',
  '11:30',
  '12:00',
  '12:30',
  '13:00',
  '13:30',
  '14:00',
  '14:30',
  '15:00',
  '15:30',
  '16:00',
  '16:30',
  '17:00',
  '17:30',
  '18:00',
  '18:30',
  '19:00',
  '19:30',
  '20:00',
  '20:30',
  '21:00',
  '21:30',
];

const getAllTime = (date: string) => {
  return timeArray.map((time, index) => ({
    id: index,
    dateTime: `${date}T${time}:00.000Z`,
    isSelected: false,
  }));
};

const Schedule = () => {
  const showSnackbar = useContext(SnackbarContext);
  const { value: isOpenTimeList, setTrue: openTimeList, setFalse: closeTimeList } = useBoolean();
  const {
    value: isOpenMultipleTimeList,
    setTrue: openMultipleTimeList,
    setFalse: closeMultipleTimeList,
  } = useBoolean();
  const { selectedItem: selectedCalendarMode, handleSelectItem: handleSelectCalendarMode } =
    useSelectList('singleSelect');
  const { monthYear, selectedDay, setSelectedDay, dateBoxLength, updateMonthYear } = useCalendar();
  const { lastDate, year, month } = monthYear;
  const [isSelectedAll, setIsSelectedAll] = useState(false);
  const [schedule, setSchedule] = useState<ScheduleInfo>({
    monthSchedule: {},
    daySchedule: [],
    date: '',
  });

  const [selectedDayList, setSelectedDayList] = useState<{ dates: string[]; times: string[] }>({
    dates: [],
    times: [],
  });

  const selectDaySchedule = (day: number) => {
    setSchedule((allSchedules) => {
      const selectedDaySchedule = schedule.monthSchedule[day];
      const date = getFormatDate(year, month, day);

      return {
        ...allSchedules,
        daySchedule: selectedDaySchedule,
        date,
      };
    });
  };

  const createScheduleMap = (scheduleArray: DaySchedule[]) => {
    setSchedule((allSchedules) => {
      const initialMonthSchedule = Array.from({ length: lastDate }).reduce(
        (newObj: MonthScheduleMap, _, index) => {
          const currentDateFormat = getFormatDate(year, month, index + 1);
          newObj[index + 1] = getAllTime(currentDateFormat);
          return newObj;
        },
        {}
      );

      const availableMonthSchedule = scheduleArray.reduce(
        (newObj: MonthScheduleMap, { day, schedules }) => {
          const currentDateFormat = getFormatDate(year, month, day);
          const newSchedule = getAllTime(currentDateFormat).map((time) => {
            const sameTime = schedules.find((coachTime) => coachTime.dateTime === time.dateTime);
            if (sameTime) {
              sameTime.isSelected = sameTime.isPossible;
              return sameTime;
            }
            return time;
          });

          newObj[day] = newSchedule;
          return newObj;
        },
        {}
      );

      return {
        ...allSchedules,
        monthSchedule: { ...initialMonthSchedule, ...availableMonthSchedule },
      };
    });
  };

  const getSelectedTimes = () => {
    return schedule.daySchedule.reduce((newArray, { isSelected, dateTime }) => {
      if (isSelected) {
        newArray.push(dateTime);
      }
      return newArray;
    }, [] as string[]);
  };

  const updateAvailableTimes = (selectedTimes: string[]) => {
    setSchedule((allSchedules) => {
      const newDaySchedule = schedule.monthSchedule[selectedDay].map(
        ({ id, dateTime, isPossible }) => {
          if (selectedTimes.includes(dateTime)) {
            return { id, dateTime, isPossible: true, isSelected: true };
          }
          if (isPossible === false) {
            return { id, dateTime, isPossible, isSelected: false };
          }
          return { id, dateTime, isSelected: false };
        }
      );

      return {
        ...allSchedules,
        monthSchedule: { ...schedule.monthSchedule, [selectedDay]: newDaySchedule },
      };
    });
  };

  const handleUpdateMonth = (increment: number) => {
    setSchedule({ monthSchedule: {}, daySchedule: [], date: '' });
    closeTimeList();
    updateMonthYear(increment);
  };

  const handleClickDate = (day: number, isWeekend: boolean) => {
    // if (isWeekend) return;

    openTimeList();
    selectDaySchedule(day);
    setSelectedDay(day);
    setIsSelectedAll(false);
  };

  const handleClickMultipleDate = (day: number) => {
    const date = getFormatDate(year, month, day);

    setSelectedDayList((prev) => {
      const newDates = [...selectedDayList.dates];
      const findIndex = newDates.findIndex((newDate) => newDate === date);

      if (newDates.includes(date)) {
        newDates.splice(findIndex, 1);
      } else {
        newDates.push(date);
      }
      console.log('newDates', newDates);

      return {
        ...prev,
        dates: newDates,
      };
    });
  };

  const handleCompleteMultipleDate = () => {
    // Todo: 선택된 날짜없이 완료를 누를수없게 하기
    openMultipleTimeList();
  };

  const handleClickTime = (dateTime: string) => {
    setSchedule((allSchedules) => {
      const selectedIndex = schedule.daySchedule.findIndex((time) => time.dateTime === dateTime);
      const newSchedules = [...schedule.daySchedule];
      newSchedules[selectedIndex].isSelected = !newSchedules[selectedIndex].isSelected;

      return {
        ...allSchedules,
        daySchedule: newSchedules,
      };
    });
  };

  const handleSelectAll = () => {
    setIsSelectedAll((prev) => !prev);
    setSchedule((allSchedules) => {
      const newSchedules = schedule.daySchedule.map((schedule) => {
        if (schedule.isPossible !== false) {
          schedule.isSelected = !isSelectedAll;
        }
        return schedule;
      });

      return {
        ...allSchedules,
        daySchedule: newSchedules,
      };
    });
  };

  const handleUpdateDaySchedule = async () => {
    const selectedTimes = getSelectedTimes();
    try {
      await editCoachSchedule(schedule.date, selectedTimes);
      updateAvailableTimes(selectedTimes);
      showSnackbar({ message: '확정되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
  };

  useEffect(() => {
    closeTimeList();
    closeMultipleTimeList();

    setSelectedDayList((prev) => {
      return {
        ...prev,
        dates: [],
      };
    });
  }, [selectedCalendarMode]);

  useEffect(() => {
    const initSelectedTime = () => {
      setSchedule((allSchedules) => {
        const newDaySchedule = [...schedule.daySchedule];
        newDaySchedule.forEach((time) => (time.isSelected = time.isPossible));

        return {
          ...allSchedules,
          daySchedule: newDaySchedule,
        };
      });
    };

    initSelectedTime();
  }, [schedule.date, selectedCalendarMode]);

  useEffect(() => {
    (async () => {
      try {
        const { data: coachSchedules } = await getCoachSchedulesByMe(year, month);
        createScheduleMap(coachSchedules);
      } catch (error) {
        if (error instanceof AxiosError) {
          alert(error.response?.data?.message);
          console.log(error);
        }
      }
    })();
  }, [monthYear]);

  return (
    <Frame>
      <SS.ScheduleContainer>
        <Title
          text="등록 가능한"
          highlightText={isOpenTimeList || isOpenMultipleTimeList ? '시간을' : '날짜를'}
          hightlightColor={theme.colors.GREEN_300}
          extraText="선택해주세요."
        />
        <SS.CalendarContainer>
          <div>
            <CalendarSelectList
              lists={[
                { id: 'singleSelect', text: '개별 선택' },
                { id: 'multiSelect', text: '다중 선택' },
              ]}
              selectedItem={selectedCalendarMode}
              onSelect={(e: React.MouseEvent<HTMLElement>) => {
                setSelectedDay(0);
                handleSelectCalendarMode(e);
              }}
            />
            <Calendar
              isCoach
              monthSchedule={schedule.monthSchedule}
              monthYear={monthYear}
              dateBoxLength={dateBoxLength}
              selectedDay={selectedDay}
              selectedDayList={selectedDayList.dates}
              onClickDate={
                selectedCalendarMode === 'multiSelect' ? handleClickMultipleDate : handleClickDate
              }
              onUpdateMonth={handleUpdateMonth}
            />
            {!isOpenMultipleTimeList && selectedCalendarMode === 'multiSelect' && (
              <S.SelectCompleteButton onClick={handleCompleteMultipleDate}>
                날짜 선택 완료
              </S.SelectCompleteButton>
            )}
          </div>

          {isOpenTimeList && (
            <ScheduleTimeList
              isSelectedAll={isSelectedAll}
              daySchedule={schedule.daySchedule}
              onClickTime={handleClickTime}
              onSelectAll={handleSelectAll}
              onUpdateDaySchedule={handleUpdateDaySchedule}
            />
          )}
          {isOpenMultipleTimeList && (
            <div>
              {timeArray.map((v) => (
                <div key={v}>{v}</div>
              ))}
            </div>
          )}
        </SS.CalendarContainer>
      </SS.ScheduleContainer>
    </Frame>
  );
};

export default Schedule;
