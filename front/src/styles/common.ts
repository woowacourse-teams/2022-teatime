import styled from 'styled-components';

const ScheduleContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const CalendarContainer = styled.div`
  display: flex;
  margin-top: 30px;
  overflow: scroll;
  ::-webkit-scrollbar {
    display: none;
  }
`;

export { ScheduleContainer, CalendarContainer };
