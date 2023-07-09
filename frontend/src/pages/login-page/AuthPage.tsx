import './AuthPage.css'
import {Outlet} from "react-router-dom";

const AuthPage = () => {
    return (
        <div className="auth-page">
            <div className="background-gradient"></div>
            <h1>Authentication</h1>
            <div className="center">
                <Outlet />
            </div>
        </div>
    )
}

export default AuthPage;