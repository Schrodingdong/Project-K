import React, {useEffect} from 'react';
import './App.css';
import {createBrowserRouter, RouterProvider, Route, Routes, useNavigate} from "react-router-dom";
import LandingPage from "./pages/home-page/LandingPage";
import NavBar from "./components/nav/NavBar";
import Footer from "./components/footer/Footer";
import AuthPage from "./pages/login-page/AuthPage";
import Login from "./pages/login-page/components/Login";
import Register from "./pages/login-page/components/Register";
import HomePage from './pages/main-page/HomePage';
import AlertList from './components/AlertList';
import { useState } from 'react';
import {getJwt, getSubject, instance} from "./api/instance";
import Fallback from "./pages/Fallback";
import jwtDecode from "jwt-decode";

export interface utilProps {
    addAlert : (value: string) => void;
}

export const AuthContext = React.createContext<{isAuth: boolean, token: string, setAuthToken: (token:string) => void}>({
    token: "",
    isAuth: false,
    setAuthToken: () => {}
});

function App() {
    const [alerts, setAlerts]  = useState<string[]>([]);
    const [token, setToken] = useState<string>(""); // check on refresh if we already have a token
    const navigate = useNavigate();
    useEffect(() => {
        // check if we already have a token
        const jwt = getJwt();
        const sub = getSubject();
        if (jwt === null || sub === null) {navigate("/");return;}
        // check if the subject from token is same as subject in cookie
        const decodedJwt = jwtDecode<{sub:string, exp:Number, iat:Number}>(jwt? jwt:"");
        if( decodedJwt.sub != sub) {navigate("/");return;}
        // check if its a valid token ?
        instance.post("/auth/validate-jwt", {}, {
            headers: {
                'Authorization' : "Bearer " + jwt
            }
        }).then(e => setToken(jwt? jwt:""))
            .catch(err => {
                console.error(err);
                setToken("")
            });
    },[])


    const addAlert = (message: string) => {
        console.log(alerts)
        setAlerts([...alerts, message]);
    }

    return (
        <AuthContext.Provider value={{
            isAuth: token.length > 0,
            token: token,
            setAuthToken: setToken
        }}>
            <NavBar/>
            <AlertList alerts={alerts}/>
            <Routes>
                <Route path="/" element={<LandingPage/>}/>
                <Route path="auth" element={<AuthPage addAlert={addAlert}/>}>
                    <Route path="login" element={<Login setAuthToken={setToken} addAlert={addAlert}/>}/>
                    <Route path="register" element={<Register addAlert={addAlert}/>}/>
                </Route>
                <Route path="home" element={<HomePage/>}/>
                <Route path={"*"} element={<Fallback/>}/>
            </Routes>
            {/*<RouterProvider router={router}/>*/}
            {/*<Footer/>*/}
        </AuthContext.Provider>
    );
}

export default App;
