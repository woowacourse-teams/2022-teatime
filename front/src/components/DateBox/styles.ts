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
  isWeekend?: boolean;
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

  ${(props) =>
    !props.isWeekend &&
    props.isSelected &&
    css`
      background-color: ${({ theme }) => theme.colors.GREEN_900};
      color: ${({ theme }) => theme.colors.WHITE};
    `}

  ${(props) =>
    !props.isWeekend &&
    props.isCoach &&
    props.hasDate &&
    css`
      border: 1px solid ${({ theme }) => theme.colors.GREEN_300};
    `}

  ${(props) =>
    props.isWeekend &&
    css`
      cursor: default;
    `}
`;

export { DateContainer, TodayIndicator };
