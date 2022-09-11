import styled from 'styled-components';

const TbodyRow = styled.tr`
  display: grid;
  place-items: center;
  grid-template-columns: 1fr 2fr repeat(3, 1fr);
  grid-auto-rows: 96px;
  border-bottom: 1px solid ${({ theme }) => theme.colors.GRAY_300};
  color: ${({ theme }) => theme.colors.BLUE_700};
`;

const Span = styled.span<{ color: string; bgColor: string }>`
  padding: 4px 10px;
  font-size: 16px;
  border-radius: 4px;
  background-color: ${({ bgColor }) => bgColor};
  color: ${({ color }) => color};
`;

const Profile = styled.div`
  display: flex;
  align-items: center;

  img {
    width: 48px;
    height: 48px;
    margin-right: 14px;
    border-radius: 50%;
    border: 3px solid white;
    box-shadow: 0 0 16px rgb(210, 210, 210);
  }
`;

const Icon = styled.img`
  width: 24px;
  height: 24px;
  margin-left: 12px;
  cursor: pointer;
  :hover {
    transform: scale(1.1);
  }
`;

export { TbodyRow, Span, Profile, Icon };
