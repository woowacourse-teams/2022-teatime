import useFetch from '@hooks/useFetch';

interface Coach {
  id: number;
  name: string;
  description: string;
  image: string;
}

const Home = () => {
  const { data: coaches } = useFetch<Coach[]>('/coaches');

  return (
    <div>
      {coaches?.map((coach: Coach) => {
        return <div key={coach.id}>{coach.name}</div>;
      })}
    </div>
  );
};

export default Home;
