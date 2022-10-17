import { useNavigate } from 'react-router-dom';

import * as S from './styles';
import { ROUTES } from '@constants/index';

const DemoHome = () => {
  const navigate = useNavigate();

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
        </S.Description>
        <S.ButtonContainer>
          <S.MentorButton onClick={() => navigate(ROUTES.COACH)}>멘토 체험하기</S.MentorButton>
          <S.MenteeButton onClick={() => navigate(ROUTES.CREW)}>멘티 체험하기</S.MenteeButton>
        </S.ButtonContainer>
      </S.MainSection>
    </S.Container>
  );
};

export default DemoHome;
