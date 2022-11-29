import TimeList from '.';
import type { MultipleTime } from '@typings/domain';

interface MultipleTimeListProps {
  data: MultipleTime[];
  onClickTime: (dateTime: string) => void;
  onSubmit: () => Promise<void>;
}

const MultipleTimeList = ({ data, onClickTime, onSubmit }: MultipleTimeListProps) => {
  return (
    <TimeList>
      <TimeList.Scroll>
        {data.map(({ id, dateTime, ...props }) => (
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

export default MultipleTimeList;
