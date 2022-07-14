import styled, { css } from 'styled-components';

const CalendarContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin: 0 40px;
  padding-left: 40px;
  border-left: 1px solid #e7e7e7;

  h1 {
    height: 90px;
    margin-left: 10px;
    font-size: 28px;
  }
`;

const DateContainer = styled.div<{ hasSchedule: boolean; isSelected: boolean }>`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 40px;
  border-radius: 25px;
  background-color: ${(props) => props.hasSchedule && '#cce6ba'};
  color: ${(props) => !props.hasSchedule && '#C4C4C4'};
  font-weight: bolder;
  cursor: ${(props) => props.hasSchedule && 'pointer'};

  ${(props) =>
    props.isSelected &&
    css`
      background-color: #404338;
      color: #fff;
    `}
`;

const TodayIndicator = styled.div`
  position: absolute;
  bottom: 5px;
  width: 5px;
  height: 5px;
  background-color: #ff008a;
  border-radius: 5px;
`;

const DateGrid = styled.div`
  display: grid;
  gap: 8px;
  grid-template-columns: repeat(7, 40px);
`;

const DayOfWeekBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  padding-top: 3px;
  color: black;
  font-weight: bolder;
  font-size: 20px;
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
    cursor: pointer;
  }
`;

export {
  CalendarContainer,
  DateContainer,
  DateGrid,
  DayOfWeekBox,
  YearMonthContainer,
  TodayIndicator,
};
