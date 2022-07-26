import Frame from '@components/Frame';
import Calendar from '@components/Calendar';
import Title from '@components/Title';
import CoachTimeList from '@components/CoachTimeList';
import { CalendarContainer, ScheduleContainer } from '@styles/common';

const Schedule = () => {
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
