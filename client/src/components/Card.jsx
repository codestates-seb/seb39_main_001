import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { ReactComponent as BookmarkIcon } from '../assets/icons/bookmark.svg';

const Card = ({ data }) => {
	// 프로젝트 기간 text 변환
	const periodNaming = (e) => {
		if (e === 'short') {
			return '1개월 미만';
		} else if (e === 'long') {
			return '장기';
		} else {
			return e + '개월';
		}
	};

	return (
		<CardContainer status={data.status}>
			{data.status === 'CLOSED' ? <Closed>모집 완료</Closed> : ''}
			<CardHeader>
				<CardType>{data.type}</CardType>
				<Bookmark>
					<BookmarkIcon width={'24px'} height={'24px'} />
					{data.bookmarks}
				</Bookmark>
			</CardHeader>
			<Link to={`/boards/${data.id}`}>
				<CardSummary>
					<div className='date'>{`${data.startingDate} (${periodNaming(
						data.period
					)})`}</div>
					<p className='title'>{data.title}</p>
					<div className='tags-container'>
						{/* 스택 리스트는 5개 초과 시 첫 5개만 잘라서 보여줌 */}
						{data.tagList.slice(0, 5).map((e, i) => (
							<img
								className='tag'
								key={i}
								src={`/icons/stacks/${e}.png`}
								alt={`${e}`}
							/>
						))}
					</div>
				</CardSummary>
			</Link>
			<CardInfo>
				<AuthorInfo>
					<div className='picture'></div>
					<div className='name'>{data.user.nickname}</div>
				</AuthorInfo>
				<Views>
					<i className='fi fi-rr-eye'></i>
					{data.views}
				</Views>
			</CardInfo>
		</CardContainer>
	);
};

const CardContainer = styled.div`
	position: relative;
	border: 2px solid ${({ theme }) => theme.colors.grey1};
	width: 370px;
	padding: 20px 30px;
	opacity: ${({ status }) => (status === 'CLOSED' ? 0.5 : 1)};
`;

const CardHeader = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
`;

const CardType = styled.div`
	background-color: ${({ theme }) => theme.colors.grey5};
	color: white;
	font-size: 13px;
	padding: 7px 10px;
`;

const Bookmark = styled.div`
	display: flex;
	flex-direction: column;
	text-align: center;
`;

const CardSummary = styled.div`
	border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
	padding: 10px 0;
	margin-bottom: 10px;
	min-height: 256px;
	.date {
		color: ${({ theme }) => theme.colors.grey4};
		padding-top: 5px;
	}
	.title {
		display: -webkit-box;
		font-size: 24px;
		font-weight: 700;
		line-height: 1.3em;
		overflow: hidden;
		-webkit-line-clamp: 3; /* 라인수 */
		-webkit-box-orient: vertical;
		margin: 10px 0;
		width: auto;
		height: 3.9em;
	}
	.tags-container {
		display: flex;
		flex-wrap: wrap;
		gap: 10px;
		margin: 30px 0;
		> .tag {
			border: 1px solid ${({ theme }) => theme.colors.grey3};
			border-radius: 50%;
			width: 50px;
			height: 50px;
			padding: 5px;
		}
	}
`;

const CardInfo = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
`;

const AuthorInfo = styled.div`
	display: flex;
	align-items: center;
	padding: 15px 0;
	> .picture {
		border: 1px solid ${({ theme }) => theme.colors.grey3};
		border-radius: 50%;
		width: 35px;
		height: 35px;
	}
	> .name {
		font-size: 18px;
		padding-left: 10px;
	}
`;

const Views = styled.div`
	display: flex;
	align-items: center;
	gap: 4px;
	color: ${({ theme }) => theme.colors.grey4};
	> i {
		transform: translateY(1px);
	}
`;

const Closed = styled.div`
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	padding: 16px;
	background-color: #000;
	color: #fff;
`;

export default Card;
