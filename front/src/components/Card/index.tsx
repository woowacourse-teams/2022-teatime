import type { Coach as CoachType } from '@typings/domain';
import { ButtonWrapper, CardContainer, ImageWrapper } from './styles';

interface CoachProps {
  coach: CoachType;
  onClick: (id: number) => void;
}

const Card = ({ coach, onClick }: CoachProps) => {
  return (
    <CardContainer onClick={() => onClick(coach.id)}>
      <div>
        <ImageWrapper>
          <img src={coach.image} alt="코치 프로필 이미지" />
        </ImageWrapper>
        <span>{coach.name}</span>
        <p>{coach.description}</p>
        <ButtonWrapper>
          <button>예약하기</button>
        </ButtonWrapper>
      </div>
    </CardContainer>
  );
};

export default Card;
