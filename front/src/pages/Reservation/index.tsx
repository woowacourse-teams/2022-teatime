import TimeList from '@components/TimeList';
import Calendar from '@components/Calendar';
import Frame from '@components/Frame';
import Title from '@components/Title';
import * as S from '@styles/common';
import { useState } from 'react';

const Reservation = () => {
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
        <Title text="신청 날짜를 선택해주세요" />
        <S.CalendarContainer>
          <Calendar onClickDate={handleOpenTimeList} onChangeMonth={handleCloseTimeList} />
          {isOpenTimeList && <TimeList />}
        </S.CalendarContainer>
      </S.ScheduleContainer>
    </Frame>
  );
};

export default Reservation;
