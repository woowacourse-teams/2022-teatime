import styled from 'styled-components';

const Span = styled.span<{ color: string; bgColor: string }>`
  padding: 4px 10px;
  font-size: 16px;
  border-radius: 4px;
  background-color: ${({ bgColor }) => bgColor};
  color: ${({ color }) => color};
  cursor: pointer;

  &:hover {
    opacity: 0.7;
  }
`;

const Profile = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  img {
    width: 65px;
    height: 65px;
    margin-right: 18px;
    border-radius: 50%;
    border: 3px solid white;
    box-shadow: 0 0 16px rgb(210, 210, 210);
  }
`;

const Icon = styled.img`
  width: 22px;
  height: 22px;
  cursor: pointer;
  :hover {
    transform: scale(1.1);
  }
`;

export { Span, Profile, Icon };
