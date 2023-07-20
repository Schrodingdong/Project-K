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
          <li>
            <Alert key={el} errorMessage={el}/>
          </li>
        )
      }
    </ul>
  );
}

export default AlertList;