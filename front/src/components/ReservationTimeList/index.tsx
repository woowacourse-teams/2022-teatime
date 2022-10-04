import { Fragment } from 'react';

import Conditional from '@components/Conditional';
import { getHourMinutes } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';
import * as S from './styles';

interface ReservationTimeListProps {
  daySchedule: TimeSchedule[];
  selectedTimeId: number | null;
  onClickTime: (id: number) => void;
  onClickReservation: (scheduleId: number) => void;
}

const ReservationTimeList = ({
  daySchedule,
  selectedTimeId,
  onClickTime,
  onClickReservation,
}: ReservationTimeListProps) => {
  return (
    <S.TimeListContainer>
      {daySchedule.map((schedule) => {
        const time = getHourMinutes(schedule.dateTime);

        return (
          <Fragment key={schedule.id}>
            <Conditional condition={selectedTimeId === schedule.id}>
              <S.ReserveButtonWrapper>
                <div>{time}</div>
                <button onClick={() => onClickReservation(schedule.id)}>예약하기</button>
              </S.ReserveButtonWrapper>
            </Conditional>

            <Conditional condition={selectedTimeId !== schedule.id}>
              <S.ReservationTimeBox
                isPossible={schedule.isPossible}
                onClick={() => onClickTime(schedule.id)}
              >
                {time}
              </S.ReservationTimeBox>
            </Conditional>
          </Fragment>
        );
      })}
    </S.TimeListContainer>
  );
};

export default ReservationTimeList;
