import { getHourMinutes, getMonthDate } from '@utils/index';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';
import ClockIcon from '@assets/clock.svg';

interface ReservationInfoProps {
  coachImage?: string;
  coachName?: string;
  dateTime?: string;
}

const ReservationInfo = ({ coachImage, coachName, dateTime }: ReservationInfoProps) => {
  return (
    <>
      <S.CoachImg src={coachImage} alt="코치 프로필 이미지" />
      <p>{coachName}</p>
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
