import styled from 'styled-components';

const CardContainer = styled.div<{ color: string }>`
  display: flex;
  flex-direction: column;
  justify-content: end;
  align-items: center;
  width: 250px;
  height: 300px;
  margin: 0 10px;
  border-radius: 20px;
  box-shadow: rgba(0, 0, 0, 0.07) 0px 1px 2px, rgba(0, 0, 0, 0.07) 0px 2px 4px,
    rgba(0, 0, 0, 0.07) 0px 4px 8px, rgba(0, 0, 0, 0.07) 0px 8px 16px,
    rgba(0, 0, 0, 0.07) 0px 16px 32px, rgba(0, 0, 0, 0.07) 0px 32px 64px;
  background: linear-gradient(to right, ${(props) => props.color});
  overflow: hidden;
  cursor: pointer;

  div {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: end;
    align-items: center;
    padding: 10px;
    width: 100%;
    height: 60%;
    background-color: white;
  }

  img {
    position: absolute;
    top: -50px;
    width: 100px;
    height: 100px;
    border-radius: 50%;
  }

  span {
    font-size: 20px;
    font-weight: 600;
  }

  button {
    width: 80%;
    height: 35px;
    margin-bottom: 10px;
    border: none;
    font-size: 20px;
    border-radius: 4px;
    cursor: pointer;
  }

  &:hover {
    transform: scale(1.03);
  }
`;

export { CardContainer };
