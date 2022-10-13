import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Card from '@components/Card';
import EmptyContent from '@components/EmptyContent';
import Modal from '@components/Modal';
import useBoolean from '@hooks/useBoolean';
import { UserDispatchContext, UserStateContext } from '@context/UserProvider';
import { SnackbarContext } from '@context/SnackbarProvider';
import { CACHE, ROUTES } from '@constants/index';
import { cacheFetch } from '@utils/cacheFetch';
import { getCoaches } from '@api/coach';
import { postReservationRequest } from '@api/crew';
import type { Coach } from '@typings/domain';
import * as S from './styles';

const CrewMain = () => {
  const navigate = useNavigate();
  const { userData } = useContext(UserStateContext);
  const showSnackbar = useContext(SnackbarContext);
  const dispatch = useContext(UserDispatchContext);
  const { value: isOpenModal, setTrue: openModal, setFalse: closeModal } = useBoolean();
  const [coaches, setCoaches] = useState<Coach[]>();
  const [selectedCoach, setSelectedCoach] = useState({
    id: 0,
    image: 'https://i.pinimg.com/564x/8f/e9/9f/8fe99f6f8549200d77c8ce62cee1903c.jpg',
  });

  const handleClickCard = (e: React.MouseEvent, id: number, image: string) => {
    const target = (e.target as HTMLImageElement).id;
    if (target === 'request-icon') {
      setSelectedCoach({ id, image });
      openModal();
      return;
    }
    navigate(`${ROUTES.RESERVATION}/${id}`, { state: image });
  };

  const handleReservationRequest = async () => {
    try {
      await postReservationRequest(selectedCoach.id);
      showSnackbar({ message: '알림을 보냈습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
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
              onClick={(e) => handleClickCard(e, id, image)}
              isPossible={isPossible}
            />
          );
        })}
      </S.CardListContainer>
      {isOpenModal && (
        <Modal
          title="콕! 찔러보기"
          firstButtonName="뒤로가기"
          secondButtonName="보내기"
          onClickFirstButton={() => navigate(-1)}
          onClickSecondButton={() => handleReservationRequest()}
          closeModal={closeModal}
        >
          <S.ImageWrapper>
            <img src={selectedCoach.image} alt="코치 이미지" />
            <span>💌</span>
            <img src={userData?.image} alt="크루 이미지" />
          </S.ImageWrapper>
        </Modal>
      )}
    </S.Layout>
  );
};

export default CrewMain;
