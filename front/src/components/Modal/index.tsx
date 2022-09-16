import Portal from '../../Portal';
import * as S from './styles';

import CloseIcon from '@assets/close.svg';

interface ModalProps {
  icon?: string;
  title: string;
  children: React.ReactNode;
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
}: ModalProps) => {
  const handleClickDimmer = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      closeModal();
    }
  };

  return (
    <Portal id="modal">
      <S.Background onClick={handleClickDimmer}>
        <S.ModalContainer>
          <S.InnerContainer>
            <S.CloseIconWrapper>
              <img src={CloseIcon} alt="닫기 아이콘" onClick={closeModal} />
            </S.CloseIconWrapper>
            <S.TitleWrapper>
              <h1>{title}</h1>
              {icon && <img src={icon} alt={`${title} 아이콘`} />}
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
