import React, { useEffect, useState } from 'react'
import { CircularProgressbar } from 'react-circular-progressbar';

const ProgressBarWrapper = ({valueStart, valueEnd, children}) => {
    const [value, setValue] = useState(valueStart)

    useEffect(()=>{
        setValue(valueEnd)
    },[valueEnd])
	return (
        children(value)
    )
}

export default ProgressBarWrapper;
