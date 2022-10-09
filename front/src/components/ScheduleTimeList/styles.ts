import styled, { css } from 'styled-components';
import { TimeBox } from '@components/TimeBox/styles';
import { FadeIn } from '@styles/common';

const TimeListContainer = styled.div`
  animation: ${FadeIn} 0.8s;
  position: relative;
  height: 100%;
  width: 250px;
  margin-left: 60px;

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    display: flex;
    justify-content: center;
    width: 100%;
    margin-left: 0;
  }
`;

const ScrollContainer = styled.div`
  height: calc(100% - 70px);
  overflow: scroll;
  ::-webkit-scrollbar {
    display: none;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    gap: 10px;
    margin: 50px 0 60px;
  }
`;

const ScheduleTimeBox = styled(TimeBox)`
  ${(props) =>
    (props.isPossible === false || props.isPastTime) &&
    css`
      background-color: ${({ theme }) => theme.colors.GRAY_200};
      color: ${({ theme }) => theme.colors.GRAY_500};
      cursor: default;
      text-decoration: line-through;
      pointer-events: none;
    `}

  ${(props) =>
    props.isSelected &&
    css`
      background-color: ${({ theme }) => theme.colors.GREEN_900};
      color: ${({ theme }) => theme.colors.WHITE};
    `}
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  position: absolute;
  bottom: 0;
  width: 250px;

  button {
    width: 120px;
    height: 50px;
    border: none;
    border-radius: 4px;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
  }
`;

const CheckButton = styled.button``;

const ConfirmButton = styled.button`
  background-color: ${({ theme }) => theme.colors.BLUE_300};
  color: ${({ theme }) => theme.colors.BLACK};
`;

export {
  TimeListContainer,
  ScrollContainer,
  ScheduleTimeBox,
  ButtonContainer,
  CheckButton,
  ConfirmButton,
};
