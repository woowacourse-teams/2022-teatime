import { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import { CoachData } from '@typings/domain';
import { editCoachProfile, getCoachProfile } from '@api/coach';
import { UserDispatchContext } from '@context/UserProvider';
import { SnackbarContext } from '@context/SnackbarProvider';
import { ROUTES } from '@constants/index';
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

    try {
      await editCoachProfile({
        name,
        description,
      });
      dispatch({ type: 'EDIT_USER', name });
      showSnackbar({ message: '변경되었습니다. ✅' });
      navigate(ROUTES.COACH);
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
      <img src={image} alt="코치 프로필 이미지" />
      <form onChange={handleChangeProfile} onSubmit={handleSubmitProfile}>
        <S.InputWrapper>
          <label htmlFor="">Nickname</label>
          <input name="name" type="text" defaultValue={name} />
        </S.InputWrapper>
        <S.InputWrapper>
          <label htmlFor="">Description</label>
          <textarea name="description" id="" defaultValue={description} rows={7}></textarea>
        </S.InputWrapper>
        <S.EditButton>수정하기</S.EditButton>
      </form>
    </S.Container>
  );
};

export default CoachProfile;
