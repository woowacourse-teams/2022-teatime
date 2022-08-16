import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Card from '@components/Card';
import { LOCAL_DB, ROUTES } from '@constants/index';
import type { Coach } from '@typings/domain';
import { getStorage } from '@utils/localStorage';
import api from '@api/index';
import * as S from './styles';

const Crew = () => {
  const navigate = useNavigate();
  const { token } = getStorage(LOCAL_DB.USER);
  const [coaches, setCoaches] = useState<Coach[]>();

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.get(`/api/v2/coaches'`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setCoaches(data);
      } catch (error) {
        console.log(error);
      }
    })();
  }, []);

  const handleClickCard = (id: number) => {
    navigate(`${ROUTES.RESERVATION}/${id}`);
  };

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
