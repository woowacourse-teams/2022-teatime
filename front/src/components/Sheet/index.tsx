import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import Textarea from '@components/Textarea';
import Title from '@components/Title';
import useFetch from '@hooks/useFetch';
import api from '@api/index';
import { ReservationInfo } from '@typings/domain';
import { getHourMinutes, getMonthDate } from '@utils/index';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';
import ClockIcon from '@assets/clock.svg';

interface SheetInfoProps {
  isRead?: boolean;
  title: string;
  firstButton: string;
  secondButton: string;
}

const Sheet = ({ isRead, title, firstButton, secondButton }: SheetInfoProps) => {
  const { id: reservationId } = useParams();
  const navigate = useNavigate();
  const { data: reservationInfo } = useFetch<ReservationInfo, null>(
    `/api/crews/me/reservations/${reservationId}`
  );
  const [isSubmit, setIsSubmit] = useState(false);
  const [contents, setContents] = useState([
    {
      questionNumber: 1,
      answerContent: '',
    },
    {
      questionNumber: 2,
      answerContent: '',
    },
    {
      questionNumber: 3,
      answerContent: '',
    },
  ]);

  const handleChangeContent = (index: number) => (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContents((prevContent) => {
      const newContent = [...prevContent];
      newContent[index].answerContent = e.target.value;
      return newContent;
    });
  };

  const handleClickFirstButton = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    if (isRead) {
      navigate(-1);
      return;
    }

    try {
      await api.put(`/api/crews/me/reservations/${reservationId}`, {
        status: 'WRITING',
        sheets: contents,
      });
      alert('제출 되었습니다✅');
      navigate('/crew');
    } catch (error) {
      alert('제출 실패🚫');
    }
  };

  const handleClickSecondButton = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    if (isRead) {
      navigate('/');
      return;
    }

    const checkValidation = contents.some((content) => !content.answerContent);
    setIsSubmit(true);
    if (checkValidation) return;

    try {
      await api.put(`/api/crews/me/reservations/${reservationId}`, {
        status: 'SUBMITTED',
        sheets: contents,
      });
      alert('제출 되었습니다✅');
      navigate('/crew');
    } catch (error) {
      alert('제출 실패🚫');
    }
  };

  useEffect(() => {
    if (isRead && reservationInfo) {
      setContents(reservationInfo.sheets);
    }
  }, [reservationInfo]);

  return (
    <>
      <S.InfoContainer>
        <img src={reservationInfo?.coachImage} alt="코치 프로필 이미지" />
        <p>{reservationInfo?.coachName}</p>
        <S.DateWrapper>
          <img src={ScheduleIcon} alt="일정 아이콘" />
          <span>{getMonthDate(reservationInfo?.dateTime as string)}</span>
        </S.DateWrapper>
        <S.DateWrapper>
          <img src={ClockIcon} alt="시계 아이콘" />
          <span>{getHourMinutes(reservationInfo?.dateTime as string)}</span>
        </S.DateWrapper>
      </S.InfoContainer>
      <S.SheetContainer>
        <Title text={title} />
        <form>
          <Textarea
            id="0"
            label={reservationInfo?.sheets[0].questionContent}
            value={contents[0].answerContent || ''}
            handleChangeContent={handleChangeContent(0)}
            isSubmit={isSubmit}
            isRead={isRead}
          />
          <Textarea
            id="1"
            label={reservationInfo?.sheets[1].questionContent}
            value={contents[1].answerContent || ''}
            handleChangeContent={handleChangeContent(1)}
            isSubmit={isSubmit}
            isRead={isRead}
          />
          <Textarea
            id="2"
            label={reservationInfo?.sheets[2].questionContent}
            value={contents[2].answerContent || ''}
            handleChangeContent={handleChangeContent(2)}
            isSubmit={isSubmit}
            isRead={isRead}
          />
          <S.ButtonContainer>
            <S.FirstButton onClick={handleClickFirstButton}>{firstButton}</S.FirstButton>
            <S.SecondButton onClick={handleClickSecondButton}>{secondButton}</S.SecondButton>
          </S.ButtonContainer>
        </form>
      </S.SheetContainer>
    </>
  );
};

export default Sheet;
