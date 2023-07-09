const Login = () => {
    return (
        <div className="auth-container">
            <h1>Login</h1>
            <form>
                <label htmlFor="email-field">email</label>
                <input type="text" id="email-field" name="email-field"/>
                <label htmlFor="password-field">password</label>
                <input type="password" id="password-field" name="password-field"/>
                <input type="submit" value="Login" className="button-full"/>
            </form>
        </div>
    )
}

export default Login;