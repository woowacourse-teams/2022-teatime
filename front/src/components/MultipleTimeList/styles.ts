import styled, { css } from 'styled-components';
import { TimeBox } from '@components/TimeBox/styles';
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

const MultipleTimeBox = styled(TimeBox)`
  ${(props) =>
    props.isSelected &&
    css`
      background-color: ${({ theme }) => theme.colors.GREEN_900};
      color: ${({ theme }) => theme.colors.WHITE};
    `}
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
  background-color: ${({ theme }) => theme.colors.BLUE_300};
  color: ${({ theme }) => theme.colors.BLACK};

  &:hover {
    opacity: 0.7;
  }
`;

export { MultipleTimeListContainer, MultipleTimeBox, ScrollContainer, ConfirmButton };
