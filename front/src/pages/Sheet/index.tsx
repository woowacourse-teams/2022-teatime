import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import Frame from '@components/Frame';
import Textarea from '@components/Textarea';
import Title from '@components/Title';
import useFetch from '@hooks/useFetch';
import api from '@api/index';
import { ReservationInfo } from '@typings/domain';
import { getHourMinutes, getMonthDate } from '@utils/index';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';
import ClockIcon from '@assets/clock.svg';
import LeftArrowIcon from '@assets/left-arrow-disabled.svg';

const Sheet = () => {
  const { id: reservationId } = useParams();
  const navigate = useNavigate();
  const { data: reservationInfo } = useFetch<ReservationInfo, null>(
    `/api/crews/me/reservations/${reservationId}`
  );
  const [isSubmit, setIsSubmit] = useState<boolean>(false);
  const [contents, setContents] = useState([
    {
      questionNumber: 1,
      questionContent: '',
      answerContent: '',
    },
    {
      questionNumber: 2,
      questionContent: '',
      answerContent: '',
    },
    {
      questionNumber: 3,
      questionContent: '',
      answerContent: '',
    },
  ]);
  const isRead = reservationInfo?.status === 'SUBMITTED';

  const handleChangeContent = (index: number) => (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContents((prevContent) => {
      const newContent = [...prevContent];
      newContent[index].answerContent = e.target.value;
      return newContent;
    });
  };

  const handleSubmit = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const isSubmitted = e.currentTarget.innerText === '제출하기';
    if (isSubmitted) {
      setIsSubmit(true);
      const checkValidation = contents.some((content) => !content.answerContent);
      if (checkValidation) return;
    }

    try {
      await api.put(`/api/crews/me/reservations/${reservationId}`, {
        status: isSubmitted ? 'SUBMITTED' : 'WRITING',
        sheets: contents,
      });
      alert('제출 되었습니다✅');
      navigate('/crew');
    } catch (error) {
      alert('제출 실패🚫');
    }
  };

  useEffect(() => {
    console.log('reservationInfo', reservationInfo);
    reservationInfo && setContents(reservationInfo.sheets);
  }, [reservationInfo]);

  return (
    <Frame>
      <S.InfoContainer>
        <S.CoachImg src={reservationInfo?.coachImage} alt="코치 프로필 이미지" />
        <p>{reservationInfo?.coachName}</p>
        <S.DateWrapper>
          <img src={ScheduleIcon} alt="일정 아이콘" />
          <span>{getMonthDate(reservationInfo?.dateTime as string)}</span>
        </S.DateWrapper>
        <S.DateWrapper>
          <img src={ClockIcon} alt="시계 아이콘" />
          <span>{getHourMinutes(reservationInfo?.dateTime as string)}</span>
        </S.DateWrapper>
        {reservationInfo?.status === 'SUBMITTED' && (
          <S.ArrowIcon src={LeftArrowIcon} alt="화살표 아이콘" onClick={() => navigate(-1)} />
        )}
      </S.InfoContainer>
      <S.SheetContainer>
        <Title text="면담 내용" />
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
          {reservationInfo?.status === 'WRITING' && (
            <S.ButtonContainer>
              <S.FirstButton onClick={handleSubmit}>임시저장</S.FirstButton>
              <S.SecondButton onClick={handleSubmit}>제출하기</S.SecondButton>
            </S.ButtonContainer>
          )}
        </form>
      </S.SheetContainer>
    </Frame>
  );
};

export default Sheet;
