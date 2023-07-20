import React from 'react';
import './App.css';
import {createBrowserRouter, RouterProvider, Route, Routes} from "react-router-dom";
import LandingPage from "./pages/home-page/LandingPage";
import NavBar from "./components/nav/NavBar";
import Footer from "./components/footer/Footer";
import AuthPage from "./pages/login-page/AuthPage";
import Login from "./pages/login-page/components/Login";
import Register from "./pages/login-page/components/Register";
import HomePage from './pages/main-page/HomePage';
import AlertList from './components/AlertList';
import { useState } from 'react';

export interface utilProps {
    addAlert : (value: string) => void;
}

const AuthContext = React.createContext<string>("");

function App() {
    const _isAuth = false;
    const [alerts, setAlerts]  = useState<string[]>([]);
    const [token, setToken] = useState<string>("");

    const addAlert = (message: string) => {
        console.log(alerts)
        setAlerts([...alerts, message]);
    }

    return (
        <AuthContext.Provider value={token}>
            <NavBar auth={_isAuth}/>
            <AlertList alerts={alerts}/>
            <Routes>
                <Route path="/" element={<LandingPage/>}/>
                <Route path="auth" element={<AuthPage addAlert={addAlert}/>}>
                    <Route path="login" element={<Login addAlert={addAlert}/>}/>
                    <Route path="register" element={<Register addAlert={addAlert}/>}/>
                </Route>
                <Route path="home" element={<HomePage/>}/>
            </Routes>
            {/*<RouterProvider router={router}/>*/}
            {/*<Footer/>*/}
        </AuthContext.Provider>
    );
}

export default App;
