import { useNavigate } from 'react-router-dom';
import Card from '@components/Card';
import useFetch from '@hooks/useFetch';
import type { Coach } from '@typings/domain';
import { ROUTES } from '@constants/index';
import { CardListContainer, Layout } from './styles';
import Loading from '@components/Loading/index';

const Home = () => {
  const navigate = useNavigate();
  const { data: coaches, isLoading, isError } = useFetch<Coach[], null>('/api/coaches');

  const handleClickCard = (id: number) => {
    navigate(`${ROUTES.RESERVATION}/${id}`);
  };

  if (isError) return <h1>error</h1>;
  if (isLoading) return <Loading />;

  return (
    <Layout>
      <CardListContainer>
        {coaches?.map((coach) => {
          return (
            <Card
              key={coach.id}
              name={coach.name}
              image={coach.image}
              description={coach.description}
              buttonName="예약하기"
              onClick={() => handleClickCard(coach.id)}
            />
          );
        })}
      </CardListContainer>
    </Layout>
  );
};

export default Home;
