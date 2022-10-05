import styled, { css } from 'styled-components';

const TodayIndicator = styled.div`
  position: absolute;
  bottom: 5px;
  width: 5px;
  height: 5px;
  background-color: ${({ theme }) => theme.colors.RED_300};
  border-radius: 5px;
`;

const DateContainer = styled.div<{
  hasSchedule: boolean;
  hasDate: boolean;
  isSelected: boolean;
  isCoach?: boolean;
  isImpossibleDay?: boolean;
  isPastDay?: boolean;
}>`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 50px;
  font-size: 20px;
  font-weight: bold;
  border-radius: 25px;
  background-color: ${(props) => props.hasSchedule && props.theme.colors.GREEN_300};
  color: ${(props) => !props.hasSchedule && props.theme.colors.GRAY_300};
  cursor: ${(props) => (props.hasSchedule || props.isCoach) && 'pointer'};

  &:hover {
    border: 2px solid ${({ theme }) => theme.colors.GREEN_900};
  }

  ${(props) =>
    props.isCoach &&
    props.hasDate &&
    css`
      border: 1px solid ${({ theme }) => theme.colors.GREEN_300};
    `}

  ${(props) =>
    !props.hasSchedule &&
    !props.isCoach &&
    css`
      pointer-events: none;
    `}

  ${(props) =>
    props.isPastDay &&
    css`
      pointer-events: none;
      border: none;
    `}
 
  ${(props) =>
    !props.isPastDay &&
    props.isCoach &&
    css`
      color: ${({ theme }) => theme.colors.GREEN_900};
    `}

  ${(props) =>
    props.isImpossibleDay &&
    css`
      background-color: ${({ theme }) => theme.colors.GRAY_200};
    `}

  ${(props) =>
    props.isSelected &&
    css`
      color: ${({ theme }) => theme.colors.WHITE};
      background-color: ${({ theme }) => theme.colors.GREEN_900};
      border: 1px solid ${({ theme }) => theme.colors.GREEN_900};
    `}

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    width: 40px;
    height: 40px;
  }

  @media screen and (${({ theme }) => theme.devices.mobileM}) {
    width: 35px;
    height: 35px;
    font-size: 16px;
  }
`;

export { DateContainer, TodayIndicator };
