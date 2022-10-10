import { useEffect, useState } from 'react';
import styled from 'styled-components';
import Select from 'react-select';
import TechStack from './TechStack';
// datepicker
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DesktopDatePicker } from '@mui/x-date-pickers';
import { LocalizationProvider } from '@mui/x-date-pickers';
import TextField from '@mui/material/TextField';
import dayjs from 'dayjs';
import theme from '../assets/styles/Theme';

const HeaderTemplate = ({
  formData,
  setFormData,
  company,
  setCompany,
  count,
  setCount,
}) => {
  // 모집 기간, 포지션 select options
  const period = [
    { value: 'short', label: '1개월 미만' },
    { value: '1', label: '1개월' },
    { value: '2', label: '2개월' },
    { value: '3', label: '3개월' },
    { value: '4', label: '4개월' },
    { value: '5', label: '5개월' },
    { value: '6', label: '6개월' },
    { value: 'long', label: '장기' },
  ];

  const position = [
    { value: 'frontend', label: '프론트엔드' },
    { value: 'backend', label: '백엔드' },
    { value: 'designer', label: '디자이너' },
    { value: 'etc', label: '기타' },
  ];

  // const customStyles = {
  // 	option: (base, provided) => {
  // 		return {
  // 			...base,
  // 			...provided,
  // 			background: theme.background,
  // 			color: 'white',
  // 		};
  // 	},
  // };

  // 클릭된 버튼 스타일링 (default: 모집 중 버튼)
  const [defaultBtnActive, setDefaultBtnActive] = useState('OPENING');
  const [typeBtnActive, setTypeBtnActive] = useState('PROJECT');
  const [onlineBtnActive, setOnlineBtnActive] = useState('');

  // 예상 기간 선택
  const [periodSelected, setPeriodSelected] = useState(period[0]);

  // 선택된 기술스택
  const [stack, setStack] = useState([]);

  // stack 이 바뀔때마다 formdata update 해주는 useEffect
  useEffect(() => {
    setFormData({
      ...formData,
      tagList: [...stack],
    });
  }, [stack.length]);

  // 이벤트 핸들러
  const meetingStatusHandler = (e) => {
    setDefaultBtnActive(e.target.id);
    setFormData({
      ...formData,
      status: e.target.id,
    });
  };

  const meetingTypeHandler = (e) => {
    setTypeBtnActive(e.target.id);
    setFormData({
      ...formData,
      type: e.target.id,
    });
  };

  const meetingOnlineHandler = (e) => {
    setOnlineBtnActive(e.target.id);
    setFormData({
      ...formData,
      onOffline: e.target.id,
    });
  };

  const inputValueHandler = (e) => {
    setFormData({
      ...formData,
      contact: e.target.value,
    });
  };

  const periodHandler = (e) => {
    setPeriodSelected(e.value);
    setFormData({
      ...formData,
      period: e.value,
    });
  };

  const dueDateHandler = (date) => {
    setFormData({
      ...formData,
      dueDate: dayjs(date).format('YYYY-MM-DD'),
    });
  };

  const startingDateHandler = (date) => {
    setFormData({
      ...formData,
      startingDate: dayjs(date).format('YYYY-MM-DD'),
    });
  };

  // 모집 유형 프로젝트일 때 인원
  const decreaseHandler = (i) => {
    const temp = [...company];
    if (temp[i].count > 0) {
      temp[i].count--;
    }
    setCompany(temp);
    setFormData((prev) => ({ ...prev, [temp[i].position]: temp[i].count }));
  };

  const increaseHandler = (i) => {
    const temp = [...company];
    temp[i].count++;
    setCompany(temp);
    setFormData((prev) => ({ ...prev, [temp[i].position]: temp[i].count }));
  };

  const positionChangeHandler = (e, i) => {
    const temp = [...company];
    temp[i].position = e.value;
    setCompany(temp);
  };

  const clickHandler = () => {
    setCompany([...company, { position: 'frontend', count: 0 }]);
  };

  // 모집 유형 스터디일 때 인원
  const peopleDecreaseHandler = () => {
    const temp = count - 1;
    setCount(temp);
    setFormData((prev) => ({ ...prev, people: temp }));
  };

  const peopleIncreaseHandler = () => {
    const temp = count + 1;
    setCount(temp);
    setFormData((prev) => ({ ...prev, people: temp }));
  };

  return (
    <HeaderTemplateContainer>
      <SelectSingle>
        <label>모집 상태</label>
        <div
          className={`select-btn ${
            defaultBtnActive === 'OPENING' ? 'active' : ''
          }`}
          id='OPENING'
          onClick={meetingStatusHandler}>
          모집 중
        </div>
        <div
          className={`select-btn ${
            defaultBtnActive === 'CLOSED' ? 'active' : ''
          }`}
          id='CLOSED'
          onClick={meetingStatusHandler}>
          모집 완료
        </div>
      </SelectSingle>
      <div className='group-selection'>
        <SelectButton>
          <label>유형</label>
          <div
            id='PROJECT'
            className={`select-btn ${
              typeBtnActive === 'PROJECT' ? 'active' : ''
            }`}
            onClick={meetingTypeHandler}>
            프로젝트
          </div>
          <div
            id='STUDY'
            className={`select-btn ${
              typeBtnActive === 'STUDY' ? 'active' : ''
            }`}
            onClick={meetingTypeHandler}>
            스터디
          </div>
        </SelectButton>
        <SelectButton>
          <label>진행 방법</label>
          <div
            id='online'
            className={`select-btn ${
              onlineBtnActive === 'online' ? 'active' : ''
            }`}
            onClick={meetingOnlineHandler}>
            온라인
          </div>
          <div
            id='offline'
            className={`select-btn ${
              onlineBtnActive === 'offline' ? 'active' : ''
            }`}
            onClick={meetingOnlineHandler}>
            오프라인
          </div>
        </SelectButton>
      </div>
      <div className='group-selection'>
        <SelectType>
          <label>모집 마감일</label>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DesktopDatePicker
              views={['day']}
              value={formData.dueDate || null}
              onChange={dueDateHandler}
              inputFormat='YYYY-MM-DD'
              renderInput={(params) => (
                <TextField
                  size='small'
                  {...params}
                  sx={{
                    width: 245,
                    svg: { color: '#7e858d' },
                    input: { color: '#7e858d' },
                    '& .MuiOutlinedInput-root': {
                      '& fieldset': {
                        borderColor: '#7e858d',
                      },
                      '&:hover fieldset': {
                        borderColor: '#be99ff',
                      },
                      '&.Mui-focused fieldset': {
                        borderColor: '#be99ff',
                      },
                    },
                  }}
                />
              )}
            />
          </LocalizationProvider>
        </SelectType>
        <SelectType>
          <label>시작 예정일</label>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DesktopDatePicker
              views={['day']}
              value={formData.startingDate || null}
              onChange={startingDateHandler}
              inputFormat='YYYY-MM-DD'
              renderInput={(params) => (
                <TextField
                  size='small'
                  {...params}
                  sx={{
                    width: 245,
                    svg: { color: '#7e858d' },
                    input: { color: '#7e858d' },
                    '& .MuiOutlinedInput-root': {
                      '& fieldset': {
                        borderColor: '#7e858d',
                      },
                      '&:hover fieldset': {
                        borderColor: '#be99ff',
                      },
                      '&.Mui-focused fieldset': {
                        borderColor: '#be99ff',
                      },
                    },
                  }}
                />
              )}
            />
          </LocalizationProvider>
        </SelectType>
      </div>
      <div className='group-selection'>
        <SelectType>
          <label>예상 기간</label>
          <Select
            id='period'
            options={period}
            value={period.filter((option) => {
              return (
                // 글쓰기 화면에서 선택한 value
                option.value === periodSelected
              );
            })}
            defaultValue={period[0]}
            onChange={periodHandler}
            theme={(theme) => ({
              ...theme,
              colors: {
                ...theme.colors,
                primary25: '#f0e7fe',
                primary: '#be99ff',
                background: `${({ theme }) => theme.background}`,
                text: `${({ theme }) => theme.text}`,
              },
            })}
            styles={{
              singleValue: (provided) => ({
                ...provided,
                color: theme.colors.text,
              }),
              option: (provided) => ({
                ...provided,
                ':hover': {
                  color: theme.colors.black1,
                },
              }),
            }}
            // styles={customStyles}
          />
        </SelectType>
        <SelectButton>
          <label>연락 방법</label>
          <input
            id='contact'
            type='text'
            placeholder='오픈채팅 링크 또는 이메일을 입력해주세요'
            value={formData.contact}
            onChange={inputValueHandler}
          />
        </SelectButton>
      </div>
      <SelectPositionContainer>
        <div className='position-title'>
          <label>모집 포지션</label>
          <label>인원</label>
        </div>
        {formData.type === 'PROJECT' ? (
          company &&
          company.map((e, i) => (
            <div className='position' key={i}>
              <PositionSelectBox>
                <Select
                  id='position'
                  options={position}
                  value={e.value}
                  defaultValue={position[0]}
                  onChange={(e) => positionChangeHandler(e, i)}
                  theme={(theme) => ({
                    ...theme,
                    colors: {
                      ...theme.colors,
                      primary25: '#f0e7fe',
                      primary: '#be99ff',
                    },
                  })}
                  styles={{
                    singleValue: (provided) => ({
                      ...provided,
                      color: theme.colors.text,
                    }),
                    option: (provided) => ({
                      ...provided,
                      ':hover': {
                        color: theme.colors.black1,
                      },
                    }),
                  }}
                />
              </PositionSelectBox>
              <PositionCountBtn>
                <div className='count-button-group'>
                  <button onClick={() => decreaseHandler(i)}>-</button>
                  <span>{e.count}</span>
                  <button onClick={() => increaseHandler(i)}>+</button>
                </div>
              </PositionCountBtn>
            </div>
          ))
        ) : (
          <div className='position'>
            <PositionSelectBox>
              <Select isDisabled={true} />
            </PositionSelectBox>
            <PositionCountBtn>
              <div className='count-button-group'>
                <button onClick={peopleDecreaseHandler}>-</button>
                <span id='people'>{count}</span>
                <button onClick={peopleIncreaseHandler}>+</button>
              </div>
            </PositionCountBtn>
          </div>
        )}
      </SelectPositionContainer>
      <AddButton className='add-btn' onClick={clickHandler}>
        추가
      </AddButton>
      <SelectSingle>
        <label className='individual-selection'>기술 스택</label>
        <TechStack
          selected={stack}
          setSelected={setStack}
          formData={formData}
          setFormData={setFormData}
        />
      </SelectSingle>
    </HeaderTemplateContainer>
  );
};

const HeaderTemplateContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 800px;
  padding: 20px;
  margin-bottom: 30px;
  color: ${({ theme }) => theme.colors.black1};
  border: 1px solid ${({ theme }) => theme.colors.grey2};
  border-radius: 4px;
  > .group-selection {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    margin: 15px 0px 15px 0px;
  }
`;

const SelectButton = styled.div`
  border-radius: 4px;
  width: 250px;
  > label {
    display: block;
    font-size: 16px;
    font-weight: 700;
    padding: 10px 10px 10px 0px;
  }
  > div {
    font-size: 14px;
    border: 1px solid ${({ theme }) => theme.colors.grey3};
    border-radius: inherit;
    width: 120px;
    padding: 10px 15px;
    margin-right: 5px;
    text-align: center;
    display: block;
    float: left;
    cursor: pointer;
    :hover {
      border: 1px solid ${({ theme }) => theme.colors.purple1};
      background: ${({ theme }) => theme.background};
      color: ${({ theme }) => theme.colors.purple1};
    }
    &.active {
      border: 1px solid ${({ theme }) => theme.colors.purple1};
      background: ${({ theme }) => theme.colors.purple1};
      color: #ffffff;
    }
  }
  > input {
    width: 245px;
    padding: 10px 15px;
    border: 1px solid ${({ theme }) => theme.colors.grey3};
    border-radius: inherit;
    background: ${({ theme }) => theme.background};
    color: ${({ theme }) => theme.text};
    :focus {
      outline: 1px solid ${({ theme }) => theme.colors.purple1};
    }
    :focus::-webkit-input-placeholder {
      color: transparent;
    }
    :hover {
      border: 1px solid ${({ theme }) => theme.colors.purple1};
    }
  }
