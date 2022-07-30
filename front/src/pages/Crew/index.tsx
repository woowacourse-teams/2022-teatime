import { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import Card from '@components/Card';
import useFetch from '@hooks/useFetch';
import type { Coach } from '@typings/domain';
import { ROUTES } from '@constants/index';
import * as S from './styles';
import { ScheduleDispatchContext } from '@context/ScheduleProvider';

const Crew = () => {
  const navigate = useNavigate();
  const dispatch = useContext(ScheduleDispatchContext);
  const { data: coaches, isLoading, isError } = useFetch<Coach[], null>('/api/coaches');

  const handleClickCard = (id: number, name: string, image: string) => {
    dispatch({ type: 'SELECT_COACH', name, image });
    navigate(`${ROUTES.RESERVATION}/${id}`);
  };

  if (isError) return <h1>error</h1>;

  return (
    <S.Layout>
      <S.CardListContainer>
        {coaches?.map((coach) => {
          return (
            <Card
              key={coach.id}
              name={coach.name}
              image={coach.image}
              description={coach.description}
              buttonName="예약하기"
              onClick={() => handleClickCard(coach.id, coach.name, coach.image)}
            />
          );
        })}
      </S.CardListContainer>
    </S.Layout>
  );
};

export default Crew;
