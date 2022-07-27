import { useNavigate } from 'react-router-dom';
import { Layout } from './styles';

const Coach = () => {
  const navigate = useNavigate();
  const handleClickCard = () => {
    navigate(`/schedule/41`);
  };

  return (
    <Layout>
      <button onClick={handleClickCard}>일정 추가</button>
    </Layout>
  );
};

export default Coach;
