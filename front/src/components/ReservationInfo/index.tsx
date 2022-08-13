import { getHourMinutes, getMonthDate } from '@utils/index';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';
import ClockIcon from '@assets/clock.svg';
import LeftArrowIcon from '@assets/left-arrow-disabled.svg';

interface ReservationInfoProps {
  image?: string;
  name?: string;
  dateTime?: string;
  isView: boolean;
  onClick: () => void;
}

const ReservationInfo = ({ image, name, dateTime, isView, onClick }: ReservationInfoProps) => {
  return (
    <>
      <S.CoachImg src={image} alt="코치 프로필 이미지" />
      <S.Name>{name}</S.Name>
      <S.DateWrapper>
        <img src={ScheduleIcon} alt="일정 아이콘" />
        <span>{getMonthDate(dateTime as string)}</span>
      </S.DateWrapper>
      <S.DateWrapper>
        <img src={ClockIcon} alt="시계 아이콘" />
        <span>{getHourMinutes(dateTime as string)}</span>
      </S.DateWrapper>
      {isView && <S.ArrowIcon src={LeftArrowIcon} alt="화살표 아이콘" onClick={onClick} />}
    </>
  );
};

export default ReservationInfo;
