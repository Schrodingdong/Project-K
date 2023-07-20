import {Link, useNavigate} from "react-router-dom";
import { instance } from "../../../api/instance";
import { useState } from "react";
import Alert from "../../../components/Alert";
import { utilProps } from "../../../App";

interface RegisterProps extends utilProps{}

const Register = (props: RegisterProps) => {
    const navigate = useNavigate();
    const [reg_username, setUsername] = useState("");
    const [reg_email, setEmail] = useState("");
    const [reg_password, setPassword] = useState("");
    const [reg_repeatPassword, setRepeatPassword] = useState("");

    const arePasswordMatching = () => {
        return reg_password === reg_repeatPassword;
    }

    const registration = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if(reg_email.length === 0 || reg_username.length === 0 || 
            reg_password.length === 0 || reg_repeatPassword.length === 0 ){
            props.addAlert("invalid credentials")
            return;
        }
        if(!arePasswordMatching()){
            props.addAlert("Passwords don't match")
            return;
        }
        instance.post("/auth/register",
            {
                username: reg_username,
                email:  reg_email,
                password: reg_password 
            }
        ).then((res) => {
            navigate("../login");
        }).catch((e) => {
            console.error(e.response.data)
            // show a modal
            props.addAlert(e.response.data)
        })
    }

    return (
        <div className="auth-container">
            <h1>Register</h1>
            <form onSubmit={registration}>
                <label htmlFor="username-field">username</label>
                <input type="text" id="username-field" name="username-field" onChange={e => setUsername(e.target.value)}/>
                <label htmlFor="email-field">email</label>
                <input type="text" id="email-field" name="email-field" onChange={e => setEmail(e.target.value)}/>
                <label htmlFor="password-field">password</label>
                <input type="password" id="password-field" name="password-field" onChange={e => setPassword(e.target.value)}/>
                <label htmlFor="password2-field">rewrite password</label>
                <input type="password" id="password2-field" name="password2-field" onChange={e => setRepeatPassword(e.target.value)}/>
                <input type="submit" value="Register" className="button-full"/>
            </form>
            <br/>
            <Link to="../login" className="underline-on-hover">Already have an account ?</Link>
        </div>
    )
}

export default  Register;