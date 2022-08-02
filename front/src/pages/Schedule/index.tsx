import Frame from '@components/Frame';
import Calendar from '@components/Calendar';
import Title from '@components/Title';
import CoachTimeList from '@components/CoachTimeList';
import * as S from '@styles/common';
import { useState } from 'react';

const Schedule = () => {
  const [isOpenTimeList, setIsOpenTimeList] = useState(false);

  const handleOpenTimeList = () => {
    setIsOpenTimeList(true);
  };

  const handleCloseTimeList = () => {
    setIsOpenTimeList(false);
  };

  return (
    <Frame>
      <S.ScheduleContainer>
        <Title text="가능한 날짜를 선택해주세요" />
        <S.CalendarContainer>
          <Calendar isCoach onClickDate={handleOpenTimeList} onChangeMonth={handleCloseTimeList} />
          {isOpenTimeList && <CoachTimeList />}
        </S.CalendarContainer>
      </S.ScheduleContainer>
    </Frame>
  );
};

export default Schedule;
