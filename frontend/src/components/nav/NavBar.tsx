import './NavBar.css'
import {Link, useNavigate} from "react-router-dom";
import AccountButton from "../AccountButton";
import {useContext} from "react";
import {AuthContext, utilProps} from "../../App";
import {getJwt, getSubject, getUsername, instance} from "../../api/instance";
import * as util from "util";

interface navbarProps {
    setAuthToken: (token: string) => void;
}
const NavBar = (props: navbarProps) => {
    const authContext = useContext(AuthContext);
    const _isAuth = authContext.isAuth;

    return (
        <nav className="navbar">
            <Link to="/">
                <h1>Project K</h1>
            </Link>
            <div className="navigation">
                {
                    _isAuth ?
                        NavMenu(props) :
                        <Link to="/auth/login" className="button-link button-login">Login</Link>
                }
            </div>
        </nav>
    )
}

interface navmenuProps {
    setAuthToken: (token: string) => void;
}

const NavMenu = (props: navmenuProps) => {
    const navigate = useNavigate();
    const authContext = useContext(AuthContext);
    const token = authContext.token;
    function delete_cookie( name: string, path:string , domain:string ) {
        if( get_cookie( name ) ) {
            document.cookie = name + "=" +
                ((path) ? ";path="+path:"")+
                ((domain)?";domain="+domain:"") +
                ";expires=Thu, 01 Jan 1970 00:00:01 GMT";
        }
    }
    function get_cookie(name: string){
        return document.cookie.split(';').some(c => {
            return c.trim().startsWith(name + '=');
        });
    }


    const logout = (e: React.MouseEvent<HTMLButtonElement>) => {
        instance(getJwt()).post("/auth/logout",{}, {
            headers: {
                'Authorization' : "Bearer " + token
            }
        })
        props.setAuthToken("");
    }
    return (
        <div className="nav-menu">
            <ul>
                <li>
                    <button className={'button-text'} onClick={event => {navigate("/home")}}>
                        Home
                    </button>
                </li>
                <li>
                    <button className={'button-text'} onClick={event => {navigate("/users/"+getSubject()+"/shared-quotes")}}>
                        Profile
                    </button>
                </li>
                <li>
                    <button className="button-outline-white" onClick={logout}>
                        logout
                    </button>
                </li>
            </ul>
            <AccountButton username={getUsername()}/>
        </div>
    )
}

export default NavBar;