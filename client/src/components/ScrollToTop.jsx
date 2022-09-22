import styled from 'styled-components';
import { useEffect, useState } from 'react';

export default function ScrollToTop() {
  const [scrollY, setScrollY] = useState(0);
  const [btnStatus, setBtnStatus] = useState(false); // 버튼 상태

  const handleFollow = () => {
    setScrollY(window.pageYOffset);
    if (scrollY > 500) {
      setBtnStatus(true);
    } else {
      setBtnStatus(false);
    }
  };

  const handleScroll = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  useEffect(() => {
    const watch = () => {
      window.addEventListener('scroll', handleFollow);
    };
    watch();
  });

  return (
    <div>
      {btnStatus ? (
        <PositionContainer onClick={handleScroll}>
          <i className='fi fi-rr-angle-circle-up'></i>
        </PositionContainer>
      ) : (
        ''
      )}
    </div>
  );
}

const PositionContainer = styled.div`
  position: fixed;
  width: 50px;
  height: 50px;
  bottom: 50px;
  right: 50px;
  z-index: 999;
  font-size: 50px;
  color: ${({ theme }) => theme.colors.purple1};
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.7);
  cursor: pointer;
`;
