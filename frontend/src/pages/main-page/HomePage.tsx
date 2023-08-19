import React, {useContext, useEffect} from 'react';
import {AuthContext} from "../../App";
import {Navigate} from "react-router-dom";
import SharedQuotes from './components/SharedQuotes';
import { instance } from '../../api/instance';

function HomePage() {
    const authContext = useContext(AuthContext);
    // const isAuth = authContext.isAuth; //FOR PROD!!
    const isAuth = true;
    // get the shared quotes on page load



    return (
        <>
            {
                isAuth?
                    <div className='main-content'>
                        <SharedQuotes/>
                    </div> :
                    <Navigate to="/auth/login" replace={true}/>
            }
        </>
    );
}

export default HomePage;