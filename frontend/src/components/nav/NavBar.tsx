import './NavBar.css'
import {Link} from "react-router-dom";
import AccountButton from "../AccountButton";
import {useContext} from "react";
import {AuthContext} from "../../App";
import {instance} from "../../api/instance";

const NavBar = () => {
    const authContext = useContext(AuthContext);
    console.log(authContext)
    const _isAuth = authContext.isAuth;

    return (
        <nav className="navbar">
            <Link to="/">
                <h1>Project K</h1>
            </Link>
            <div className="navigation">
                {
                    _isAuth ?
                        NavMenu() :
                        <Link to="/auth/login" className="button-link button-login">Login</Link>
                }
            </div>
        </nav>
    )
}



const NavMenu = () => {
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
        instance.post("/auth/logout",{}, {
            headers: {
                'Authorization' : "Bearer " + token
            }
        })
        authContext.setAuthToken("");
    }
    return (
        <div className="nav-menu">
            <ul>
                <li>
                    Home
                </li>
                <li>
                    Profile
                </li>
                <li>
                    <button className="button-outline-white" onClick={logout}>
                        logout
                    </button>
                </li>
            </ul>
            <AccountButton username="username"/>
        </div>
    )
}

export default NavBar;