import BackButton from '@components/BackButton';
import { getHourMinutes, getMonthDate } from '@utils/index';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';
import ClockIcon from '@assets/clock.svg';

interface ReservationInfoProps {
  image?: string;
  name?: string;
  dateTime?: string;
  isView: boolean;
}

const ReservationInfo = ({ image, name, dateTime, isView }: ReservationInfoProps) => {
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
      {isView && <BackButton />}
    </>
  );
};

export default ReservationInfo;
