import TimeList from '@components/TimeList';
import Calendar from '@components/Calendar';
import Frame from '@components/Frame';
import Title from '@components/Title';
import { CalendarContainer, ScheduleContainer } from '@styles/common';

const Reservation = () => {
  return (
    <Frame>
      <ScheduleContainer>
        <Title text="신청 날짜를 선택해주세요" />
        <CalendarContainer>
          <Calendar />
          <TimeList />
        </CalendarContainer>
      </ScheduleContainer>
    </Frame>
  );
};

export default Reservation;
