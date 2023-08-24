import React from 'react';
import Alert from './Alert';
import './AlertList.css'

interface AlertListProps {
  alerts: string[] 
}

function AlertList(props: AlertListProps) {
  return(
    <ul className='alert-list'>
      {
        props.alerts.map((el) =>
            <Alert key={el} errorMessage={el}/>
        )
      }
    </ul>
  );
}

export default AlertList;