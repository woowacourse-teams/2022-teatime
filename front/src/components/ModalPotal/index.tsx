import reactDom from 'react-dom';

interface ModalPotalProps {
  children: React.ReactNode;
}

const ModalPotal = ({ children }: ModalPotalProps) => {
  const el = document.getElementById('modal') as HTMLElement;
  return reactDom.createPortal(children, el);
};

export default ModalPotal;
