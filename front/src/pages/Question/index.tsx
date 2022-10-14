import { useState, useEffect, useContext, Fragment } from 'react';
import { AxiosError } from 'axios';

import type { Question as QuestionType } from '@typings/domain';
import { getQuestions, editQuestions } from '@api/question';
import { SnackbarContext } from '@context/SnackbarProvider';
import { MAX_LENGTH, QUESTIONS_LENGTH } from '@constants/index';
import * as S from './styles';

const Question = () => {
  const showSnackbar = useContext(SnackbarContext);
  const [questions, setQuestions] = useState<QuestionType[]>([]);

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

  const handleSubmitQuestions = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      await editQuestions(questions);
      showSnackbar({ message: '저장되었습니다. ✅' });
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
        const { data } = await getQuestions();
        setQuestions(data.questions);
      } catch (error) {
        if (error instanceof AxiosError) {
          alert(error.response?.data?.message);
          console.log(error);
        }
      }
    })();
  }, []);

  return (
    <S.Container>
      <S.Grid>
        <S.QuestionBorderBox>
          <form onSubmit={handleSubmitQuestions}>
            <S.QuestionInner>
              <S.QuestionInputContainer>
                <S.BorderBoxName>사전 질문</S.BorderBoxName>
                {Array.from({ length: QUESTIONS_LENGTH }, (_, index) => (
                  <Fragment key={index}>
                    <S.QuestionLength>
                      {`${questions[index]?.questionContent.length ?? 0} / ${MAX_LENGTH.QUESTION}`}
                    </S.QuestionLength>
                    <input
                      type="text"
                      maxLength={MAX_LENGTH.QUESTION}
                      value={questions[index]?.questionContent ?? ''}
                      onChange={(e) => handleChangeQuestionInput(index, e)}
                      required
                    />
                  </Fragment>
                ))}
              </S.QuestionInputContainer>
              <S.QuestionCheckBoxContainer>
                <S.BorderBoxName>필수 여부</S.BorderBoxName>
                {Array.from({ length: QUESTIONS_LENGTH }, (_, index) => (
                  <input
                    key={index}
                    type="checkbox"
                    checked={questions[index]?.isRequired ?? true}
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

export default Question;
