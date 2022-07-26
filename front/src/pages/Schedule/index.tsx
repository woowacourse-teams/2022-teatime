import { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import dayjs from 'dayjs';

import Frame from '@components/Frame';
import Calendar from '@components/Calendar';
import Title from '@components/Title';
import CoachTimeList from '@components/CoachTimeList';
import { CalendarContainer, ScheduleContainer } from '@styles/common';
import useSchedules from '@hooks/useSchedules';
import useFetch from '@hooks/useFetch';
import { getMonthYearDetails, getNewMonthYear } from '@utils/index';
import type {
  DaySchedule,
  MonthYear,
  ScheduleMap,
  Schedule as ScheduleType,
} from '@typings/domain';
import api from '@api/index';
import { CoachScheduleDispatchContext } from '@context/CoachScheduleProvider';

// const timeArray = [
//   '10:00',
//   '10:30',
//   '11:00',
//   '11:30',
//   '12:00',
//   '12:30',
//   '13:00',
//   '13:30',
//   '14:00',
//   '14:30',
//   '15:00',
//   '15:30',
//   '16:00',
//   '16:30',
//   '17:00',
//   '17:30',
// ];

// const getAllTime = (date: string) => {
//   return timeArray.map((time, index) => ({ id: index, dateTime: `${date}T${time}:00.000Z` }));
// };

const Schedule = () => {
  const { id: coachId } = useParams();
  const currentMonthYear = getMonthYearDetails(dayjs());
  // const [monthYear, setMonthYear] = useState(currentMonthYear);
  // const updateMonth = (increment: number) => {
  //   setMonthYear((prev) => getNewMonthYear(prev, increment));
  // };
  const { lastDate, year, month } = currentMonthYear;
  const dispatch = useContext(CoachScheduleDispatchContext);

  // const initialMonthSchedule = Array.from({ length: lastDate }, (_, index) => {
  //   return {
  //     day: index + 1,
  //     schedules: getAllTime(`${year}-${month}-${String(index + 1).padStart(2, '0')}`),
  //   };
  // });
  // const [monthSchedule, setMonthSchedule] = useState(initialMonthSchedule);

  // console.log('initialMonthSchedule', initialMonthSchedule);

  // 같은 day를 찾아서 dateTime이 같은걸 찾아서 덮어씌운다.

  // const monthScheduleMap = monthSchedule?.reduce((newObj, { day, schedules }) => {
  //   newObj[day] = schedules;
  //   return newObj;
  // }, {} as ScheduleMap);

  // console.log('monthScheduleMap', monthScheduleMap);
  // console.log('monthSchedule', monthSchedule);

  // const handleClickDate = (date: number) => {
  //   const selectedDaySchedule = monthSchedule.find((daySchedule) => daySchedule.day === date);
  //   console.log('selectedDaySchedule', selectedDaySchedule?.schedules);
  //   // setTimeLists(selectedDaySchedule?.schedules)
  // };

  return (
    <Frame>
      <ScheduleContainer>
        <Title text="가능한 날짜를 선택해주세요" />
        <CalendarContainer>
          <Calendar isCoach />
          <CoachTimeList />
        </CalendarContainer>
      </ScheduleContainer>
    </Frame>
  );
};

export default Schedule;
