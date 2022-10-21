import { Fragment } from 'react';

import Conditional from '@components/Conditional';
import { getHourMinutes } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';
import * as S from './styles';

interface ReservationTimeListProps {
  daySchedule: TimeSchedule[];
  selectedTimeId: number | null;
  onClickTime: (id: number, isPossible?: boolean) => void;
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
      {daySchedule.map(({ id, isPossible, dateTime }, index) => {
        const time = getHourMinutes(dateTime);

        return (
          <Fragment key={id}>
            <Conditional condition={selectedTimeId === id}>
              <S.ReserveButtonWrapper>
                <div>{time}</div>
                <button onClick={() => onClickReservation(id)} autoFocus>
                  예약하기
                </button>
              </S.ReserveButtonWrapper>
            </Conditional>

            <Conditional condition={selectedTimeId !== id}>
              <S.ReservationTimeBox
                aria-label={isPossible ? '' : `${time} 예약 불가`}
                autoFocus={index === 0}
                isPossible={isPossible}
                onClick={() => onClickTime(id, isPossible)}
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
