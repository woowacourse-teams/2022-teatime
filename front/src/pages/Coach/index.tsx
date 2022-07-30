import { useNavigate } from 'react-router-dom';
import BoardItem from '@components/BoardItem';
import Board from '@components/Board';
import useFetch from '@hooks/useFetch';
import type { Crew } from '@typings/domain';
import PlusIcon from '@assets/plus.svg';
import * as S from './styles';

const Coach = () => {
  const navigate = useNavigate();
  const { data: crews } = useFetch<Crew[], null>('/api/crews');

  const handleClickCard = () => {
    navigate(`/schedule/41`);
  };

  return (
    <S.Layout>
      <S.BoardListHeader>
        <S.AddScheduleButton onClick={handleClickCard}>
          <img src={PlusIcon} alt="추가 아이콘" />
          <span>일정 추가</span>
        </S.AddScheduleButton>
      </S.BoardListHeader>
      <S.BoardListContainer>
        <Board boardTitle="대기중인 일정">
          {crews?.map((crew) => (
            <BoardItem
              key={crew.id}
              dateTime={crew.dateTime}
              personName={crew.name}
              buttonName="승인하기"
            />
          ))}
        </Board>
        <Board boardTitle="확정된 일정">
          <></>
        </Board>
        <Board boardTitle="완료된 일정">
          <></>
        </Board>
      </S.BoardListContainer>
    </S.Layout>
  );
};

export default Coach;
