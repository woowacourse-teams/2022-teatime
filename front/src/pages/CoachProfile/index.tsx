import { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Card from '@components/Card';
import { CoachData } from '@typings/domain';
import { editCoachProfile, getCoachProfile } from '@api/coach';
import { UserDispatchContext } from '@context/UserProvider';
import { SnackbarContext } from '@context/SnackbarProvider';
import { MAX_LENGTH } from '@constants/index';
import * as S from './styles';

interface Question {
  questionNumber: number;
  questionContent: string;
  isRequired: boolean;
}

const CoachProfile = () => {
  const dispatch = useContext(UserDispatchContext);
  const showSnackbar = useContext(SnackbarContext);
  const [coachProfile, setCoachProfile] = useState<CoachData>({
    image: '',
    name: '',
    description: '',
  });
  const [questions, setQuestions] = useState<Question[]>([
    // {
    //   questionNumber: 1,
    //   questionContent: '이름이 뭔가요',
    //   isRequired: true,
    // },
    // {
    //   questionNumber: 2,
    //   questionContent: '별자리가 뭔가요?',
    //   isRequired: false,
    // },
    // {
    //   questionNumber: 3,
    //   questionContent: '핸드폰 기종은요',
    //   isRequired: true,
    // },
  ]);

  const { image, name, description } = coachProfile;

  const handleChangeProfile = (e: React.ChangeEvent<HTMLFormElement>) => {
    setCoachProfile((prev) => {
      return { ...prev, [e.target.name]: e.target.value };
    });
  };

  const handleChangeQuestionInput = (index: number, e: React.ChangeEvent<HTMLInputElement>) => {
    setQuestions((prev) => {
      const newState = [...prev];
      newState[index].questionContent = e.target.value;
      return newState;
    });
  };

  const handleChangeQuestionCheckBox = (index: number, e: React.ChangeEvent<HTMLInputElement>) => {
    setQuestions((prev) => {
      const newState = [...prev];
      newState[index].isRequired = e.target.checked;
      return newState;
    });
  };

  const handleSubmitProfile = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (name.length > MAX_LENGTH.NAME || description.length > MAX_LENGTH.DESCRIPTION) {
      return alert('글자 수를 조정해 주세요');
    }

    try {
      await editCoachProfile({ name, description });
      dispatch({ type: 'EDIT_USER', name });
      showSnackbar({ message: '변경되었습니다. ✅' });
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
        const { data } = await getCoachProfile();
        setCoachProfile(data);
      } catch (error) {
        if (error instanceof AxiosError) {
          alert(error.response?.data?.message);
          console.log(error);
        }
      }
    })();
  }, []);

  // useEffect(() => {
  //   (async () => {
  //     try {
  //       const { data } = await api.get<Question[]>(`/api/v2/coaches/me/questions`);
  //       console.log(data);
  //       setQuestions(data.questions);
  //     } catch (error) {
  //       if (error instanceof AxiosError) {
  //         alert(error.response?.data?.message);
  //         console.log(error);
  //       }
  //     }
  //   })();
  // }, []);

  if (!coachProfile) return <></>;

  return (
    <S.Container>
      <S.Grid>
        <S.BorderBox>
          <S.ProfileForm onChange={handleChangeProfile} onSubmit={handleSubmitProfile}>
            <S.LabelContainer>
              <label htmlFor="name">닉네임</label>
              <S.BorderBoxName>{`${name.length} / ${MAX_LENGTH.NAME}`}</S.BorderBoxName>
            </S.LabelContainer>
            <input
              id="name"
              name="name"
              type="text"
              maxLength={MAX_LENGTH.NAME}
              defaultValue={name}
              autoComplete="off"
              required
            />
            <S.LabelContainer>
              <label htmlFor="description">자기소개</label>
              <S.BorderBoxName>{`${description.length} / ${MAX_LENGTH.DESCRIPTION}`}</S.BorderBoxName>
            </S.LabelContainer>
            <textarea
              name="description"
              id="description"
              rows={7}
              maxLength={MAX_LENGTH.DESCRIPTION}
              defaultValue={description}
              required
            />
            <S.ButtonWrapper>
              <button>저장하기</button>
            </S.ButtonWrapper>
          </S.ProfileForm>
        </S.BorderBox>

        <S.PreviewBorderBox>
          <S.BorderBoxName>프로필 미리보기</S.BorderBoxName>
          <S.CardWrapper>
            <Card
              name={name}
              image={image}
              description={description}
              buttonName="예약하기"
              isPreview
            />
          </S.CardWrapper>
        </S.PreviewBorderBox>

        <S.QuestionBorderBox>
          <form>
            <S.QuestionInner>
              <S.QuestionInputContainer>
                <S.BorderBoxName>사전 질문</S.BorderBoxName>
                {Array.from({ length: 3 }, (_, index) => (
                  <input
                    key={index}
                    type="text"
                    value={questions[index]?.questionContent}
                    onChange={(e) => handleChangeQuestionInput(index, e)}
                  />
                ))}
              </S.QuestionInputContainer>
              <S.QuestionCheckBoxContainer>
                <S.BorderBoxName>필수 여부</S.BorderBoxName>
                {Array.from({ length: 3 }, (_, index) => (
                  <input
                    key={index}
                    type="checkbox"
                    checked={questions[index]?.isRequired}
                    onChange={(e) => handleChangeQuestionCheckBox(index, e)}
                  />
                ))}
              </S.QuestionCheckBoxContainer>
            </S.QuestionInner>
            <S.ButtonWrapper>
              <button>저장하기</button>
            </S.ButtonWrapper>
          </form>
        </S.QuestionBorderBox>
      </S.Grid>
    </S.Container>
  );
};

export default CoachProfile;
