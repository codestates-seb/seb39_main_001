import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { ReactComponent as ChevronUp } from '../assets/icons/chevron-up.svg';

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
          <ChevronUp width={'35px'} height={'35px'} />
        </PositionContainer>
      ) : (
        ''
      )}
    </div>
  );
}

const PositionContainer = styled.div`
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 50px;
  bottom: 50px;
  right: 50px;
  z-index: 999;
  font-size: 50px;
  color: ${({ theme }) => theme.colors.purple1};
  border-radius: 50%;
  border: 3px solid ${({ theme }) => theme.colors.purple1};
  background-color: ${({ theme }) => theme.background};
  cursor: pointer;
`;
