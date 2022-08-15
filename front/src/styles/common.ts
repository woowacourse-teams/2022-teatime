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

const InfoContainer = styled.div`
  position: relative;
  width: 30%;
  margin-right: 50px;
  border-right: 1px solid ${({ theme }) => theme.colors.GRAY_200};
  font-weight: bold;
`;

export { ScheduleContainer, CalendarContainer, InfoContainer };
