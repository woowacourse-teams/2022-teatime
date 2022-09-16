import { getHourMinutes, getMonthDate } from '@utils/date';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';
import ClockIcon from '@assets/clock.svg';

interface ReservationInfoProps {
  image?: string;
  name?: string;
  dateTime?: string;
}

const ReservationInfo = ({ image, name, dateTime }: ReservationInfoProps) => {
  return (
    <>
      <S.Image src={image} alt={`${name} 프로필 이미지`} />
      <S.Name>{name}</S.Name>
      <S.DateWrapper>
        <img src={ScheduleIcon} alt="일정 아이콘" />
        <span>{getMonthDate(dateTime as string)}</span>
      </S.DateWrapper>
      <S.DateWrapper>
        <img src={ClockIcon} alt="시계 아이콘" />
        <span>{getHourMinutes(dateTime as string)}</span>
      </S.DateWrapper>
    </>
  );
};

export default ReservationInfo;
