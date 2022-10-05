import styled, { css } from 'styled-components';
import { TimeBox } from '@components/TimeBox/styles';
import { FadeIn } from '@styles/common';

const TimeListContainer = styled.div`
  width: 250px;
  margin-left: 60px;
  overflow: scroll;
  ::-webkit-scrollbar {
    display: none;
  }
  animation: ${FadeIn} 0.8s;

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    gap: 10px;
    width: 100%;
    margin-left: 0;
    margin-top: 50px;
  }
`;

const ReservationTimeBox = styled(TimeBox)`
  ${(props) =>
    props.isPossible === false &&
    css`
      background-color: ${({ theme }) => theme.colors.GRAY_200};
      color: ${({ theme }) => theme.colors.GRAY_500};
      cursor: default;
      text-decoration: line-through;
      pointer-events: none;
    `}
`;

const ReserveButtonWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  animation: ${FadeIn} 0.8s;

  div {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 120px;
    height: 50px;
    background-color: rgba(0, 0, 0, 0.6);
    color: ${({ theme }) => theme.colors.WHITE};
    border-radius: 4px;
    font-size: 18px;
    font-weight: bold;
  }

  button {
    width: 120px;
    height: 50px;
    background-color: ${({ theme }) => theme.colors.BLUE_600};
    color: ${({ theme }) => theme.colors.WHITE};
    font-size: 17px;
    font-weight: bold;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    div {
      display: none;
    }

    button {
      width: 100px;
    }
  }
`;

const ModalContent = styled.p`
  margin: 20px 0;
  font-size: 18px;
  font-weight: bold;
`;

export { TimeListContainer, ReservationTimeBox, ReserveButtonWrapper, ModalContent };
