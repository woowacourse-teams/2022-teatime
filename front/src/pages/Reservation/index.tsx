import { useEffect, useState } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { AxiosError } from 'axios';

import ReservationTimeList from '@components/ReservationTimeList';
import Calendar from '@components/Calendar';
import Frame from '@components/Frame';
import Title from '@components/Title';
import Modal from '@components/Modal';
import useCalendar from '@hooks/useCalendar';
import useBoolean from '@hooks/useBoolean';
import { getCoachSchedulesByCrew } from '@api/coach';
import { createReservation } from '@api/reservation';
import { ROUTES } from '@constants/index';
import type { DaySchedule, MonthScheduleMap, ScheduleInfo } from '@typings/domain';
import { theme } from '@styles/theme';
import * as SS from '@styles/common';
import * as S from './styles';

import CheckCircle from '@assets/check-circle.svg';

const Reservation = () => {
  const navigate = useNavigate();
  const { id: coachId } = useParams();
  const { state: coachImage } = useLocation();
  const { value: isOpenModal, setTrue: openModal, setFalse: closeModal } = useBoolean();
  const { value: isOpenTimeList, setTrue: openTimeList, setFalse: closeTimeList } = useBoolean();
  const { monthYear, selectedDay, setSelectedDay, dateBoxLength, updateMonthYear } = useCalendar();
  const [reservationId, setReservationId] = useState<number | null>(null);
  const [selectedTimeId, setSelectedTimeId] = useState<number | null>(null);
  const [schedule, setSchedule] = useState<Omit<ScheduleInfo, 'date'>>({
    monthSchedule: {},
    daySchedule: [],
  });

  const createScheduleMap = (scheduleArray: DaySchedule[]) => {
    setSchedule((allSchedules) => {
      const newMonthSchedule = scheduleArray.reduce((newObj, { day, schedules }) => {
        newObj[day] = schedules;
        return newObj;
      }, {} as MonthScheduleMap);

      return {
        ...allSchedules,
        monthSchedule: newMonthSchedule,
      };
    });
  };

  const selectDaySchedule = (day: number) => {
    setSchedule((allSchedules) => {
      const selectedDaySchedule = schedule.monthSchedule[day];

      return {
        ...allSchedules,
        daySchedule: selectedDaySchedule,
      };
    });
  };

  const updateTimeIsPossible = (scheduleId: number, isPossible: boolean) => {
    setSchedule((allSchedules) => {
      const newDaySchedule = schedule.daySchedule.map((time) => {
        if (time.id === scheduleId) {
          return { id: scheduleId, dateTime: time.dateTime, isPossible };
        }
        return time;
      });

      return {
        ...allSchedules,
        monthSchedule: { ...schedule.monthSchedule, [selectedDay]: newDaySchedule },
        daySchedule: newDaySchedule,
      };
    });
  };

  const handleUpdateMonth = (increment: number) => {
    setSchedule({ monthSchedule: {}, daySchedule: [] });
    closeTimeList();
    updateMonthYear(increment);
  };

  const handleClickDate = (day: number) => {
    openTimeList();
    selectDaySchedule(day);
    setSelectedDay(day);
    setSelectedTimeId(null);
  };

  const handleClickTime = (id: number) => {
    setSelectedTimeId(id);
  };

  const handleClickReservation = async (scheduleId: number) => {
    try {
      const data = await createReservation(scheduleId);
      const location = data.headers.location.split('/').pop();
      setReservationId(Number(location));
      setSelectedTimeId(null);
      updateTimeIsPossible(scheduleId, false);
      openModal();
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
  };

  useEffect(() => {
    (async () => {
      try {
        const { data: coachSchedules } = await getCoachSchedulesByCrew(
          coachId as string,
          monthYear.year,
          monthYear.month
        );
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
      <S.CoachImage src={coachImage as string} alt="코치 프로필 이미지" />
      <SS.ScheduleContainer>
        <Title
          text="예약할"
          highlightText={isOpenTimeList ? '시간을' : '날짜를'}
          hightlightColor={theme.colors.GREEN_300}
          extraText="선택해주세요."
        />
        <SS.CalendarContainer>
          <Calendar
            isOpenTimeList={isOpenTimeList}
            monthSchedule={schedule.monthSchedule}
            monthYear={monthYear}
            dateBoxLength={dateBoxLength}
            selectedDay={selectedDay}
            onClickDate={handleClickDate}
            onUpdateMonth={handleUpdateMonth}
          />
          {isOpenTimeList && (
            <ReservationTimeList
              daySchedule={schedule.daySchedule}
              selectedTimeId={selectedTimeId}
              onClickTime={handleClickTime}
              onClickReservation={handleClickReservation}
            />
          )}
        </SS.CalendarContainer>
      </SS.ScheduleContainer>

      {isOpenModal && (
        <Modal
          icon={CheckCircle}
          title="예약 완료"
          firstButtonName="나중에"
          secondButtonName="작성하기"
          onClickFirstButton={() => navigate(ROUTES.CREW_HISTORY)}
          onClickSecondButton={() => navigate(`${ROUTES.CREW_SHEET}/${reservationId}`)}
          closeModal={closeModal}
        >
          <p>면담 내용을 지금 작성 하시겠습니까?</p>
        </Modal>
      )}
    </Frame>
  );
};

export default Reservation;
