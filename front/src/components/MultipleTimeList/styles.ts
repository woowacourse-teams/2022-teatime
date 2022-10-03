import styled, { css } from 'styled-components';
import { FadeIn } from '@styles/common';

const MultipleTimeListContainer = styled.div`
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

const MultipleTimeBox = styled.div<{ isPossible?: boolean; isSelected?: boolean }>`
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
    margin: 10px 0 60px;
  }
`;

const ConfirmButton = styled.button`
  position: absolute;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 250px;
  height: 50px;
  border: none;
  border-radius: 4px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  background-color: ${({ theme }) => theme.colors.YELLOW_200};
  color: ${({ theme }) => theme.colors.BLACK};

  &:hover {
    opacity: 0.7;
  }
`;

export { MultipleTimeListContainer, MultipleTimeBox, ScrollContainer, ConfirmButton };
