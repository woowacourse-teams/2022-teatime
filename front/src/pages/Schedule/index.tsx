import Frame from '@components/Frame';
import Calendar from '@components/Calendar';
import Title from '@components/Title';
import CoachTimeList from '@components/CoachTimeList';
import * as S from '@styles/common';

const Schedule = () => {
  return (
    <Frame>
      <S.ScheduleContainer>
        <Title text="가능한 날짜를 선택해주세요" />
        <S.CalendarContainer>
          <Calendar isCoach />
          <CoachTimeList />
        </S.CalendarContainer>
      </S.ScheduleContainer>
    </Frame>
  );
};

export default Schedule;
