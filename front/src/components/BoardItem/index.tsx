import { useState } from 'react';

import { getHourMinutes, getMonthDate } from '@utils/date';
import ClockIcon from '@assets/clock.svg';
import CloseIcon from '@assets/close.svg';
import ScheduleIcon from '@assets/schedule.svg';
import * as S from './styles';

interface BoardItemProps {
  dateTime: string;
  image: string;
  personName: string;
  buttonName: string;
  isButtonDisabled?: boolean;
  color: string;
  draggedColor: string;
  onClickMenu: () => void;
  onClickProfile: () => void;
  onClickCancel: () => void;
  onDragStart: (e: React.DragEvent<HTMLDivElement>) => void;
}

const BoardItem = ({
  dateTime,
  image,
  personName,
  buttonName,
  isButtonDisabled,
  color,
  draggedColor,
  onClickMenu,
  onClickProfile,
  onClickCancel,
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
        </S.DateContainer>
        <S.CloseIconWrapper>
          <img src={CloseIcon} alt="취소 아이콘" onClick={onClickCancel} />
        </S.CloseIconWrapper>
      </S.TopSection>
      <S.BottomSection>
        <div>
          <S.ProfileImage src={image} alt={`${personName} 이미지`} />
          <span>{personName}</span>
        </div>
        <S.MenuButton onClick={onClickMenu} isButtonDisabled={isButtonDisabled} color={color}>
          {buttonName}
        </S.MenuButton>
      </S.BottomSection>
    </S.BoardItemContainer>
  );
};

export default BoardItem;
