import { useContext, useRef, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { AxiosError } from 'axios';

import Dropdown from '@components/Dropdown';
import Conditional from '@components/Conditional';
import Modal from '@components/Modal';
import useOutsideClick from '@hooks/useOutsideClick';
import useBoolean from '@hooks/useBoolean';
import { UserStateContext, UserDispatchContext } from '@context/UserProvider';
import { ROUTES, MAX_LENGTH } from '@constants/index';
import * as S from './styles';

import LogoIcon from '@assets/logo.svg';
import { api } from '@api/index';

const Header = () => {
  const navigate = useNavigate();
  const profileRef = useRef(null);
  const { userData } = useContext(UserStateContext);
  const dispatch = useContext(UserDispatchContext);
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
    return;
  };

  const handleChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickName(e.target.value);
  };

  const handleOpenModal = () => {
    openModal();
    setNickName('');
  };

  const handleChangeRole = async () => {
    if (!userData) return;

    try {
      const role = userData.role === 'COACH' ? 'CREW' : 'COACH';
      const { data } = await api.post('/api/auth/login/v2', {
        name: userData.name,
        role,
      });
      dispatch({ type: 'SET_USER', userData: data });
      navigate(`/${role.toLowerCase()}`, { replace: true });
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
  };

  return (
    <S.HeaderContainer>
      <S.LogoLink to={userData ? `/${userData.role.toLowerCase()}` : ROUTES.HOME}>
        <S.LogoImage src={LogoIcon} alt="티타임 로고" />
        <h1>티타임</h1>
      </S.LogoLink>
      {!userData && (
        <S.MainButton onClick={() => (location.href = 'https://teatime.pe.kr/')}>
          체험 종료
        </S.MainButton>
      )}
      {userData && (
        <S.ProfileContainer>
          <S.RoleButton onClick={handleChangeRole} isRole={userData.role === 'COACH'}>
            {userData.role === 'COACH' ? '멘토' : '멘티'}
            <div />
          </S.RoleButton>
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
              <Link to={ROUTES.QUESTION}>
                <li>사전질문 관리</li>
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
            maxLength={MAX_LENGTH.NAME}
            type="text"
            placeholder="데모 페이지에서는 변경할 수 없어요."
            value={nickName}
            onChange={(e) => handleChangeInput(e)}
            disabled
            required
          />
        </Modal>
      )}
    </S.HeaderContainer>
  );
};

export default Header;
