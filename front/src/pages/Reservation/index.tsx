import TimeList from '@components/TimeList';
import Calendar from '@components/Calendar';
import Frame from '@components/Frame';
import Title from '@components/Title';
import * as S from '@styles/common';

const Reservation = () => {
  return (
    <Frame>
      <S.ScheduleContainer>
        <Title text="신청 날짜를 선택해주세요" />
        <S.CalendarContainer>
          <Calendar />
          <TimeList />
        </S.CalendarContainer>
      </S.ScheduleContainer>
    </Frame>
  );
};

export default Reservation;
