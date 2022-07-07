import styled from 'styled-components';

const DateContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 150px;
  height: 110px;
  margin: -0.5px;
  padding: 10px;
  border: 1px solid #e8e8e8;
  overflow: hidden;
`;

const Date = styled.div`
  color: #969696;
  margin-bottom: 5px;
`;

const DateGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 149px);
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  overflow: hidden;
`;

const DayOfWeekBox = styled.div`
  display: flex;
  align-items: center;
  padding-left: 10px;
  height: 40px;
  padding-top: 3px;
  color: #969696;
  background-color: #cce6ba;
  margin: -0.5px;
  border: 1px solid #e8e8e8;
`;

const YearMonthContainer = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  margin-bottom: 20px;
`;

const Year = styled.span`
  margin-bottom: 8px;
  font-size: 24px;
`;

const Month = styled.span`
  font-size: 36px;
`;

const ScheduleBar = styled.div`
  padding-left: 3px;
  font-size: 16px;
  width: 100%;
  margin-bottom: 6px;
  border-radius: 4px;
  background-color: #e6f3ff;
  color: #0085ff;
  cursor: pointer;
`;

export {
  DateContainer,
  Date,
  DateGrid,
  DayOfWeekBox,
  YearMonthContainer,
  Year,
  Month,
  ScheduleBar,
};
