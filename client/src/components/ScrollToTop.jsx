import styled from 'styled-components';

export default function ScrollToTop() {
  const handleScroll = (e) => {
    if (!window.scrollY) return;

    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  return (
    <PositionContainer onClick={handleScroll}>
      <i class='fi fi-rr-angle-circle-up'></i>
    </PositionContainer>
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
  background-color: #fff;
  cursor: pointer;
`;
