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

const EditHeaderTemplate = ({ formData, setFormData }) => {
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

  // 클릭된 버튼 스타일링 (default: 모집 중 버튼)
  const [defaultBtnActive, setDefaultBtnActive] = useState(formData.status);
  const [typeBtnActive, setTypeBtnActive] = useState(formData.type);
  const [onlineBtnActive, setOnlineBtnActive] = useState(formData.onOffline);
  const [contactBtnActive, setContactBtnActive] = useState(formData.contact);

  // 예상 기간 선택
  const [periodSelected, setPeriodSelected] = useState(formData.period);

  // 선택된 기술스택
  const [stack, setStack] = useState([]);

  // 이전 기술스택 불러오기
  useEffect(() => {
    setStack(formData.tagList);
  }, [formData]);

  // 수정페이지에서 추가한 기술스택을 formData에 추가
  useEffect(() => {
    setFormData({
      ...formData,
      tagList: [...stack],
    });
  }, [stack.length]);

  // event handlers
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

  const contactBtnClickHandler = (e) => {
    setContactBtnActive(e.target.id);
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

  return (
    <HeaderTemplateContainer>
      <SelectOneType>
        <label>모집 상태</label>
        <div
          className={`select-btn ${defaultBtnActive === 'OPENING' ? 'active' : ''}`}
          id='OPENING'
          onClick={meetingStatusHandler}>
          모집 중
        </div>
        <div
          className={`select-btn ${defaultBtnActive === 'CLOSED' ? 'active' : ''}`}
          id='CLOSED'
          onClick={meetingStatusHandler}>
          모집 완료
        </div>
      </SelectOneType>
      <div className='group-selection'>
        <SelectButton>
          <label>유형</label>
          <div
            id='PROJECT'
            className={`select-btn ${formData.type === 'PROJECT' ? 'active' : ''}`}
            onClick={meetingTypeHandler}>
            프로젝트
          </div>
          <div
            id='STUDY'
            className={`select-btn ${formData.type === 'STUDY' ? 'active' : ''}`}
            onClick={meetingTypeHandler}>
            스터디
          </div>
        </SelectButton>
        <SelectButton>
          <label>진행 방법</label>
          <div
            id='online'
            className={`select-btn ${formData.onOffline === 'online' ? 'active' : ''}`}
            onClick={meetingOnlineHandler}>
            온라인
          </div>
          <div
            id='offline'
            className={`select-btn ${formData.onOffline === 'offline' ? 'active' : ''}`}
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
              value={formData.dueDate}
              onChange={dueDateHandler}
              inputFormat='YYYY-MM-DD'
              renderInput={(params) => <TextField {...params} />}
            />
          </LocalizationProvider>
        </SelectType>
        <SelectType>
          <label>시작 예정일</label>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DesktopDatePicker
              views={['day']}
              value={formData.startingDate}
              onChange={startingDateHandler}
              inputFormat='YYYY-MM-DD'
              renderInput={(params) => <TextField {...params} />}
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
                option.value === periodSelected || option.value === formData.period
              );
            })}
            defaultValue={period[0]}
            onChange={periodHandler}
          />
        </SelectType>
        <SelectButton>
          <label>연락 방법</label>
          <div
            id='chatLink'
            className={`select-btn ${contactBtnActive === 'chatLink' ? 'active' : ''}`}
            onClick={contactBtnClickHandler}>
            오픈 채팅
          </div>
          <div
            id='email'
            className={`select-btn ${contactBtnActive === 'email' ? 'active' : ''}`}
            onClick={contactBtnClickHandler}>
            이메일
          </div>
          <input
            id='contact'
            type='text'
            placeholder='링크 또는 이메일을 입력해주세요'
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
        <div className='position-edit-message'>
          <p>
            <i className='fi fi-rr-exclamation' /> 모집 포지션과 인원은 수정할 수 없습니다.
          </p>
        </div>
      </SelectPositionContainer>
      <SelectOneType>
        <label className='individual-selection'>기술 스택</label>
        <TechStack selected={stack} setSelected={setStack} formData={formData} setFormData={setFormData} />
      </SelectOneType>
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
  box-shadow: 0 1px 8px ${({ theme }) => theme.colors.grey2};
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
    padding: 10px;
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
      background: #ffffff;
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
    margin-top: 10px;
    :focus {
      outline: 1px solid ${({ theme }) => theme.colors.purple1};
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
    padding: 10px;
  }
`;

const SelectOneType = styled.div`
  width: 500px;
  margin-left: 63px;
  > label {
    display: block;
    font-size: 16px;
    font-weight: 700;
    padding: 10px;
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
      background: #ffffff;
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
  width: 625px;
  margin: 15px 0px 15px 63px;
  > .position-title {
    display: flex;
    justify-content: space-between;
    > label {
      font-size: 16px;
      font-weight: 700;
      padding: 10px;
      width: 250px;
    }
  }
  > .position-edit-message {
    display: flex;
    justify-content: center;
    /* align-items: center; */
    border-radius: 4px;
    margin-top: 10px;
    background-color: ${({ theme }) => theme.colors.grey1};
    font-size: 12px;
    height: 80px;
    > p {
      display: flex;
      flex-direction: row;
      justify-content: space-around;
      align-items: center;
      width: 220px;
    }
  }
`;

export default EditHeaderTemplate;
