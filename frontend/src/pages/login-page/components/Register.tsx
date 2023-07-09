import {Link} from "react-router-dom";

const Register = () => {
    return (
        <div className="auth-container">
            <h1>Register</h1>
            <form>
                <label htmlFor="username-field">username</label>
                <input type="text" id="username-field" name="username-field"/>
                <label htmlFor="email-field">email</label>
                <input type="text" id="email-field" name="email-field"/>
                <label htmlFor="password-field">password</label>
                <input type="password" id="password-field" name="password-field"/>
                <label htmlFor="password2-field">rewrite password</label>
                <input type="password" id="password2-field" name="password2-field"/>
                <input type="submit" value="Register" className="button-full"/>
            </form>
            <br/>
            <Link to="../login" className="underline-on-hover">Already have an account ?</Link>
        </div>
    )
}

export default  Register;