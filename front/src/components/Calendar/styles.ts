import styled, { css } from 'styled-components';

const CalendarContainer = styled.div`
  display: flex;
  flex-direction: column;

  h1 {
    margin: 0 0 60px 10px;
    font-size: 28px;
  }
`;

const DateContainer = styled.div<{ hasSchedule: boolean; isSelected: boolean }>`
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

const Date = styled.div``;

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

export { CalendarContainer, DateContainer, Date, DateGrid, DayOfWeekBox, YearMonthContainer };
