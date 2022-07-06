import { useNavigate } from 'react-router-dom';
import Card from '@components/Card';
import useFetch from '@hooks/useFetch';
import type { Coach } from '@typings/domain';
import { HomeContainer } from './styles';

const Home = () => {
  const navigate = useNavigate();
  const { data: coaches } = useFetch<Coach[]>('/coaches');

  const handleClickCard = (id: number) => {
    navigate(`/schedule/${id}`);
  };

  return (
    <HomeContainer>
      {coaches?.map((coach) => {
        return <Card key={coach.id} coach={coach} onClick={handleClickCard} />;
      })}
    </HomeContainer>
  );
};

export default Home;
