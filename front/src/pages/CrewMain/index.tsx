import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Card from '@components/Card';
import { UserDispatchContext } from '@context/UserProvider';
import { SnackbarContext } from '@context/SnackbarProvider';
import { ROUTES } from '@constants/index';
import { getCoaches } from '@api/coach';
import type { Coach } from '@typings/domain';
import * as S from './styles';

const CrewMain = () => {
  const navigate = useNavigate();
  const showSnackbar = useContext(SnackbarContext);
  const dispatch = useContext(UserDispatchContext);
  const [coaches, setCoaches] = useState<Coach[]>();

  const handleClickCard = (id: number) => {
    navigate(`${ROUTES.RESERVATION}/${id}`);
  };

  useEffect(() => {
    (async () => {
      try {
        const { data } = await getCoaches();
        setCoaches(data);
      } catch (error) {
        if (error instanceof AxiosError) {
          if (error.response?.status === 401) {
            dispatch({ type: 'DELETE_USER' });
            showSnackbar({ message: '토큰이 만료되었습니다. 다시 로그인해주세요.' });
            navigate(ROUTES.HOME);
            return;
          }
          alert(error);
          console.log(error);
        }
      }
    })();
  }, []);

  return (
    <S.Layout>
      <S.CardListContainer>
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
      </S.CardListContainer>
    </S.Layout>
  );
};

export default CrewMain;
