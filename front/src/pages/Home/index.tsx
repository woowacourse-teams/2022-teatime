import { useNavigate } from 'react-router-dom';
import Card from '@components/Card';
import useFetch from '@hooks/useFetch';
import type { Coach } from '@typings/domain';
import { HomeContainer } from './styles';

const Home = () => {
  const navigate = useNavigate();
  const { data: coaches } = useFetch<Coach[]>('/coaches');

  const handleCardClick = (id: number) => {
    navigate(`/schedule/${id}`);
  };

  return (
    <HomeContainer>
      {coaches?.map((coach) => {
        return <Card key={coach.id} coach={coach} onClick={handleCardClick} />;
      })}
    </HomeContainer>
  );
};

export default Home;
