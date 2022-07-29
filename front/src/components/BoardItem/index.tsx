import dayjs from 'dayjs';
import ClockIcon from '@assets/clock.svg';
import CloseIcon from '@assets/close.svg';
import ScheduleIcon from '@assets/schedule.svg';
import * as S from './styles';

interface BoardItemProps {
  dateTime: string;
  name: string;
  buttonName: string;
}

const BoardItem = ({ dateTime, name, buttonName }: BoardItemProps) => {
  const date = dayjs.tz(dateTime).format('MM월 DD일');
  const time = dayjs.tz(dateTime).format('HH:mm');

  return (
    <S.BoardItemContainer>
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
      <S.BottomSection>
        <div>
          <S.ProfileImage
            src={
              'https://user-images.githubusercontent.com/48676844/181402601-b1b2c2ff-29a7-44fb-9613-16bd999abc1e.png'
            }
          />
          <span>{name}</span>
        </div>
        <button>{buttonName}</button>
      </S.BottomSection>
    </S.BoardItemContainer>
  );
};

export default BoardItem;
