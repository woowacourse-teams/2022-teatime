import { useContext } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { AxiosError } from 'axios';

import { UserDispatchContext } from '@context/UserProvider';
import { api } from '@api/index';
import * as S from './styles';

const DemoHome = () => {
  const navigate = useNavigate();
  const dispatch = useContext(UserDispatchContext);
  const { state: nickname } = useLocation();

  const handleUserButton = async (role: string) => {
    try {
      const { data: userData } = await api.post('/api/auth/login/v2', {
        name: nickname,
        role: role.toUpperCase(),
      });
      dispatch({ type: 'SET_USER', userData });
      navigate(`/${role}`, { replace: true });
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
  };

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
          <S.MentorButton onClick={() => handleUserButton('coach')}>멘토 체험하기</S.MentorButton>
          <S.MenteeButton onClick={() => handleUserButton('crew')}>멘티 체험하기</S.MenteeButton>
        </S.ButtonContainer>
      </S.MainSection>
    </S.Container>
  );
};

export default DemoHome;
