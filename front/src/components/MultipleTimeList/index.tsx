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
        {selectedTimes.map((t) => (
          <S.MultipleTimeBox
            key={t.id}
            isSelected={t.isSelected}
            onClick={() => onClickTime(t.time)}
          >
            {t.time}
          </S.MultipleTimeBox>
        ))}
        <button onClick={onClickUpdateMultipleDaySchedule}>확인</button>
      </S.ScrollContainer>
    </S.MultipleTimeListContainer>
  );
};

export default MultipleTimeList;
