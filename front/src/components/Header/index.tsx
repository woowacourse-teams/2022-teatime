import { useContext, useRef, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { AxiosError } from 'axios';

import Dropdown from '@components/Dropdown';
import Conditional from '@components/Conditional';
import Modal from '@components/Modal';
import useOutsideClick from '@hooks/useOutsideClick';
import useBoolean from '@hooks/useBoolean';
import { UserStateContext, UserDispatchContext } from '@context/UserProvider';
import { SnackbarContext } from '@context/SnackbarProvider';
import { ROUTES } from '@constants/index';
import { editCrewNickName } from '@api/crew';
import * as S from './styles';

import LogoIcon from '@assets/logo.svg';

const Header = () => {
  const navigate = useNavigate();
  const profileRef = useRef(null);
  const { userData } = useContext(UserStateContext);
  const dispatch = useContext(UserDispatchContext);
  const showSnackbar = useContext(SnackbarContext);
  const [isActive, setIsActive] = useOutsideClick(profileRef, false);
  const { value: isOpenModal, setTrue: openModal, setFalse: closeModal } = useBoolean();
  const [nickName, setNickName] = useState('');

  const handleLogout = () => {
    dispatch({ type: 'DELETE_USER' });
    navigate(ROUTES.HOME);
  };

  const toggleDropdown = () => {
    setIsActive(!isActive);
  };

  const handleModifyNickname = async () => {
    try {
      if (nickName.trim() === '') throw new Error('닉네임을 작성해 주세요!');
      if (nickName.length > 20) throw new Error('닉네임 길이는 20 이하로 작성해 주세요!');

      await editCrewNickName(nickName);
      dispatch({ type: 'EDIT_USER', name: nickName });
      showSnackbar({ message: '변경되었습니다. ✅' });
      closeModal();
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
        return;
      }
      if (error instanceof Error) alert(error.message);
    } finally {
      setNickName('');
    }
  };

  const handleChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickName(e.target.value);
  };

  const handleOpenModal = () => {
    openModal();
    setNickName('');
  };

  return (
    <S.HeaderContainer>
      <S.LogoLink to={userData ? `/${userData.role.toLowerCase()}` : ROUTES.HOME}>
        <S.LogoImage src={LogoIcon} alt="티타임 로고" />
        <h1>티타임</h1>
      </S.LogoLink>
      {userData && (
        <S.ProfileContainer>
          <S.ProfileWrapper ref={profileRef} onClick={toggleDropdown}>
            <span>{userData.name}</span>
            <img src={userData.image} alt="프로필 이미지" />
          </S.ProfileWrapper>
          <Dropdown isActive={isActive}>
            <Conditional condition={userData.role === 'COACH'}>
              <Link to={ROUTES.COACH_HISTORY}>
                <li>히스토리</li>
              </Link>
              <Link to={ROUTES.SCHEDULE}>
                <li>스케줄 관리</li>
              </Link>
              <Link to={ROUTES.COACH_PROFILE}>
                <li>프로필 수정</li>
              </Link>
            </Conditional>

            <Conditional condition={userData.role === 'CREW'}>
              <Link to={ROUTES.CREW_HISTORY}>
                <li>히스토리</li>
              </Link>
              <li onClick={handleOpenModal}>닉네임 수정</li>
            </Conditional>
            <li onClick={handleLogout}>로그아웃</li>
          </Dropdown>
        </S.ProfileContainer>
      )}
      {isOpenModal && (
        <Modal
          title="닉네임 수정"
          firstButtonName="취소하기"
          secondButtonName="수정하기"
          onClickFirstButton={closeModal}
          onClickSecondButton={handleModifyNickname}
          closeModal={closeModal}
        >
          <S.Input
            autoFocus
            type="text"
            placeholder="ex) 닉네임(이름)"
            value={nickName}
            onChange={(e) => handleChangeInput(e)}
          />
        </Modal>
      )}
    </S.HeaderContainer>
  );
};

export default Header;
