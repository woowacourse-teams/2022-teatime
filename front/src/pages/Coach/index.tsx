import { useNavigate } from 'react-router-dom';
import { Layout, Menu } from './styles';

const Coach = () => {
  const navigate = useNavigate();
  const handleClickCard = () => {
    navigate(`/schedule/1`);
  };

  return (
    <Layout>
      <Menu>
        <button onClick={handleClickCard}>일정 추가</button>
      </Menu>
    </Layout>
  );
};

export default Coach;
