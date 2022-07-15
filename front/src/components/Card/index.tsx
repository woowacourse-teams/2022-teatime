import { ButtonWrapper, CardContainer, ImageWrapper } from './styles';

interface CardProps {
  image: string;
  name: string;
  description: string;
  buttonName: string;
  onClick: () => void;
}

const Card = ({ name, image, description, buttonName, onClick }: CardProps) => {
  return (
    <CardContainer onClick={onClick}>
      <div>
        <ImageWrapper>
          <img src={image} alt={`${name} 카드 이미지`} />
        </ImageWrapper>
        <span>{name}</span>
        <p>{description}</p>
        <ButtonWrapper>
          <button>{buttonName}</button>
        </ButtonWrapper>
      </div>
    </CardContainer>
  );
};

export default Card;
