import Card from '@components/Card';
import useFetch from '@hooks/useFetch';
import type { Coach } from '@typings/domain';
import { HomeContainer } from './styles';

const Home = () => {
  const { data: coaches } = useFetch<Coach[]>('/coaches');

  return (
    <HomeContainer>
      {coaches?.map((coach) => {
        return <Card key={coach.id} coach={coach} />;
      })}
    </HomeContainer>
  );
};

export default Home;
