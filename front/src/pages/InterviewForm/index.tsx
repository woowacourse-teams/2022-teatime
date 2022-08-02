import { useState, useEffect } from 'react';
import * as S from './styles';

import api from '@api/index';
import Frame from '@components/Frame';
import Textarea from '@components/Textarea';
import Title from '@components/Title';
import useFetch from '@hooks/useFetch';
import { InterviewInfo } from '@typings/domain';

const InterviewForm = () => {
  const { data: interviewInfo } = useFetch<InterviewInfo, null>('/api/crews/me/reservations/1');
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

    if (e.currentTarget.innerText === '제출하기') {
      const checkValidation = contents.some((content) => !content.answerContent);
      setIsSubmit(true);
      if (checkValidation) return;
    }

    try {
      await api.put(`/api/crews/me/reservations/1`, contents);
      alert('제출 되었습니다✅');
    } catch (error) {
      alert('제출 실패🚫');
    }
  };

  useEffect(() => {
    if (interviewInfo) {
      setContents(interviewInfo.sheets);
    }
  }, [interviewInfo]);

  return (
    <Frame>
      <S.InfoContainer>
        <img src={interviewInfo?.coachImage} alt="코치 프로필 이미지" />
        <h3>{interviewInfo?.coachName}</h3>
        <p>{interviewInfo?.dateTime}</p>
        <p>{interviewInfo?.dateTime}</p>
      </S.InfoContainer>
      <S.InterviewContainer>
        <Title text="면담 내용 작성" />
        <form>
          <Textarea
            id="0"
            label={interviewInfo?.sheets[0].questionContent}
            value={contents[0].answerContent}
            handleChangeContent={handleChangeContent(0)}
            isSubmit={isSubmit}
          />
          <Textarea
            id="1"
            label={interviewInfo?.sheets[1].questionContent}
            value={contents[1].answerContent}
            handleChangeContent={handleChangeContent(1)}
            isSubmit={isSubmit}
          />
          <Textarea
            id="2"
            label={interviewInfo?.sheets[2].questionContent}
            value={contents[2].answerContent}
            handleChangeContent={handleChangeContent(2)}
            isSubmit={isSubmit}
          />
          <S.ButtonContainer>
            <S.SaveButton onClick={handleSubmit}>임시 저장</S.SaveButton>
            <S.SubmitButton onClick={handleSubmit}>제출하기</S.SubmitButton>
          </S.ButtonContainer>
        </form>
      </S.InterviewContainer>
    </Frame>
  );
};

export default InterviewForm;
