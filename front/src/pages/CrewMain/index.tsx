import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Card from '@components/Card';
import EmptyContent from '@components/EmptyContent';
import { UserDispatchContext } from '@context/UserProvider';
import { SnackbarContext } from '@context/SnackbarProvider';
import { CACHE, ROUTES } from '@constants/index';
import { cacheFetch } from '@utils/cacheFetch';
import { getCoaches } from '@api/coach';
import type { Coach } from '@typings/domain';
import * as S from './styles';

const CrewMain = () => {
  const navigate = useNavigate();
  const showSnackbar = useContext(SnackbarContext);
  const dispatch = useContext(UserDispatchContext);
  const [coaches, setCoaches] = useState<Coach[]>();

  const handleClickCard = (id: number, image: string) => {
    navigate(`${ROUTES.RESERVATION}/${id}`, { state: image });
  };

  useEffect(() => {
    (async () => {
      try {
        const { data } = await cacheFetch(CACHE.KEY, getCoaches, CACHE.TIME);
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

  if (!coaches) return <EmptyContent text={'등록된 사용자가 없습니다.'} />;

  return (
    <S.Layout>
      <S.CardListContainer>
        {coaches.map((coach) => {
          const { id, name, image, description, isPossible } = coach;
          return (
            <Card
              key={id}
              name={name}
              image={image}
              description={description}
              buttonName="예약하기"
              onClick={() => handleClickCard(id, image)}
              isPossible={isPossible}
            />
          );
        })}
      </S.CardListContainer>
    </S.Layout>
  );
};

export default CrewMain;
