import Header from '@components/Header';
import { Outlet } from 'react-router-dom';
import { Container } from './styles';

const Layout = () => {
  return (
    <>
      <Header />
      <Container>
        <Outlet />
      </Container>
    </>
  );
};

export default Layout;
