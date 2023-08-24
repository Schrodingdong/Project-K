import React, {useEffect, useState} from 'react';
import AccountButtonQuote from "./AccountButtonQuote";
import {getJwt, instance} from "../../../api/instance";

export interface QuoteData {
    id: string,
    quoteBody: string,
    quoteUserEmail: string,
    quoteBook: string,
    quoteBookAuthor: string
}


function Quote(prop: QuoteData) {
    const [username, setUsername] = useState('');
    function fetchUsernameOfUserEmail(){
        instance(getJwt()).get('/user/get?email='+prop.quoteUserEmail)
            .then(res => {
                setUsername(res.data.username);
            });
    }
    useEffect(() => {
        fetchUsernameOfUserEmail();
    }, [])

    return (
        <li className={'quote'}>
            <div className={'quote-header'}>
                <AccountButtonQuote username={username} userEmail={prop.quoteUserEmail}/>
                <p className={'quote-username'}>{username}</p>
            </div>
            <div className={'quote-body'}>
                <p>{prop.quoteBody}</p>
            </div>
            <div className={'quote-footer'}>
                <p>{prop.quoteBook} - {prop.quoteBookAuthor}</p>
            </div>

        </li>
    );
}

export default Quote;