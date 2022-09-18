import styled from 'styled-components';

const Card = () => {
  return <CardContainer></CardContainer>;
};

const CardContainer = styled.div`
  border: 1px solid ${({ theme }) => theme.colors.grey1};
`;

export default Card;
