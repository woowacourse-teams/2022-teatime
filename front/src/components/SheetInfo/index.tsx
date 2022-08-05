import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import dayjs from 'dayjs';

import Textarea from '@components/Textarea';
import Title from '@components/Title';
import useFetch from '@hooks/useFetch';
import api from '@api/index';
import { ReservationInfo } from '@typings/domain';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';
import ClockIcon from '@assets/clock.svg';

interface SheetInfoProps {
  isCoach?: boolean;
  isView?: boolean;
  title: string;
  firstButton: string;
  secondButton: string;
}

const SheetInfo = ({ isCoach, isView, title, firstButton, secondButton }: SheetInfoProps) => {
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

  const handleSubmit = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    let status = 'WRITING';

    if (e.currentTarget.innerText === '제출하기') {
      const checkValidation = contents.some((content) => !content.answerContent);
      setIsSubmit(true);
      status = 'SUBMITTED';
      if (checkValidation) return;
    }
    try {
      await api.put(`/api/crews/me/reservations/${reservationId}`, { status, sheets: contents });
      alert('제출 되었습니다✅');
      navigate('/crew');
    } catch (error) {
      alert('제출 실패🚫');
    }
  };

  useEffect(() => {
    if (isView && reservationInfo) {
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
          <span>{dayjs.tz(reservationInfo?.dateTime).format(' MM월 DD일')}</span>
        </S.DateWrapper>
        <S.DateWrapper>
          <img src={ClockIcon} alt="시계 아이콘" />
          <span>{dayjs.tz(reservationInfo?.dateTime).format(' HH:mm')}</span>
        </S.DateWrapper>
      </S.InfoContainer>
      <S.SheetContainer>
        <Title text={title} />
        <form>
          <Textarea
            id="0"
            label={reservationInfo?.sheets[0].questionContent}
            value={contents[0].answerContent}
            handleChangeContent={handleChangeContent(0)}
            isSubmit={isSubmit}
            isCoach={isCoach}
          />
          <Textarea
            id="1"
            label={reservationInfo?.sheets[1].questionContent}
            value={contents[1].answerContent}
            handleChangeContent={handleChangeContent(1)}
            isSubmit={isSubmit}
            isCoach={isCoach}
          />
          <Textarea
            id="2"
            label={reservationInfo?.sheets[2].questionContent}
            value={contents[2].answerContent}
            handleChangeContent={handleChangeContent(2)}
            isSubmit={isSubmit}
            isCoach={isCoach}
          />
          <S.ButtonContainer>
            <S.FirstButton onClick={handleSubmit}>{firstButton}</S.FirstButton>
            <S.SecondButton onClick={handleSubmit}>{secondButton}</S.SecondButton>
          </S.ButtonContainer>
        </form>
      </S.SheetContainer>
    </>
  );
};

export default SheetInfo;
