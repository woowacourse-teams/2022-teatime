import { useNavigate } from 'react-router-dom';
import BoardItem from '@components/BoardItem';
import useFetch from '@hooks/useFetch';
import { Crew } from '@typings/domain';
import * as S from './styles';

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
        <S.Board>
          <div
            style={{
              display: 'flex',
              alignItems: 'center',
              width: '100%',
              borderBottom: '3px solid #FFA500',
              paddingBottom: 15,
              marginBottom: 15,
            }}
          >
            <div
              style={{
                width: 8,
                height: 8,
                backgroundColor: '#FFA500',
                borderRadius: 5,
                marginRight: 10,
              }}
            />
            <span style={{ fontWeight: 'bold' }}>대기중인 일정</span>
          </div>
          <div
            style={{
              width: '100%',
              height: '100%',
              overflow: 'scroll',
              padding: '0 10px',
            }}
          >
            {crews?.map((crew) => {
              return <BoardItem key={crew.id} buttonName="승인하기" {...crew} />;
            })}
          </div>
        </S.Board>
        <S.Board></S.Board>
        <S.Board></S.Board>
      </div>
    </S.Layout>
  );
};

export default Coach;
