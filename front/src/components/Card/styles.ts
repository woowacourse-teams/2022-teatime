import styled from 'styled-components';

const CardContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 250px;
  height: 320px;
  padding: 35px 16px;
  border-radius: 20px;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.25);
  overflow: hidden;
  cursor: pointer;

  div {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    height: 100%;
  }

  img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
  }

  span {
    font-size: 20px;
    font-weight: 600;
  }

  p {
    text-align: center;
    font-size: 15px;
    margin: 10px 0;
  }

  button {
    width: 67%;
    height: 35px;
    background-color: ${({ theme }) => theme.colors.GREEN_700};
    color: ${({ theme }) => theme.colors.WHITE};
    border: none;
    border-radius: 20px;
    font-size: 18px;
    font-weight: bolder;
    cursor: pointer;
  }

  &:hover {
    transform: scale(1.03);
  }
`;

const ImageWrapper = styled.div`
  margin-bottom: 10px;
`;

const ButtonWrapper = styled.div`
  justify-content: flex-end;
`;

export { CardContainer, ImageWrapper, ButtonWrapper };
