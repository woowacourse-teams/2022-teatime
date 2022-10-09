import { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Card from '@components/Card';
import { CoachData } from '@typings/domain';
import { editCoachProfile, getCoachProfile } from '@api/coach';
import { UserDispatchContext } from '@context/UserProvider';
import { SnackbarContext } from '@context/SnackbarProvider';
import { ROUTES, MAX_LENGTH } from '@constants/index';
import * as S from './styles';

const CoachProfile = () => {
  const navigate = useNavigate();
  const dispatch = useContext(UserDispatchContext);
  const showSnackbar = useContext(SnackbarContext);
  const [coachProfile, setCoachProfile] = useState<CoachData>({
    image: '',
    name: '',
    description: '',
  });

  const { image, name, description } = coachProfile;

  const handleChangeProfile = (e: React.ChangeEvent<HTMLFormElement>) => {
    setCoachProfile((prev) => {
      return { ...prev, [e.target.name]: e.target.value };
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
          <S.QuestionNameWrapper>
            <span>사전 질문</span>
            <span>필수 여부</span>
          </S.QuestionNameWrapper>
          <form>
            <S.QuestionContainer>
              <S.QuestionInput />
              <S.CheckBox type="checkbox" />
            </S.QuestionContainer>
            <S.QuestionContainer>
              <S.QuestionInput />
              <S.CheckBox type="checkbox" />
            </S.QuestionContainer>
            <S.QuestionContainer>
              <S.QuestionInput />
              <S.CheckBox type="checkbox" />
            </S.QuestionContainer>
            <S.ButtonWrapper>
              <button>등록하기</button>
            </S.ButtonWrapper>
          </form>
        </S.QuestionBorderBox>
      </S.Grid>
    </S.Container>
  );
};

export default CoachProfile;
