import {Link, Navigate, useNavigate} from "react-router-dom";
import {useState, useEffect, useContext} from 'react';
import {AuthContext, utilProps} from "../../../App";
import { instance, getJwt, getSubject } from "../../../api/instance";
import jwtDecode from "jwt-decode";

interface LoginProps extends utilProps{
    setAuthToken: () => void;
}
/**
 * Should do the following :
 * - Check if jwt exists in cookies
 * - validate it with the subject in the cookie
 * - if gud :
 *      - redirect Home
 *      - clear cookies, force to login
 */
const Login = (props: LoginProps) => {
    const authContext = useContext(AuthContext);
    const isAuth = authContext.isAuth;
    const [log_email, setEmail] = useState("");
    const [log_password, setPassword] = useState("");
    const navigate = useNavigate();

    const login = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if(log_email.length === 0 || log_password.length === 0) {
            props.addAlert("Incomplete credentials");
            return;
        }

        instance(authContext.token).post("/auth/login", {
            email: log_email,
            password: log_password,
        }, {
            headers: getJwt() == null? {}:{'Authorization': 'Bearer ' + getJwt()} ,
        })
        .then((res) => {
            // store jwt
            const jwt = res.data;
            const decodedJwt = jwtDecode<{sub:string, exp:Number, iat:Number}>(jwt);
            document.cookie = `jwt=${jwt}; path=/`
            document.cookie = `subject=${decodedJwt.sub}; path=/`
            props.setAuthToken();
            // navigate to homepage
            navigate("/home")
        }).catch((e) => {
            props.addAlert("Invalid credentials")
        })
    }


    return (
        <>
            {
                !isAuth?
                    <div className="auth-container">
                        <h1>Login</h1>
                        <form onSubmit={login}>
                            <label htmlFor="email-field">email</label>
                            <input type="text" id="email-field" name="email-field" onChange={e => setEmail(e.target.value)}/>
                            <label htmlFor="password-field">password</label>
                            <input type="password" id="password-field" name="password-field" onChange={e => setPassword(e.target.value)}/>
                            <input type="submit" value="Login" className="button-full"/>
                        </form>
                        <br/>
                        <Link to="../register" className="underline-on-hover">Don't have an account ?</Link>
                    </div>:
                    <Navigate to={"/home"}/>
            }
        </>
    )
}

export default Login;