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
  }, 2000)

  return (
    <>{
        isVisible && 
        <div className='pop-up-alert'>
          <div className='alert-container'>
            <span>
              {props.errorMessage}
            </span>
            <IoMdClose onClick={closeAlert}/>
          </div>
        </div>
      }
    </>
  );
}

export default Alert;