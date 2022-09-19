import { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { NavbarContainer, Logo } from './NavbarPublic';

const NavbarPrivate = () => {
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const dropdownClickHandler = () => {
    setDropdownOpen(!dropdownOpen);
  };

  return (
    <NavbarContainer>
      <Logo />
      <NavButtons>
        <Notification>
          <i class="fi fi-rr-bell" />
        </Notification>
        <Profile onClick={dropdownClickHandler}>
          {dropdownOpen ? (
            <DropdownNav>
              <DropdownLink to="/users/user-id">
                <i class="fi fi-rr-user" />
                <p>마이페이지</p>
              </DropdownLink>
              <DropdownLink to="/users/myjuse/user-id">
                <i class="fi fi-rr-bookmark" />
                <p>나의 JUSE</p>
              </DropdownLink>
              <DropdownLink to="/logout">
                <i class="fi fi-sr-exit" />
                <p>로그아웃</p>
              </DropdownLink>
            </DropdownNav>
          ) : null}
        </Profile>
      </NavButtons>
    </NavbarContainer>
  );
};

const NavButtons = styled.div`
  display: flex;
  justify-content: space-between;
  width: 80px;
`;

const Notification = styled.div`
  display: flex;
  align-items: center;
  font-size: 18px;
  color: ${({ theme }) => theme.colors.grey4};
  cursor: pointer;
  :hover {
    color: ${({ theme }) => theme.colors.black1};
  }
`;

const Profile = styled.div`
  position: relative;
  width: 32px;
  height: 32px;
  border: 1px solid ${({ theme }) => theme.colors.grey3};
  border-radius: 50px;
  cursor: pointer;
`;

const DropdownNav = styled.nav`
  position: absolute;
  width: 140px;
  top: 60px;
  right: 0;
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 1px 8px ${({ theme }) => theme.colors.grey2};
`;

const DropdownLink = styled(Link)`
  display: flex;
  justify-content: space-between;
  padding: 15px 20px;
  font-size: 14px;
  color: ${({ theme }) => theme.colors.grey4};
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
  > i {
    margin-right: 10px;
  }
  :hover {
    background: ${({ theme }) => theme.colors.grey1};
  }
`;

export default NavbarPrivate;
