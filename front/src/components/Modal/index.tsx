import ModalPotal from '@components/ModalPotal';
import CloseIcon from '@assets/close.svg';
import {
  Background,
  ModalContainer,
  FirstButton,
  SecondButton,
  CloseIconWrapper,
  ButtonWrapper,
  InnerContainer,
  TitleWrapper,
} from './styles';

interface ModalProps {
  icon: string;
  title: string;
  content: string;
  firstButtonName: string;
  secondButtonName: string;
  closeModal: () => void;
}

const Modal = ({
  icon,
  title,
  content,
  firstButtonName,
  secondButtonName,
  closeModal,
}: ModalProps) => {
  const handleClickDimmer = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      closeModal();
    }
  };

  return (
    <ModalPotal>
      <Background onClick={handleClickDimmer}>
        <ModalContainer>
          <InnerContainer>
            <CloseIconWrapper>
              <img src={CloseIcon} alt="닫기 아이콘" onClick={closeModal} />
            </CloseIconWrapper>
            <TitleWrapper>
              <h1>{title}</h1>
              <img src={icon} alt={`${title} 아이콘`} />
            </TitleWrapper>
            <p>{content}</p>
          </InnerContainer>
          <ButtonWrapper>
            <FirstButton>{firstButtonName}</FirstButton>
            <SecondButton>{secondButtonName}</SecondButton>
          </ButtonWrapper>
        </ModalContainer>
      </Background>
    </ModalPotal>
  );
};

export default Modal;
