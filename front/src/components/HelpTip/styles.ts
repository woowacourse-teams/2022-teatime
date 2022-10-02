import styled from 'styled-components';
import { FadeIn } from '@styles/common';

const Help = styled.div`
  position: relative;
  width: 24px;
  height: 24px;
  margin: 0 10px;
  border: 1px solid ${({ theme }) => theme.colors.GRAY_300};
  background-color: ${({ theme }) => theme.colors.WHITE};
  border-radius: 50%;
  text-align: center;
  font-size: 18px;
  line-height: 22px;

  ::before {
    content: '?';
    color: ${({ theme }) => theme.colors.GRAY_300};
  }

  :hover p {
    display: block;
    animation: ${FadeIn} 0.2s ease-in-out;
  }

  p {
    position: absolute;
    left: -10px;
    display: none;
    width: 220px;
    margin-top: 6px;
    padding: 14px;
    border-radius: 4px;
    background-color: rgba(0, 0, 0, 0.8);
    text-align: left;
    color: ${({ theme }) => theme.colors.WHITE};
    font-size: 15px;
    line-height: 1.4;
  }
`;

export { Help };
