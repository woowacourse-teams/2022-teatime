import { Fragment } from 'react';

import Conditional from '@components/Conditional';
import { getDateTime, getHourMinutes } from '@utils/date';
import type { MultipleTime, TimeSchedule } from '@typings/domain';
import * as S from './styles';

interface TimeListProps {
  children: React.ReactNode;
}

interface ScrollProps {
  children: React.ReactNode;
}

interface TimeProps {
  children: React.ReactNode;
  onClick: () => void;
  isPastTime?: boolean;
  isPossible?: boolean;
  dateTime?: string;
  index?: number;
}

interface DividerProps {
  children: React.ReactNode;
}

interface ControlsProps {
  children: React.ReactNode;
}

interface SelectAllButtonProps {
  children: React.ReactNode;
  onClick: () => void;
}

interface ConfirmButtonProps {
  children: React.ReactNode;
  onClick: () => void;
}

interface SelectedTimeProps {
  children: React.ReactNode;
}

interface ReservationButtonProps {
  children: React.ReactNode;
  onClick: () => void;
}

const TimeList = ({ children }: TimeListProps) => {
  return <S.TimeListContainer>{children}</S.TimeListContainer>;
};

const Scroll = ({ children }: ScrollProps) => {
  return <S.ScrollContainer>{children}</S.ScrollContainer>;
};

const Time = ({ children, isPossible, dateTime, index, ...props }: TimeProps) => {
  return (
    <S.TimeItem
      isPossible={isPossible}
      aria-label={isPossible ? '' : `${dateTime} 선택 불가`}
      autoFocus={index === 0}
      {...props}
    >
      {children}
    </S.TimeItem>
  );
};

const Controls = ({ children }: ControlsProps) => {
  return <S.Controls>{children}</S.Controls>;
};

const SelectAllButton = ({ children, ...props }: SelectAllButtonProps) => {
  return <S.SelectAllButton {...props}>{children}</S.SelectAllButton>;
};

const ConfirmButton = ({ children, ...props }: ConfirmButtonProps) => {
  return <S.ConfirmButton {...props}>{children}</S.ConfirmButton>;
};

const Divider = ({ children }: DividerProps) => {
  return <S.Divider>{children}</S.Divider>;
};

const SelectedTime = ({ children }: SelectedTimeProps) => {
  return <div>{children}</div>;
};

const ReservationButton = ({ children, ...props }: ReservationButtonProps) => {
  return (
    <button {...props} autoFocus>
      {children}
    </button>
  );
};

interface ScheduleTimeListProps {
  data: TimeSchedule[];
  onClickTime: (dateTime: string) => void;
  onSelectAll: () => void;
  onSubmit: () => Promise<void>;
  isSelectedAll: boolean;
}

export const ScheduleTimeList = ({
  data,
  onClickTime,
  onSelectAll,
  onSubmit,
  isSelectedAll,
}: ScheduleTimeListProps) => {
  return (
    <TimeList>
      <TimeList.Scroll>
        {data?.map(({ id, dateTime, ...props }) => (
          <TimeList.Time
            key={id}
            onClick={() => onClickTime(dateTime)}
            isPastTime={new Date() > getDateTime(dateTime)}
            {...props}
          >
            {getHourMinutes(dateTime)}
          </TimeList.Time>
        ))}
      </TimeList.Scroll>
      <TimeList.Controls>
        <TimeList.SelectAllButton onClick={onSelectAll}>
          {isSelectedAll ? '전체 해제' : '전체 선택'}
        </TimeList.SelectAllButton>
        <TimeList.ConfirmButton onClick={onSubmit}>확인</TimeList.ConfirmButton>
      </TimeList.Controls>
    </TimeList>
  );
};

interface MultipleTimeListProps {
  data: MultipleTime[];
  onClickTime: (dateTime: string) => void;
  onSubmit: () => Promise<void>;
}

export const MultipleTimeList = ({ data, onClickTime, onSubmit }: MultipleTimeListProps) => {
  return (
    <TimeList>
      <TimeList.Scroll>
        {data?.map(({ id, dateTime, ...props }) => (
          <TimeList.Time key={id} onClick={() => onClickTime(dateTime)} {...props}>
            {dateTime}
          </TimeList.Time>
        ))}
      </TimeList.Scroll>
      <TimeList.Controls>
        <TimeList.ConfirmButton onClick={onSubmit}>확인</TimeList.ConfirmButton>
      </TimeList.Controls>
    </TimeList>
  );
};

interface ReservationTimeListProps {
  data: TimeSchedule[];
  onClickTime: (id: number, isPossible?: boolean) => void;
  selectedTimeId: number | null;
  onClickReservation: (scheduleId: number) => void;
}

export const ReservationTimeList = ({
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

TimeList.Time = Time;
TimeList.Scroll = Scroll;
TimeList.Controls = Controls;
TimeList.Divider = Divider;
TimeList.SelectAllButton = SelectAllButton;
TimeList.ConfirmButton = ConfirmButton;
TimeList.SelectedTime = SelectedTime;
TimeList.ReservationButton = ReservationButton;

export default TimeList;
