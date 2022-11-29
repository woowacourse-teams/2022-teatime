import { Fragment } from 'react';

import TimeList from '.';
import Conditional from '@components/Conditional';
import { getHourMinutes } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';

interface ReservationTimeListProps {
  data: TimeSchedule[];
  onClickTime: (id: number, isPossible?: boolean) => void;
  selectedTimeId: number | null;
  onClickReservation: (scheduleId: number) => void;
}

const ReservationTimeList = ({
  data,
  onClickTime,
  selectedTimeId,
  onClickReservation,
}: ReservationTimeListProps) => {
  return (
    <TimeList>
      {data?.map(({ id, dateTime, isPossible, ...props }, index) => {
        const time = getHourMinutes(dateTime);
        return (
          <Fragment key={id}>
            <Conditional condition={selectedTimeId === id}>
              <TimeList.Divider>
                <TimeList.SelectedTime>{time}</TimeList.SelectedTime>
                <TimeList.ReservationButton onClick={() => onClickReservation(id)}>
                  예약하기
                </TimeList.ReservationButton>
              </TimeList.Divider>
            </Conditional>

            <Conditional condition={selectedTimeId !== id}>
              <TimeList.Time
                onClick={() => onClickTime(id, isPossible)}
                isPossible={isPossible}
                dateTime={time}
                index={index}
                {...props}
              >
                {time}
              </TimeList.Time>
            </Conditional>
          </Fragment>
        );
      })}
    </TimeList>
  );
};

export default ReservationTimeList;
