import styled from 'styled-components';
import { Link, useNavigate, useParams } from 'react-router-dom';
import QuestionAnswer from '../components/QuestionAnswer';
import Application from '../components/Application';
import { ReactComponent as Eye } from '../assets/icons/eye.svg';
import { ReactComponent as BookmarkIcon } from '../assets/icons/bookmark.svg';
import { ReactComponent as BookmarkCheckedIcon } from '../assets/icons/bookmark-check-fill.svg';
import { apis } from '../apis/axios';
import { useCookies } from 'react-cookie';
import { useQuery, useMutation, useQueryClient } from 'react-query';

const Board = () => {
	const [cookies] = useCookies();
	const token = cookies.user;
	const param = useParams();
	const boardId = param.boardId;
	const queryClient = useQueryClient();
	const navigate = useNavigate();

	const { data, isLoading, isError, error } = useQuery(
		'board',
		() => apis.getBoardDetail(token, boardId),
		{
			onError: (error) => {
				console.log(error);
			},
		}
	);

	const postBookmarkMutation = useMutation(
		() => apis.postBookmark(token, boardId),
		{
			onMutate: (variable) => {
				console.log('onMutate', variable);
			},
			onSuccess: (data, variables, context) => {
				queryClient.invalidateQueries('board');
			},
			onError: (error) => {
				alert('로그인이 필요한 기능입니다.');
			},
			onSettled: () => {
				console.log('settled');
			},
		}
	);

	const deleteBookmarkMutation = useMutation(
		() => apis.deleteBookmark(token, boardId),
		{
			onMutate: (variable) => {
				console.log('onMutate', variable);
			},
			onSuccess: (data, variables, context) => {
				queryClient.invalidateQueries('board');
			},
			onSettled: () => {
				console.log('settled');
			},
		}
	);

	const deleteBoardBtn = () => {
		if (window.confirm('모집 글을 삭제하시겠습니까?')) {
			apis.deleteBoard(token, boardId);
			alert('삭제 되었습니다.');
			navigate('/');
		} else return;
	};

	return !isLoading ? (
		<BoardContainer>
			<HeaderInfo>
				<StatusType>
					<div className='type'>{data.type}</div>
					<div className='status'>
						{data.status === 'OPENING' ? '모집 중' : '모집 완료'}
					</div>
				</StatusType>
				<FlexContainer>
					{data.auth ? (
						<EditDelete>
							<Link to='/boards/edit' state={{ boardId }}>
								수정
							</Link>
							<DeleteBtn onClick={deleteBoardBtn}>삭제</DeleteBtn>
						</EditDelete>
					) : (
						''
					)}
					<ViewBookmark>
						<Eye />
						{data.views}
						{data.bookmarked ? (
							<BookmarkCheckedIcon
								width={'24px'}
								height={'24px'}
								className='bookmark-checked-icon'
								onClick={() => deleteBookmarkMutation.mutate()}
							/>
						) : (
							<BookmarkIcon
								width={'24px'}
								height={'24px'}
								className='bookmark-icon'
								onClick={() => postBookmarkMutation.mutate()}
							/>
						)}
						{data.bookmarks}
					</ViewBookmark>
				</FlexContainer>
			</HeaderInfo>
			<Title>{data.title}</Title>
			<LeaderInfo>
				<SubTitle>팀장 정보</SubTitle>
				<FlexContainer>
					<Link to={`/users/${data.user.id}`}>
						<ImgContainer>
							<img src={data.user.img} alt='팀장프로필' />
						</ImgContainer>
					</Link>
					<Link to={`/users/${data.user.id}`}>
						<div className='name'>{data.user.nickname}</div>
					</Link>
					{data.user.skillStackTags.map((e, i) => (
						<Stack key={i} src={`/icons/stacks/${e}.png`} alt={`${e}`} />
					))}
				</FlexContainer>
			</LeaderInfo>
			<Application data={data} />
			<TopTemplate>
				<LeftInfo>
					<FlexContainer>
						<Category>모집 마감일</Category>
						{data.dueDate}
					</FlexContainer>
					<FlexContainer>
						<Category>연락 방법</Category>
						{data.contact}
					</FlexContainer>
					<FlexContainer>
						<Category>진행 방식</Category>
						{data.onOffline === 'online' ? '온라인' : '오프라인'}
					</FlexContainer>
				</LeftInfo>
				<RightInfo>
					<FlexContainer>
						<Category>예상 시작일</Category>
						{data.startingDate}
					</FlexContainer>
					<Category>기술 스택</Category>
					<TagsContainer>
						{data.tagList.map((e, i) => (
							<Stack key={i} src={`/icons/stacks/${e}.png`} alt={`${e}`} />
						))}
					</TagsContainer>
				</RightInfo>
			</TopTemplate>
			<Main>{data.content}</Main>
			<QuestionAnswer data={data} />
		</BoardContainer>
	) : (
		''
	);
};

