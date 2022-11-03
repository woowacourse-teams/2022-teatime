import { useEffect, useRef } from 'react';

import Portal from '../../Portal';
import type { PropsWithRequiredChildren } from '@typings/utils';
import * as S from './styles';

import CloseIcon from '@assets/close.svg';

interface ModalProps {
  icon?: string;
  title: string;
  firstButtonName: string;
  secondButtonName: string;
  closeModal: () => void;
  onClickFirstButton: () => void;
  onClickSecondButton: () => void;
}

const Modal = ({
  icon,
  title,
  children,
  firstButtonName,
  secondButtonName,
  closeModal,
  onClickFirstButton,
  onClickSecondButton,
}: PropsWithRequiredChildren<ModalProps>) => {
  const handleClickDimmer = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      closeModal();
    }
  };

  const titleRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    titleRef.current !== null && titleRef.current.focus();
  }, []);

  return (
    <Portal id="modal">
      <S.Background onClick={handleClickDimmer}>
        <S.ModalContainer tabIndex={0} ref={titleRef} aria-label={title}>
          <S.InnerContainer>
            <S.CloseIconWrapper>
              <img src={CloseIcon} alt="닫기 아이콘" onClick={closeModal} />
            </S.CloseIconWrapper>
            <S.TitleWrapper>
              <h1>{title}</h1>
              {icon && <img src={icon} alt="" />}
            </S.TitleWrapper>
            {children}
          </S.InnerContainer>
          <S.ButtonWrapper>
            <S.FirstButton onClick={onClickFirstButton}>{firstButtonName}</S.FirstButton>
            <S.SecondButton onClick={onClickSecondButton}>{secondButtonName}</S.SecondButton>
          </S.ButtonWrapper>
        </S.ModalContainer>
      </S.Background>
    </Portal>
  );
};

export default Modal;