`;

const SelectType = styled.div`
  border-radius: 4px;
  width: 250px;
  > label {
    display: block;
    font-size: 16px;
    font-weight: 700;
    padding: 10px 10px 10px 0px;
  }
  > .count-button-group {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    width: 100px;
    > button {
      width: 25px;
      height: 25px;
      border: 1px solid ${({ theme }) => theme.colors.grey3};
      border-radius: 90px;
      background: #ffffff;
      cursor: pointer;
      :hover {
        background: ${({ theme }) => theme.colors.purple1};
        color: #ffffff;
        border: none;
      }
    }
  }
  /* > .css-1s2u09g-control {
    background-color: ${({ theme }) => theme.background};
    > .css-6j8wv5-Input {
      input {
        color: ${({ theme }) => theme.text};
      }
    }
  }
  > .css-26l3qy-menu {
    background: ${({ theme }) => theme.background};
  } */
  #period {
    > div {
      background-color: ${({ theme }) => theme.background};
      color: ${({ theme }) => theme.text};
      border-color: ${({ theme }) => theme.colors.grey3};
    }
  }
`;

const SelectSingle = styled.div`
  width: 500px;
  margin-left: 63px;
  > label {
    display: block;
    font-size: 16px;
    font-weight: 700;
    padding: 10px 10px 10px 0px;
    &.individual-selection {
      padding-bottom: 0;
    }
  }

  .select-btn {
    font-size: 14px;
    border: 1px solid ${({ theme }) => theme.colors.grey3};
    border-radius: 4px;
    width: 120px;
    padding: 10px 15px;
    margin-right: 5px;
    text-align: center;
    display: block;
    float: left;
    cursor: pointer;
    :hover {
      border: 1px solid ${({ theme }) => theme.colors.purple1};
      background: ${({ theme }) => theme.background};
      color: ${({ theme }) => theme.colors.purple1};
    }
    &.active {
      border: 1px solid ${({ theme }) => theme.colors.purple1};
      background: ${({ theme }) => theme.colors.purple1};
      color: #ffffff;
    }
  }
