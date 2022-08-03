import * as S from './styles';
import dayjs from 'dayjs';

import ClockIcon from '@assets/clock.svg';
import CloseIcon from '@assets/close.svg';
import ScheduleIcon from '@assets/schedule.svg';

interface BoardItemProps {
  dateTime: string;
  image: string;
  personName: string;
  buttonName: string;
  color: string;
  onClick: () => void;
}

const BoardItem = ({ dateTime, image, personName, buttonName, color, onClick }: BoardItemProps) => {
  const date = dayjs.tz(dateTime).format('MM월 DD일');
  const time = dayjs.tz(dateTime).format('HH:mm');

  return (
    <S.BoardItemContainer color={color}>
      <S.TopSection>
        <S.DateContainer>
          <div>
            <img src={ScheduleIcon} alt="일정 아이콘" />
            <span>{date}</span>
          </div>
          <div>
            <img src={ClockIcon} alt="시계 아이콘" />
            <span>{time}</span>
          </div>
        </S.DateContainer>
        <S.CloseIconWrapper>
          <img src={CloseIcon} alt="닫기 아이콘" />
        </S.CloseIconWrapper>
      </S.TopSection>
      <S.BottomSection color={color}>
        <div>
          <S.ProfileImage src={image} />
          <span>{personName}</span>
        </div>
        <button onClick={onClick}>{buttonName}</button>
      </S.BottomSection>
    </S.BoardItemContainer>
  );
};

export default BoardItem;
