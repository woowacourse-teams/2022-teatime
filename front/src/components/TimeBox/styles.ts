import styled from 'styled-components';

const TimeBox = styled.div<{ isPossible?: boolean; isSelected?: boolean; isPastTime?: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
  margin-bottom: 10px;
  border: 1px solid ${({ theme }) => theme.colors.GREEN_900};
  border-radius: 4px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;

  &:hover {
    border: 3px solid ${({ theme }) => theme.colors.GREEN_900};
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    width: 100px;
  }
`;

export { TimeBox };
