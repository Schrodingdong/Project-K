import React from 'react';
import './App.css';
import {createBrowserRouter, RouterProvider, Route, Routes} from "react-router-dom";
import LandingPage from "./pages/home-page/LandingPage";
import NavBar from "./components/nav/NavBar";
import Footer from "./components/footer/Footer";
import AuthPage from "./pages/login-page/AuthPage";
import Login from "./pages/login-page/components/Login";
import Register from "./pages/login-page/components/Register";

function App() {
    const _isAuth = false;
    const  router = createBrowserRouter([{
        path: "/",
        element: <LandingPage/>,
    },{
        path: "/auth",
        element: <AuthPage/>,
        children: [
            {
                path:'login',
                element: <Login/>
            },
            {
                path:'register',
                element: <Register/>
            }
        ]
    }])
    return (
        <>
            <NavBar auth={_isAuth}/>
            <Routes>
                <Route path="/" element={<LandingPage/>}/>
                <Route path="auth" element={<AuthPage/>}>
                    <Route path="login" element={<Login/>}/>
                    <Route path="register" element={<Register/>}/>
                </Route>
            </Routes>
            {/*<RouterProvider router={router}/>*/}
            {/*<Footer/>*/}
        </>
    );
}

export default App;
