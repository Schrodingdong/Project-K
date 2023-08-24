import React, {useContext, useEffect, useState} from 'react';
import {instance} from "../../../api/instance";
import {AuthContext} from "../../../App";
import AccountButton from "../../../components/AccountButton";
import './UserFollowPopup.css';

interface UserFollowPopupProps {
    setUserSearch: (b: boolean) => void
}

const usersFollowListSmaple: Array<UserFollowPopupElementProps> = [];

function UserFollowPopup(props: UserFollowPopupProps) {
    const authContext = useContext(AuthContext);
    const [searchKey, setSearchKey] = useState('');
    const [userFollowList, setUserFollowList] = useState(usersFollowListSmaple);
    useEffect(() => {
        instance(authContext.token).get('/user/get/all')
            .then(res => {
                console.log(res.data);
                setUserFollowList(res.data);
            })
    }, [searchKey])
    return (
        <div className={'popup'}>
            <div className={'overlay'} onClick={e=> {props.setUserSearch(false)}}></div>
            <div className={'popup-content user-follow-container'}>
                <div className={'close-container'}>
                    <button onClick={() => props.setUserSearch(false)}>X</button>
                </div>
                <textarea onChange={(e) => setSearchKey(e.target.value)}></textarea>
                <div className={'full-width user-follow-element-container'}>
                    {
                        userFollowList.filter((e,i) => e.username.includes(searchKey)).map((e,i) =>
                            <UserFollowPopupElement username={e.username} email={e.email} bio={e.bio} setUserSearch={props.setUserSearch}/>
                        )
                    }
                </div>

            </div>
        </div>
    );
}

interface UserFollowPopupElementProps extends UserFollowPopupProps{
    username: string,
    email: string,
    bio: string
}
function UserFollowPopupElement(props: UserFollowPopupElementProps) {
    const authContext = useContext(AuthContext);
    const followUser = (email: string) => {
        instance(authContext.token).put('/user/follow?toFollow='+email);
        props.setUserSearch(false);
    }
    return (
        <div className={'user-follow-element'}>
            <div>
                <AccountButton username={props.username}/>
                <p>{props.username}</p>
            </div>
            <button className={"button-outline"} onClick={() => {
                followUser(props.email);
            }}>Follow</button>
        </div>
    )
}

export default UserFollowPopup;