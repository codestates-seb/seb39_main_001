import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { ReactComponent as Indicator } from '../assets/icons/circle-fill.svg';
import { ReactComponent as CloseBtnIcon } from '../assets/icons/close.svg';
import theme from '../assets/styles/Theme';

const NotiBoard = ({ el }) => {
  // 알림 멘트 변경
  const notiText = (type) => {
    switch (type) {
      case 'application':
        return '님이 작성한 게시글에 지원하였습니다.';
      case 'accept':
        return '지원한 모임에 수락 되었습니다.';
      case 'decline':
        return '지원한 모임에 거절 되었습니다.';
      case 'close':
        return '게시물이 모집 마감되었습니다.';
      case 'question':
        return '님이 문의사항을 등록했습니다.';
      case 'answer':
        return '님이 작성한 문의사항에 대한 답변을 등록했습니다.';
      case 'upcoming':
        return '북마크한 게시물의 모집 마감기한이 3일 남았습니다.';
      case 'like':
        return '님이 회원님을 좋아요 했습니다.';
      default:
        return '';
    }
  };

  return (
    <StyledNotiBoard key={el.id} className={el.type}>
      <div className='indicator'>
        {el.isRead ? '' : <Indicator fill='tomato' width={'8px'} />}
      </div>
      {['application', 'question', 'answer', 'like'].includes(el.type) ? (
        <div className='content'>
          <p className='text'>
            <Link to={`/users/${el.userId}`}>{el.nickname}</Link>
            {notiText(el.type)}
          </p>
          {el.type === 'like' ? (
            ''
          ) : (
            <p className='title'>
              <Link to={`/boards/${el.boardId}`}>{el.boardTitle}</Link>
            </p>
          )}
        </div>
      ) : (
        <div className='content'>
          <p className='text'>{notiText(el.type)}</p>
          <p className='title'>
            <Link to={`/boards/${el.boardId}`}>{el.boardTitle}</Link>
          </p>
        </div>
      )}
      <p className='date'>
        {`${el.createdAt.split('T')[0]} ${el.createdAt
          .split('T')[1]
          .slice(0, 5)}`}
      </p>
      <div className='delete-button'>
        <CloseBtnIcon fill={theme.colors.grey4} />
      </div>
    </StyledNotiBoard>
  );
};

const StyledNotiBoard = styled.div`
  display: flex;
  align-items: center;
  padding: 20px;
  background-color: ${({ theme }) => theme.colors.grey1 + '90'};
  border-radius: 5px;
  min-height: 83px;
  border: 1px solid ${({ theme }) => theme.colors.grey1 + '90'};
  :hover {
    border: 1px solid ${({ theme }) => theme.colors.purple1};
  }
  > .indicator {
    width: 28px;
    padding-right: 5px;
  }
  > .content {
    display: flex;
    flex-direction: column;
    gap: 10px;
    > .text {
      > a {
        font-weight: 700;
        :hover {
          color: ${({ theme }) => theme.colors.purple1};
        }
      }
    }
    > .title {
      font-size: 15px;
      color: ${({ theme }) => theme.colors.grey4};
    }
  }
  > .date {
    color: ${({ theme }) => theme.colors.grey4};
    margin-left: auto;
  }
  > .delete-button {
    margin-left: 15px;
    > svg {
      :hover {
        fill: ${({ theme }) => theme.colors.purple1};
        cursor: pointer;
      }
    }
  }
`;

export default NotiBoard;