const BoardContainer = styled.div`
	max-width: 1300px;
	padding: 30px;
	margin: auto;
`;

const HeaderInfo = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 10px 0;
`;

const StatusType = styled.div`
	display: flex;
	gap: 15px;
	> .status {
		background-color: ${({ theme }) => theme.colors.grey3};
		border-radius: 4px;
		color: #fff;
		padding: 10px;
	}
	> .type {
		background: ${({ theme }) => theme.colors.purple1};
		border-radius: 4px;
		color: #fff;
		padding: 10px;
	}
`;

const FlexContainer = styled.div`
	display: flex;
	align-items: center;
`;

const EditDelete = styled.div`
	display: flex;
	gap: 10px;
	align-items: center;
	color: ${({ theme }) => theme.colors.grey4};
	margin: 0 30px;
`;

const ViewBookmark = styled.div`
	display: flex;
	align-items: center;
	gap: 10px;
	font-size: 24px;
	> .bookmark-icon {
		cursor: pointer;
	}
	> .bookmark-checked-icon {
		cursor: pointer;
	}
`;

const Title = styled.h2`
	font-size: 36px;
	font-weight: 700;
	line-height: 1.3em;
	padding-bottom: 20px;
	border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
`;

const LeaderInfo = styled.div`
	padding: 15px 0;
	border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
	.name {
		width: 150px;
		margin-left: 15px;
		font-weight: 700;
	}
`;

const ImgContainer = styled.div`
	position: relative;
	border: 1px solid ${({ theme }) => theme.colors.grey3};
	border-radius: 50%;
	width: 60px;
	height: 60px;
	> img {
		position: absolute;
		top: 0;
		left: 0;
		transform: translate(50, 50);
		width: 100%;
		height: 100%;
		object-fit: cover;
		border-radius: 999px;
		margin: auto;
		padding: 2px;
	}
`;

const Stack = styled.img`
	border: 1px solid ${({ theme }) => theme.colors.grey2};
	border-radius: 50%;
	width: 50px;
	height: 50px;
	padding: 5px;
	margin-right: 20px;
`;

const SubTitle = styled.h4`
	font-weight: 700;
	font-size: 22px;
	margin: 5px 0 20px 0;
`;

const TopTemplate = styled.div`
	display: flex;
	padding: 15px 0;
	border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
`;

const LeftInfo = styled.div`
	flex: 1;
	display: flex;
	flex-direction: column;
	gap: 20px;
`;

const RightInfo = styled.div`
	flex: 1;
	display: flex;
	flex-direction: column;
	gap: 20px;
`;

const Category = styled.div`
	color: ${({ theme }) => theme.colors.grey4};
	font-size: 18px;
	width: 150px;
`;

const TagsContainer = styled.div`
	display: flex;
	align-items: center;
	margin-top: -10px;
	> img {
		width: 40px;
		height: 40px;
		padding: 3px;
	}
`;

const Main = styled.div`
	padding: 15px 0;
	min-height: 200px;
	border-bottom: 1px solid ${({ theme }) => theme.colors.grey2};
`;

const DeleteBtn = styled.div`
	cursor: pointer;
	:hover {
		color: ${({ theme }) => theme.colors.purple1};
	}
`;

export default Board;
