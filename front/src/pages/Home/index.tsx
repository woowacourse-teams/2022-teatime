import { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { UserStateContext } from '@context/UserProvider';

import SlackIcon from '@assets/slack.svg';
import * as S from './styles';

const Home = () => {
  const navigate = useNavigate();
  const { userData } = useContext(UserStateContext);

  const handleLogin = () => {
    location.href =
      'https://slack.com/openid/connect/authorize?response_type=code&scope=openid%20profile%20email&client_id=3853132979991.3908459157459&redirect_uri=https://teatime.pe.kr/certification';
  };

  useEffect(() => {
    if (userData) {
      navigate(`/${userData.role.toLowerCase()}`);
    }
  }, [userData]);

  return (
    <S.Container>
      <S.MainSection>
        <S.OverlapContent>
          <h2>서로를 채워주는 시간</h2>
          <h2>서로를 채워주는 시간</h2>
        </S.OverlapContent>
        <S.OverlapContent>
          <h2>티타임</h2>
          <h2>티타임</h2>
        </S.OverlapContent>
        <S.Description>
          <p>티타임은 면담 예약을 편리하게 도와주는 서비스입니다.</p>
          <p>슬랙로그인으로 시작해보세요!</p>
        </S.Description>
        <S.ButtonContainer>
          <S.SlackButton onClick={handleLogin}>
            <img src={SlackIcon} alt="슬랙 아이콘" />
            <span>Slack으로 로그인</span>
          </S.SlackButton>
          <S.TeamIntroButton onClick={() => alert('준비중입니다.')}>
            <span>팀원 소개</span>
          </S.TeamIntroButton>
        </S.ButtonContainer>
      </S.MainSection>
    </S.Container>
  );
};

export default Home;
