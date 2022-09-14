import { useContext, useRef, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { AxiosError } from 'axios';

import Dropdown from '@components/Dropdown';
import Conditional from '@components/Conditional';
import useOutsideClick from '@hooks/useOutsideClick';
import useBoolean from '@hooks/useBoolean';
import { UserStateContext, UserDispatchContext } from '@context/UserProvider';
import { SnackbarContext } from '@context/SnackbarProvider';
import { ROUTES } from '@constants/index';
import { editCoachNickName } from '@api/coach';
import { editCrewNickName } from '@api/crew';
import * as S from './styles';

import LogoIcon from '@assets/logo.svg';

const Header = () => {
  const navigate = useNavigate();
  const { userData } = useContext(UserStateContext);
  const dispatch = useContext(UserDispatchContext);
  const showSnackbar = useContext(SnackbarContext);
  const profileRef = useRef(null);
  const [isActive, setIsActive] = useOutsideClick(profileRef, false);
  const { value: isOpenInput, setTrue: openInput, setFalse: closeInput } = useBoolean();
  const [nickName, setNickName] = useState('');

  const handleLogout = () => {
    dispatch({ type: 'DELETE_USER' });
    navigate(ROUTES.HOME);
  };

  const toggleDropdown = () => {
    setIsActive(!isActive);
  };

  const handleModifyNickname = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      userData?.role === 'COACH'
        ? await editCoachNickName(nickName)
        : await editCrewNickName(nickName);

      showSnackbar({ message: '변경되었습니다. ✅' });
      setNickName('');
      closeInput();
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
  };

  const handleChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickName(e.target.value);
  };

  return (
    <S.HeaderContainer>
      <S.LogoLink to={userData ? `/${userData.role.toLowerCase()}` : ROUTES.HOME}>
        <S.LogoImage src={LogoIcon} alt="티타임 로고" />
        <h1>티타임</h1>
      </S.LogoLink>
      {userData && (
        <S.ProfileContainer>
          {isOpenInput ? (
            <S.Form onSubmit={(e) => handleModifyNickname(e)}>
              <input
                type="text"
                placeholder="ex) 닉네임(이름)"
                value={nickName}
                onChange={(e) => handleChangeInput(e)}
              />
              <button>수정</button>
              <button onClick={() => closeInput()}>취소</button>
            </S.Form>
          ) : (
            <S.ProfileWrapper ref={profileRef} onClick={toggleDropdown}>
              <span>{userData.name}</span>
              <img src={userData.image} alt="프로필 이미지" />
            </S.ProfileWrapper>
          )}
          <Dropdown isActive={isActive}>
            <Conditional condition={userData.role === 'COACH'}>
              <Link to={ROUTES.COACH_HISTORY}>
                <li>히스토리</li>
              </Link>
              <Link to={ROUTES.SCHEDULE}>
                <li>스케줄 관리</li>
              </Link>
            </Conditional>

            <Conditional condition={userData.role === 'CREW'}>
              <Link to={ROUTES.CREW_HISTORY}>
                <li>히스토리</li>
              </Link>
            </Conditional>

            <li onClick={() => openInput()}>닉네임 변경</li>
            <li onClick={handleLogout}>로그아웃</li>
          </Dropdown>
        </S.ProfileContainer>
      )}
    </S.HeaderContainer>
  );
};

export default Header;
