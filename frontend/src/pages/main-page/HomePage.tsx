import React, {useContext, useEffect, useState} from 'react';
import {AuthContext, utilProps} from "../../App";
import {Navigate} from "react-router-dom";
import SharedQuotes from './components/SharedQuotes';
import {getJwt, getSubject, instance} from '../../api/instance';
import {QuoteData} from "./components/Quote";

const testQuotes:Array<QuoteData>= [
]


function HomePage(props: utilProps) {
    const [quotes, setQuotes] = useState(testQuotes);
    const authContext = useContext(AuthContext);
    const isAuth = authContext.isAuth; //FOR PROD!!
    // get the shared quotes on page load
    const getSharedQuotesOfFollowing = () => {
        console.log("HOMEPAGE : " + authContext.token);
        instance(authContext.token).get('/quote/get/following')
            .then(res => {
                const newQuoteList:Array<QuoteData> = [];
                res.data.forEach((e: { id: any; quote: any; userEmail: any; bookAuthor: any; book: any; }) => {
                    newQuoteList.push(
                        {
                            id: e.id,
                            quoteBody: e.quote,
                            quoteUserEmail: e.userEmail,
                            quoteBookAuthor: e.bookAuthor,
                            quoteBook: e.book
                        }
                    )
                })
                setQuotes(newQuoteList);
            })
            .catch(err => {
                console.log(err);
            })
    }

    useEffect(() => {
        getSharedQuotesOfFollowing()
    }, []);



    return (
        <>
            {
                isAuth?
                    <div className='main-content'>
                        <SharedQuotes quotes={quotes} addAlert={props.addAlert}/>
                    </div> :
                    <Navigate to="/auth/login" replace={true}/>
            }
        </>
    );
}

export default HomePage;