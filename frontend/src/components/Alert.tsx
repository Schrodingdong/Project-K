import React from 'react';
import './Alert.css'
import { IoMdClose } from 'react-icons/io'
import { useState } from 'react'

interface AlertProps {
  errorMessage: string
}


function Alert(props: AlertProps) {
  const [isVisible, setVisible] = useState(true);
  const closeAlert = (e:React.MouseEvent<SVGElement>) => {
    setVisible(false);
  }

  setTimeout(() => {
    setVisible(false)
  }, 5000)

  return (
    <>{
        isVisible && 
        <li className='pop-up-alert'>
          <div className='alert-container'>
            <span>
              {props.errorMessage}
            </span>
            <IoMdClose onClick={closeAlert}/>
          </div>
        </li >
      }
    </>
  );
}

export default Alert;