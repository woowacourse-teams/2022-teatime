import * as S from './styles';

const Home = () => {
  const handleLogin = () => {
    location.href =
      'https://slack.com/oauth/v2/authorize?scope=users.profile:read,users:read,users:read.email&user_scope=openid&client_id=3853132979991.3908459157459';
  };

  return (
    <S.Login>
      <button onClick={handleLogin}>❃ Slack 로그인</button>
    </S.Login>
  );
};

export default Home;
