import './NavBar.css'
import {Link} from "react-router-dom";
import AccountButton from "../AccountButton";

interface NavbarProps {
    auth: boolean
}

const NavBar = (props: NavbarProps) => {
    let _isAuth = props.auth;
    return (
        <nav className="navbar">
            <h1>Project K</h1>
            <div className="navigation">
                {
                    _isAuth ?
                        NavMenu() :
                        <Link to="/auth/login">Login</Link>
                }
            </div>
        </nav>
    )
}



const NavMenu = () => {
    return (
        <div className="nav-menu">
            <ul>
                <li>
                    adsqd
                </li>
                <li>
                    dsqdjqskljdlqsj
                </li>
            </ul>
            <AccountButton username="username"/>
        </div>
    )
}

export default NavBar;