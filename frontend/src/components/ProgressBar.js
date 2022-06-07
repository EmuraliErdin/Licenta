import React, { useEffect, useState } from 'react'
import { CircularProgressbar } from 'react-circular-progressbar';
import ProgressBarWrapper from './ProgressBarWrapper';

const ProgressBar= (props) => {
    const {score} = props;

    const calcColor = (percent, start, end) => {
        let a = percent / 100,
          b = (end - start) * a,
          c = b + start;

        return 'hsl(' + c + ', 100%, 50%)';
      };

		
	return (
        <ProgressBarWrapper valueStart={0} valueEnd={score}>
            {(value)=>(
                	<CircularProgressbar
                    value={value}
                    circleRatio={0.7}
                    text ={`${value}%`}
                    styles={{
                        trail:{
                            strokeLinecap:"butt",
                            transform:"rotate(-126deg)",
                            transformOrigin:"center center"
                        },
                        path:{
                            strokeLinecap:"butt",
                            transform:"rotate(-126deg)",
                            transformOrigin:"center center",
                            stroke:calcColor(value, 0, 120)
                        },
                        text:{
                            fill:"#000"
                        }
                    }} 
                    strokeWidth={10}/>
            )}
        </ProgressBarWrapper>

	)
}

export default ProgressBar;
