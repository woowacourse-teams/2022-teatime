import { getMonthDate } from '@utils/index';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';

interface HistoryItemProps {
  image: string;
  name: string;
  dateTime: string;
}

const HistoryItem = ({ image, name, dateTime }: HistoryItemProps) => {
  return (
    <S.Item>
      <S.Date>
        <img src={ScheduleIcon} alt="일정 아이콘" />
        <span>{getMonthDate(dateTime as string)}</span>
      </S.Date>
      <S.Profile>
        <img src={image} alt="코치 프로필 이미지" />
        <span>{name}</span>
      </S.Profile>
    </S.Item>
  );
};

export default HistoryItem;
