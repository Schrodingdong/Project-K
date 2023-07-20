import React, {useContext} from 'react';
import {AuthContext} from "../../App";
import {Navigate} from "react-router-dom";

function HomePage() {
    const authContext = useContext(AuthContext);
    const isAuth = authContext.isAuth;
    console.log(authContext)
    return (
        <>
            {
                isAuth?
                    <div>
                        <h1>Home Page !</h1>
                    </div> :
                    <Navigate to="/auth/login" replace={true}/>
            }
        </>
    );
}

export default HomePage;