import React, {useContext, useEffect, useState} from 'react';
import './UserFollowing.css'
import {FollUserDetails} from "../UserPage";
import AccountButton from "../../../components/AccountButton";
import {instance} from "../../../api/instance";
import {AuthContext, utilProps} from "../../../App";
import {useParams} from "react-router-dom";
import UserFollowPopup from "./UserFollowPopup";

const followingSampleList:Array<FollUserDetails> = []

function UserFollowing(props: utilProps) {
    const [followingList, setFollowing] = useState(followingSampleList);
    const [userSearch, setUserSearch] = useState(false);
    const authContext = useContext(AuthContext);
    const { subject } = useParams();
    useEffect(() => {
        instance(authContext.token).get("/user/get/following?email="+subject)
            .then(res => {
                console.log(res.data)
                setFollowing(res.data)
            })
    }, [])

    const unfollowUser = (email:string) => {
        instance(authContext.token).delete("/user/unfollow?toUnfollow="+email)
            .catch(err => {
                props.addAlert("Unfollow Problem");
            })
    }

    return (
        <div>
            <button className={'button-full'} onClick={() => setUserSearch(true)}>
                Follow a User
            </button>
            <ul className="user-following">
                {
                    followingList.map(e =>
                        <li className={"following"}>
                            <div>
                                <AccountButton username={e.username}/>
                                <p>{e.username}</p>
                            </div>
                            <button className={"delete"} onClick={() => {
                                unfollowUser(e.email);
                            }}>X</button>
                        </li>
                    )
                }
            </ul>
            {
                userSearch?
                    <UserFollowPopup setUserSearch={setUserSearch}/>
                    :
                    <></>
            }
        </div>
    );
}

export default UserFollowing;