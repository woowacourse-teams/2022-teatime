import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Card from '@components/Card';
import useSnackbar from '@hooks/useSnackbar';
import { UserDispatchContext, UserStateContext } from '@context/UserProvider';
import { ROUTES } from '@constants/index';
import type { Coach } from '@typings/domain';
import api from '@api/index';
import * as S from './styles';

const Crew = () => {
  const navigate = useNavigate();
  const showSnackbar = useSnackbar();
  const { userData } = useContext(UserStateContext);
  const dispatch = useContext(UserDispatchContext);
  const [coaches, setCoaches] = useState<Coach[]>();

  const handleClickCard = (id: number) => {
    navigate(`${ROUTES.RESERVATION}/${id}`);
  };

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.get(`/api/v2/coaches`, {
          headers: {
            Authorization: `Bearer ${userData?.token}`,
          },
        });
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

export default Crew;
