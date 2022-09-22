import { useContext } from 'react';

import { UserStateContext } from '@context/UserProvider';
import * as S from './styles';

const CoachProfile = () => {
  const { userData } = useContext(UserStateContext);

  return (
    <S.Container>
      <img src={userData?.image} alt="코치 프로필 이미지" />
      <h1>{userData?.name}</h1>
      <form>
        <S.InputWrapper>
          <label htmlFor="">Nickname</label>
          <input type="text" />
        </S.InputWrapper>
        <S.InputWrapper>
          <label htmlFor="">Description</label>
          <textarea name="" id="" rows={7}></textarea>
        </S.InputWrapper>
        <S.ButtonWrapper>
          <S.FirstButton>취소하기</S.FirstButton>
          <S.SecondButton>수정하기</S.SecondButton>
        </S.ButtonWrapper>
      </form>
    </S.Container>
  );
};

export default CoachProfile;
