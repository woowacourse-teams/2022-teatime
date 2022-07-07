import styled from 'styled-components';

const Layout = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 25px;
`;

const DateContainer = styled.div`
  display: flex;
  align-items: flex-end;
  flex-direction: column;
  width: 100px;
  height: 80px;
  margin: -1px;
  padding: 10px;
  border: 2px solid #e2e2e2;
  overflow: hidden;
`;

const DateGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  width: 854px;
`;

const DayOfWeekBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 3px;
  background-color: #dedede;
`;

const CalendarTitle = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  width: 854px;
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
  width: 100%;
  margin-bottom: 4px;
  border-radius: 4px;
  background-color: #cce6ba;
  cursor: pointer;
`;

export { Layout, DateContainer, DateGrid, DayOfWeekBox, CalendarTitle, Year, Month, ScheduleBar };