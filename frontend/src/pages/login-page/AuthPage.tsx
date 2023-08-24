import {AuthContext, utilProps} from '../../App';
import './AuthPage.css'
import {Navigate, Outlet} from "react-router-dom";
import {useContext} from "react";

interface AuthPageProps extends utilProps{}

const AuthPage = (props: AuthPageProps) => {
    const authContext = useContext(AuthContext)
    const isAuth = authContext.isAuth;
    return (
        <>
            {
                !isAuth?
                    <div className="auth-page">
                        <div className="background-gradient"></div>
                        <div className="center">
                            <Outlet />
                        </div>
                    </div>:
                    <Navigate to={"/home"}/>
            }
        </>
    )
}

export default AuthPage;