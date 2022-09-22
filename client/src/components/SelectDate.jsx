import { useState } from 'react';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DesktopDatePicker } from '@mui/x-date-pickers';
import { LocalizationProvider } from '@mui/x-date-pickers';
import dayjs from 'dayjs';
import TextField from '@mui/material/TextField';

const SelectDate = () => {
  const [startDate, setStartDate] = useState(new Date());
  const dateFormat = dayjs(startDate).format('YYYY-MM-DD');
  return (
    <>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DesktopDatePicker
          // views={['day']}
          value={dateFormat}
          onChange={setStartDate}
          inputFormat="YYYY-MM-DD"
          renderInput={(params) => <TextField {...params} />}
        />
        {/* {console.log(dateFormat)} */}
      </LocalizationProvider>
    </>
  );
};

export default SelectDate;
