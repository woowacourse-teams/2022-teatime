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
    flex-wrap: wrap;
    gap: 10px;
    width: 100%;
    margin-left: 0;
    margin-top: 20px;
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
    margin-bottom: 60px;
  }
`;

const TimeItem = styled.button<{
  isPossible?: boolean;
  isSelected?: boolean;
  isPastTime?: boolean;
}>`
  width: 100%;
  height: 50px;
  margin-bottom: 10px;
  border: 1px solid ${({ theme }) => theme.colors.GREEN_900};
  border-radius: 4px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;

  &:hover {
    border: 3px solid ${({ theme }) => theme.colors.GREEN_900};
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    width: 100px;
  }

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

const Controls = styled.div`
  display: flex;
  justify-content: space-between;
  position: absolute;
  bottom: 0;
  width: 250px;
  gap: 10px;

  button {
    width: 100%;
    height: 50px;
    border: none;
    border-radius: 4px;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
  }
`;

const SelectAllButton = styled.button`
  background-color: ${({ theme }) => theme.colors.GRAY_200};
`;

const ConfirmButton = styled.button`
  background-color: ${({ theme }) => theme.colors.BLUE_300};
  color: ${({ theme }) => theme.colors.BLACK};
`;

const Divider = styled.div`
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

export {
  TimeListContainer,
  ScrollContainer,
  TimeItem,
  Controls,
  Divider,
  SelectAllButton,
  ConfirmButton,
};
