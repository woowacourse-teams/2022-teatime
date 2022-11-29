import TimeList from '.';
import { getDateTime, getHourMinutes } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';

interface ScheduleTimeListProps {
  data: TimeSchedule[];
  onClickTime: (dateTime: string) => void;
  onSelectAll: () => void;
  onSubmit: () => Promise<void>;
  isSelectedAll: boolean;
}

const ScheduleTimeList = ({
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

export default ScheduleTimeList;
