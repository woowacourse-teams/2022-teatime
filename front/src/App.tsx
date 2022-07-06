import { Routes, Route } from 'react-router-dom';
import Home from '@pages/Home';
import Schedule from '@pages/Schedule';

const App = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/schedule/:id" element={<Schedule />} />
    </Routes>
  );
};

export default App;
