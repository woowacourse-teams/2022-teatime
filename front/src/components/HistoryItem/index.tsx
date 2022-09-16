import { getMonthDate } from '@utils/date';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';

interface HistoryItemProps {
  index: number;
  image: string;
  name: string;
  dateTime: string;
  onClick: (index: number) => void;
  historyIndex: number;
}

const HistoryItem = ({ index, image, name, dateTime, onClick, historyIndex }: HistoryItemProps) => {
  return (
    <S.Item onClick={() => onClick(index)} isSelected={index === historyIndex}>
      <S.Date>
        <img src={ScheduleIcon} alt="일정 아이콘" />
        <span>{getMonthDate(dateTime as string)}</span>
      </S.Date>
      <S.Profile>
        <img src={image} alt={`${name} 프로필 이미지`} />
        <span>{name}</span>
      </S.Profile>
    </S.Item>
  );
};

export default HistoryItem;
