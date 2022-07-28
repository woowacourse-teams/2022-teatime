import reactDom from 'react-dom';

interface ModalPotalProps {
  children: React.ReactNode;
}

const ModalPotal = ({ children }: ModalPotalProps) => {
  const element = document.getElementById('modal') as HTMLElement;
  return reactDom.createPortal(children, element);
};

export default ModalPotal;
