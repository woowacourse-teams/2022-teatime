import React, { useState } from 'react';
import ModalPotal from '@components/ModalPotal';
import CloseIcon from '@assets/close.png';
import { Background, CloseIconWrapper, Container, FormContainer } from './styles';

interface InputModalProps {
  onClose: () => void;
  title: string;
  selectScheduleId: number | null;
  onSubmit: (
    e: React.FormEvent<HTMLFormElement>,
    scheduleId: number,
    crewName: string
  ) => Promise<void>;
}

const InputModal = ({ onClose, title, selectScheduleId, onSubmit }: InputModalProps) => {
  const [text, setText] = useState('');

  const handleChangeText = (e: React.ChangeEvent<HTMLInputElement>) => {
    setText(e.target.value);
  };

  return (
    <ModalPotal>
      <Background>
        <Container>
          <CloseIconWrapper>
            <img src={CloseIcon} onClick={onClose} />
          </CloseIconWrapper>
          <h1>{title}</h1>
          <FormContainer>
            <form
              onSubmit={(e: React.FormEvent<HTMLFormElement>) =>
                onSubmit(e, selectScheduleId as number, text)
              }
            >
              <input
                type="text"
                value={text}
                onChange={handleChangeText}
                placeholder="예약자명을 입력하세요."
              />
              <button>확인</button>
            </form>
          </FormContainer>
        </Container>
      </Background>
    </ModalPotal>
  );
};

export default InputModal;
