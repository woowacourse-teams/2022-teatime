import * as S from './styles';

import ModalPortal from '../../ModalPortal';

import CloseIcon from '@assets/close.svg';

interface ModalProps {
  icon: string;
  title: string;
  content: string;
  firstButtonName: string;
  secondButtonName: string;
  closeModal: () => void;
  onClick: () => void;
}

const Modal = ({
  icon,
  title,
  content,
  firstButtonName,
  secondButtonName,
  closeModal,
  onClick,
}: ModalProps) => {
  const handleClickDimmer = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      closeModal();
    }
  };

  return (
    <ModalPortal>
      <S.Background onClick={handleClickDimmer}>
        <S.ModalContainer>
          <S.InnerContainer>
            <S.CloseIconWrapper>
              <img src={CloseIcon} alt="닫기 아이콘" onClick={closeModal} />
            </S.CloseIconWrapper>
            <S.TitleWrapper>
              <h1>{title}</h1>
              <img src={icon} alt={`${title} 아이콘`} />
            </S.TitleWrapper>
            <p>{content}</p>
          </S.InnerContainer>
          <S.ButtonWrapper>
            <S.FirstButton>{firstButtonName}</S.FirstButton>
            <S.SecondButton onClick={onClick}>{secondButtonName}</S.SecondButton>
          </S.ButtonWrapper>
        </S.ModalContainer>
      </S.Background>
    </ModalPortal>
  );
};

export default Modal;
