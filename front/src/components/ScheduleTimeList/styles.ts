import styled, { css } from 'styled-components';

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

const TimeBox = styled.div<{ isPossible?: boolean; isSelected: boolean; isPastTime: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
  margin-bottom: 10px;
  border: 1px solid ${({ theme }) => theme.colors.GREEN_900};
  border-radius: 4px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;

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

  &:hover {
    border: 3px solid ${({ theme }) => theme.colors.GREEN_900};
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    width: 100px;
  }
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
  background-color: ${({ theme }) => theme.colors.YELLOW_200};
  color: ${({ theme }) => theme.colors.BLACK};
`;

export { TimeListContainer, ScrollContainer, TimeBox, ButtonContainer, CheckButton, ConfirmButton };
