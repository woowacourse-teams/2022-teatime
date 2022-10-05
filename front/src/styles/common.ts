import styled, { keyframes } from 'styled-components';

const FadeIn = keyframes`
  from {
    opacity: 0
  }
  to {
    opacity: 1
  }
`;

const ScheduleContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
`;

const CalendarContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 35px;
  overflow: scroll;
  ::-webkit-scrollbar {
    display: none;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    display: flex;
    flex-direction: column;
  }
`;

const InfoContainer = styled.div`
  width: 30%;
  margin-right: 50px;
  border-right: 1px solid ${({ theme }) => theme.colors.GRAY_200};
  font-weight: bold;
  overflow: scroll;
  ::-webkit-scrollbar {
    display: none;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    display: flex;
    align-items: center;
    justify-content: space-between;
    border: none;
    margin-right: 0px;
    margin: 20px 0;
    padding: 5px 10px;
    width: 100%;
    background-color: #f2f2f2;
    border-radius: 10px;
  }
`;

export { FadeIn, ScheduleContainer, CalendarContainer, InfoContainer };
