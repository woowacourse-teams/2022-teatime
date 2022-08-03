import Frame from '@components/Frame';
import Calendar from '@components/Calendar';
import Title from '@components/Title';
import CoachTimeList from '@components/CoachTimeList';
import useTimeList from '@hooks/useTimeList';
import * as S from '@styles/common';

const Schedule = () => {
  const { isOpenTimeList, openTimeList, closeTimeList } = useTimeList();

  return (
    <Frame>
      <S.ScheduleContainer>
        <Title text="가능한 날짜를 선택해주세요" />
        <S.CalendarContainer>
          <Calendar isCoach openTimeList={openTimeList} closeTimeList={closeTimeList} />
          {isOpenTimeList && <CoachTimeList />}
        </S.CalendarContainer>
      </S.ScheduleContainer>
    </Frame>
  );
};

export default Schedule;
