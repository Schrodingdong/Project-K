import React, {useContext, useEffect, useState} from 'react';
import {AuthContext, utilProps} from "../../App";
import {Navigate, Outlet, useNavigate, useParams} from "react-router-dom";
import './UserPage.css';
import jojiProfile from '../../static/img/joji.jpg' ;
import Quote, {QuoteData} from "../main-page/components/Quote";
import UserQuote from "./components/UserQuote";
import {getSubject, instance} from "../../api/instance";

interface UserPageProps extends utilProps{}
export interface FollUserDetails{
    username: string,
    email: string
}

const sampleQuoets: Array<QuoteData> = []


function UserPage(props: UserPageProps) {
    const [quotes, setQuotes] = useState(sampleQuoets);
    const { subject } = useParams();
    const [username, setUsername] = useState('Username');
    const [bio, setBio] = useState('Bio');
    const authContext = useContext(AuthContext);
    const isAuth = authContext.isAuth;
    const navigate = useNavigate();


    useEffect(() => {
        // get user details
        instance(authContext.token).get('/user/get?email=' + subject)
            .then(res => {
                setUsername(res.data.username);
                setBio(res.data.bio);
            })
    }, [])



    return (
        <>
            {
                isAuth?
                    <div className='main-content'>
                        <div className={'section-3-4'}>
                            <div className={'section-3'}>
                                <img src={jojiProfile} alt={'userImage'} className={'profile-image'}/>
                                <div className={'user-info'}>
                                    <h3>{username}</h3>
                                    <p>{bio}</p>
                                    <button className={'button-outline'}>Edit User Info</button>
                                    {/*<div className={'following-info'}>*/}
                                    {/*    <p>Followers {followerNumber}</p>*/}
                                    {/*    <p>Following {followingNumber}</p>*/}
                                    {/*</div>*/}
                                </div>
                            </div>
                            <div className={'section-4'}>
                                <div className={"selection"}>
                                    <button className={'button-outline'} onClick={() => {navigate("shared-quotes")}}>My Quotes</button>
                                    <button className={'button-outline'} onClick={() => {navigate("following")}}>Following</button>
                                    <button className={'button-outline'} onClick={() => {navigate("followers")}}>Follower</button>
                                </div>
                                <br/>
                                <div className={'huge-sep'}></div>
                                <br/>
                                <Outlet/>
                            </div>
                        </div>
                    </div>:
                    <Navigate to={"/auth/login"} replace={true}/>

            }
        </>
    );
}

export default UserPage;