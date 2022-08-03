import TimeList from '@components/TimeList';
import Calendar from '@components/Calendar';
import Frame from '@components/Frame';
import Title from '@components/Title';
import useTimeList from '@hooks/useTimeList';
import * as S from '@styles/common';

const Reservation = () => {
  const { isOpenTimeList, openTimeList, closeTimeList } = useTimeList();

  return (
    <Frame>
      <S.ScheduleContainer>
        <Title text="신청 날짜를 선택해주세요" />
        <S.CalendarContainer>
          <Calendar openTimeList={openTimeList} closeTimeList={closeTimeList} />
          {isOpenTimeList && <TimeList />}
        </S.CalendarContainer>
      </S.ScheduleContainer>
    </Frame>
  );
};

export default Reservation;
