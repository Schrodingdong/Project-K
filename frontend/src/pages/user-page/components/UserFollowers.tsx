import React, {useContext, useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {instance} from "../../../api/instance";
import AccountButton from "../../../components/AccountButton";
import {AuthContext, utilProps} from "../../../App";
import {FollUserDetails} from "../UserPage";
import './UserFollowers.css'

const followerSampleList:Array<FollUserDetails> = []

function UserFollowers() {
    const [followerList, setFollower] = useState(followerSampleList);
    const authContext = useContext(AuthContext);
    const { subject } = useParams();
    useEffect(() => {
        instance(authContext.token).get("/user/get/followers?email="+subject)
            .then(res => {
                setFollower(res.data)
            })
    }, [])


    return (
        <ul className="user-follower">
            {
                followerList.map(e =>
                    <li className={"follower"}>
                        <div>
                            <AccountButton username={e.username}/>
                            <p>{e.username}</p>
                        </div>
                    </li>
                )
            }
        </ul>
    );
}

export default UserFollowers;