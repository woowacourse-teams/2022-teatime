import type { PropsWithRequiredChildren } from '@typings/utils';
import * as S from './styles';

interface TimeProps {
  onClick: () => void;
  isPastTime?: boolean;
  isPossible?: boolean;
  dateTime?: string;
  index?: number;
}

interface ButtonProps {
  onClick: () => void;
}

const TimeList = ({ children }: PropsWithRequiredChildren) => {
  return <S.TimeListContainer>{children}</S.TimeListContainer>;
};

const Scroll = ({ children }: PropsWithRequiredChildren) => {
  return <S.ScrollContainer>{children}</S.ScrollContainer>;
};

const Time = ({
  children,
  isPossible,
  dateTime,
  index,
  ...props
}: PropsWithRequiredChildren<TimeProps>) => {
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

const Controls = ({ children }: PropsWithRequiredChildren) => {
  return <S.Controls>{children}</S.Controls>;
};

const SelectAllButton = ({ children, ...props }: PropsWithRequiredChildren<ButtonProps>) => {
  return <S.SelectAllButton {...props}>{children}</S.SelectAllButton>;
};

const ConfirmButton = ({ children, ...props }: PropsWithRequiredChildren<ButtonProps>) => {
  return <S.ConfirmButton {...props}>{children}</S.ConfirmButton>;
};

const Divider = ({ children }: PropsWithRequiredChildren) => {
  return <S.Divider>{children}</S.Divider>;
};

const SelectedTime = ({ children }: PropsWithRequiredChildren) => {
  return <div>{children}</div>;
};

const ReservationButton = ({ children, ...props }: PropsWithRequiredChildren<ButtonProps>) => {
  return (
    <button {...props} autoFocus>
      {children}
    </button>
  );
};

TimeList.Time = Time;
TimeList.Scroll = Scroll;
TimeList.Controls = Controls;
TimeList.SelectAllButton = SelectAllButton;
TimeList.ConfirmButton = ConfirmButton;
TimeList.Divider = Divider;
TimeList.SelectedTime = SelectedTime;
TimeList.ReservationButton = ReservationButton;

export default TimeList;
