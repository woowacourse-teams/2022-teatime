import { useState } from 'react';

import { getHourMinutes, getMonthDate } from '@utils/date';
import ClockIcon from '@assets/clock.svg';
import PersonIcon from '@assets/person.svg';
import ScheduleIcon from '@assets/schedule.svg';
import * as S from './styles';

interface BoardItemProps {
  dateTime: string;
  image: string;
  personName: string;
  firstButton: string;
  secondButton: string;
  isButtonDisabled?: boolean;
  color: string;
  draggedColor: string;
  onClickSecondButton: () => void;
  onClickProfile: () => void;
  onClickFirstButton: () => void;
  onDragStart: (e: React.DragEvent<HTMLDivElement>) => void;
}

const BoardItem = ({
  dateTime,
  image,
  personName,
  firstButton,
  secondButton,
  isButtonDisabled,
  color,
  draggedColor,
  onClickSecondButton,
  onClickProfile,
  onClickFirstButton,
  onDragStart,
}: BoardItemProps) => {
  const [isDragging, setIsDragging] = useState(false);
  const date = getMonthDate(dateTime);
  const time = getHourMinutes(dateTime);

  const handleDrag = () => {
    setIsDragging(true);
  };

  const handleDragEnd = () => {
    setIsDragging(false);
  };

  return (
    <S.BoardItemContainer
      draggable
      color={color}
      draggedColor={draggedColor}
      onDragStart={onDragStart}
      onDrag={handleDrag}
      onDragEnd={handleDragEnd}
      isDragging={isDragging}
    >
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
          <div>
            <img src={PersonIcon} alt="프로필 아이콘" />
            <span>{personName}</span>
          </div>
        </S.DateContainer>
        <S.ProfileImage src={image} alt={`${personName} 이미지`} />
      </S.TopSection>
      <S.ButtonWrapper>
        <S.FirstButton onClick={onClickFirstButton} color={color}>
          {firstButton}
        </S.FirstButton>
        <S.SecondButton
          onClick={onClickSecondButton}
          isButtonDisabled={isButtonDisabled}
          color={color}
        >
          {secondButton}
        </S.SecondButton>
      </S.ButtonWrapper>
    </S.BoardItemContainer>
  );
};

export default BoardItem;
