import { useNavigate } from 'react-router-dom';
import BoardItem from '@components/BoardItem';
import useFetch from '@hooks/useFetch';
import { Crew } from '@typings/domain';
import * as S from './styles';
import Board from '@components/Board';

const Coach = () => {
  const navigate = useNavigate();
  const { data: crews } = useFetch<Crew[], null>('/api/crews');

  const handleClickCard = () => {
    navigate(`/schedule/41`);
  };

  return (
    <S.Layout>
      <div style={{ height: 50, display: 'flex', justifyContent: 'flex-end' }}>
        <button onClick={handleClickCard}>일정 추가</button>
      </div>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <Board boardTitle="대기중인 일정">
          {crews?.map((crew) => {
            return (
              <BoardItem
                key={crew.id}
                buttonName="승인하기"
                dateTime={crew.dateTime}
                personName={crew.name}
              />
            );
          })}
        </Board>
      </div>
    </S.Layout>
  );
};

export default Coach;
