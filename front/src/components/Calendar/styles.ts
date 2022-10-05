import styled, { css, keyframes } from 'styled-components';

const MoveLeft = keyframes`
  from {
    transform: translateX(50px) 
  }
  to {
    transform: translateX(0px) 
  }
`;

const CalendarContainer = styled.div<{ isMultipleSelecting?: boolean; isOpenTimeList?: boolean }>`
  display: flex;
  flex-direction: column;

  ${(props) =>
    props.isMultipleSelecting &&
    css`
      pointer-events: none;
      opacity: 0.6;
    `}

  ${(props) =>
    (props.isMultipleSelecting || props.isOpenTimeList) &&
    css`
      animation: ${MoveLeft} 0.3s;
    `}

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    align-items: center;
    animation: none;
  }
`;

const DateGrid = styled.div`
  display: grid;
  gap: 10px;
  grid-template-columns: repeat(7, 50px);

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    grid-template-columns: repeat(7, 40px);
  }

  @media screen and (${({ theme }) => theme.devices.mobileM}) {
    grid-template-columns: repeat(7, 35px);
    gap: 8px;
  }
`;

const DayOfWeekBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 50px;
  height: 50px;
  padding-top: 3px;
  font-weight: bolder;
  font-size: 20px;

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

const YearMonthContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  padding: 0 10px;

  div {
    display: flex;
    justify-content: space-between;
    width: 70px;
  }

  span {
    font-size: 24px;
    font-weight: bolder;
  }

  img {
    width: 13px;
    height: 24px;
    cursor: pointer;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    width: 100%;
    justify-content: center;

    span {
      margin-right: 30px;
    }
  }
`;

export { CalendarContainer, DateGrid, DayOfWeekBox, YearMonthContainer };