`;

const SelectPositionContainer = styled.div`
  width: 630px;
  margin: 15px 0px 15px 63px;
  > .position-title {
    display: flex;
    justify-content: space-between;
    > label {
      font-size: 16px;
      font-weight: 700;
      padding: 10px 10px 10px 0px;
      width: 250px;
    }
  }
  > .position {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 5px 0px 5px 0px;
  }
`;

const PositionSelectBox = styled.div`
  width: 250px;
  #position {
    > div {
      background-color: ${({ theme }) => theme.background};
      color: ${({ theme }) => theme.text};
      border-color: ${({ theme }) => theme.colors.grey3};
    }
  }
`;

const PositionCountBtn = styled.div`
  width: 250px;
  > .count-button-group {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    width: 100px;
    > button {
      width: 25px;
      height: 25px;
      border: 1px solid ${({ theme }) => theme.colors.grey3};
      border-radius: 90px;
      background: ${({ theme }) => theme.background};
      color: ${({ theme }) => theme.text};
      cursor: pointer;
      :hover {
        background: ${({ theme }) => theme.colors.purple1};
        color: #ffffff;
        border: none;
      }
    }
  }
`;

const AddButton = styled.button`
  width: 60px;
  height: 30px;
  padding: 5px 10px;
  margin: 0px 0px 30px 63px;
  text-align: center;
  font-size: 14px;
  background: ${({ theme }) => theme.background};
  color: ${({ theme }) => theme.text};
  border: 1px solid ${({ theme }) => theme.colors.grey3};
  border-radius: 4px;
  :hover {
    color: ${({ theme }) => theme.colors.purple1};
    background: ${({ theme }) => theme.background};
    border: 1px solid ${({ theme }) => theme.colors.purple1};
  }
`;

export default HeaderTemplate;
