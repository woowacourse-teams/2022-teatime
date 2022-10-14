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
    image: '',
  });

  const handleClickCard = (e: React.MouseEvent, id: number, image: string, isPokable: boolean) => {
    const target = (e.target as HTMLImageElement).id;
    if (target === 'request') {
      if (!isPokable) return showSnackbar({ message: '상대방이 OFF 상태 입니다.' });

      setSelectedCoach({ id, image });
      openModal();
      return;
    }
    if (target === 'subscribe') {
      showSnackbar({ message: '구독 기능 준비 중입니다. ㅠㅠ' });
      return;
    }

    navigate(`${ROUTES.RESERVATION}/${id}`, { state: image });
  };

  const handleReservationRequest = async () => {
    try {
      await postReservationRequest(selectedCoach.id);
      showSnackbar({ message: '요청 보냈습니다. 💌' });
      closeModal();
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
          const { id, name, image, description, isPossible, isPokable } = coach;
          return (
            <Card
              key={id}
              name={name}
              image={image}
              description={description}
              buttonName="예약하기"
              onClick={(e) => handleClickCard(e, id, image, isPokable)}
              isPossible={isPossible}
            />
          );
        })}
      </S.CardListContainer>
      {isOpenModal && (
        <Modal
          title="콕! 찔러보기"
          firstButtonName="취소하기"
          secondButtonName="요청하기"
          onClickFirstButton={() => closeModal()}
          onClickSecondButton={() => handleReservationRequest()}
          closeModal={closeModal}
        >
          <div>
            <S.ImageWrapper>
              <img src={selectedCoach.image} alt="코치 이미지" />
              <span>💌</span>
              <img src={userData?.image} alt="크루 이미지" />
            </S.ImageWrapper>
            <S.RequestText>
              가능한 시간이 없나요?
              <br />
              상대방에게 알림을 보내보세요 ☕️
            </S.RequestText>
          </div>
        </Modal>
      )}
    </S.Layout>
  );
};

export default CrewMain;
