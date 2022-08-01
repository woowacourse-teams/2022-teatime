import { useContext, useState } from 'react';
import * as S from './styles';

import api from '@api/index';
import Frame from '@components/Frame';
import Textarea from '@components/Textarea';
import Title from '@components/Title';
import { ScheduleStateContext } from '@context/ScheduleProvider';

const InterviewForm = () => {
  const { coach, date, time } = useContext(ScheduleStateContext);
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

  return (
    <Frame>
      <S.InfoContainer>
        {/* <img src={coach.image} alt="코치 프로필 이미지" />
        <h3>{coach.name}</h3>
        <p>{date}</p>
        <p>{time}</p> */}
        <img
          src="https://user-images.githubusercontent.com/48676844/177775689-096b53fd-a9f2-44e6-9daf-73e4e0b9a603.png"
          alt="코치 프로필 이미지"
        />
        <h3>포비</h3>
        <p>🗓 7월 28일</p>
        <p>🕖 11 : 00</p>
      </S.InfoContainer>
      <S.InterviewContainer>
        <Title text="면담 내용 작성" />
        <form>
          <Textarea
            id="0"
            label="이번 면담을 통해 논의하고 싶은 내용"
            value={contents[0].answerContent}
            handleChangeContent={handleChangeContent(0)}
            isSubmit={isSubmit}
          />
          <Textarea
            id="1"
            label="최근에 자신이 긍정적으로 보는 시도와 변화"
            value={contents[1].answerContent}
            handleChangeContent={handleChangeContent(1)}
            isSubmit={isSubmit}
          />
          <Textarea
            id="2"
            label="이번 면담을 통해 생기기를 원하는 변화"
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
