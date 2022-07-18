import Frame from '@components/Frame';
import Calendar from '@components/Calendar';
import TimeList from '@components/TimeList';
import Title from '@components/Title';
import { CalendarContainer, ScheduleContainer } from '@styles/common';

const Schedule = () => {
  return (
    <Frame>
      <ScheduleContainer>
        <Title text="가능한 날짜를 선택해주세요" />
        <CalendarContainer>
          <Calendar isCoach />
          <TimeList />
        </CalendarContainer>
      </ScheduleContainer>
    </Frame>
  );
};

export default Schedule;
