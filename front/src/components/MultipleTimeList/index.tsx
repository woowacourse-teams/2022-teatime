import { MultipleTime } from '@typings/domain';
import * as S from './styles';

interface MultipleTimeListProps {
  selectedTimes: MultipleTime[];
  onClickTime: (time: string) => void;
  onClickUpdateMultipleDaySchedule: () => Promise<void>;
}

const MultipleTimeList = ({
  selectedTimes,
  onClickTime,
  onClickUpdateMultipleDaySchedule,
}: MultipleTimeListProps) => {
  return (
    <S.MultipleTimeListContainer>
      <S.ScrollContainer>
        {selectedTimes.map(({ id, time, isSelected }) => (
          <S.MultipleTimeBox key={id} isSelected={isSelected} onClick={() => onClickTime(time)}>
            {time}
          </S.MultipleTimeBox>
        ))}
      </S.ScrollContainer>

      <S.ConfirmButton onClick={onClickUpdateMultipleDaySchedule}>확인</S.ConfirmButton>
    </S.MultipleTimeListContainer>
  );
};

export default MultipleTimeList;
